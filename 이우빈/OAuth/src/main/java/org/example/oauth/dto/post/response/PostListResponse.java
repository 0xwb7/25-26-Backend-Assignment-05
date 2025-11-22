package org.example.oauth.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResponse {
    private final Long postId;
    private final String title;
    private final String authorName;
}
