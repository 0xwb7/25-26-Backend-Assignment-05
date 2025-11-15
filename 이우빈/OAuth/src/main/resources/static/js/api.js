(function () {
    const API = "";            // same-origin
    let accessToken = null;    // memory-only

    function out(data) {
        const el = document.querySelector("#out");
        if (!el) return;
        el.textContent =
            typeof data === "string" ? data : JSON.stringify(data, null, 2);
    }

    async function api(path, { method = "GET", body, retry = true } = {}) {
        const headers = { "Content-Type": "application/json" };
        if (accessToken) headers["Authorization"] = `Bearer ${accessToken}`;

        const res = await fetch(API + path, {
            method,
            headers,
            credentials: "include",
            body: body ? JSON.stringify(body) : undefined,
        });

        if (res.status === 401 && retry) {
            const r = await fetch(API + "/auth/refresh", {
                method: "POST",
                credentials: "include",
            });
            if (r.ok) {
                const j = await r.json();
                accessToken = j.accessToken || null;
                return api(path, { method, body, retry: false });
            }
        }

        const text = await res.text();
        try {
            return { status: res.status, json: JSON.parse(text) };
        } catch {
            return { status: res.status, text };
        }
    }

    async function requireAuth({ redirectToLogin = false } = {}) {
        if (!accessToken) {
            const r = await fetch("/auth/refresh", {
                method: "POST",
                credentials: "include",
            });
            if (r.ok) {
                const j = await r.json();
                accessToken = j.accessToken || null;
            }
        }
        if (!accessToken) {
            if (redirectToLogin) {
                const returnTo = encodeURIComponent(location.pathname + location.search);
                location.href = `/login.html?returnTo=${returnTo}`;
            }
            return false;
        }
        return true;
    }

    async function logoutAndGoHome() {
        try {
            await fetch("/auth/logout", { method: "POST", credentials: "include" });
        } finally {
            accessToken = null;
            location.href = "/index.html";
        }
    }

    async function bootstrapAfterGoogle() {
        const params = new URLSearchParams(location.search);
        if (params.get("loggedIn") === "google") {
            const r = await fetch("/auth/refresh", {
                method: "POST",
                credentials: "include",
            });
            if (r.ok) {
                const j = await r.json();
                accessToken = j.accessToken || null;
                out({ status: 200, json: { message: "Google 로그인 완료" } });
            } else {
                out({ status: r.status, text: "refresh 실패" });
            }
        }
    }

    window.AppAPI = {
        api,
        out,
        requireAuth,
        logoutAndGoHome,
        bootstrapAfterGoogle,
        setAccessToken: (t) => {
            accessToken = t;
        },
        getAccessToken: () => accessToken,
    };
})();
