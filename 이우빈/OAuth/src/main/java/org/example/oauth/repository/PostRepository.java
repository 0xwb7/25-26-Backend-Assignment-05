package org.example.oauth.repository;

import org.example.oauth.domain.post.Post;
import org.example.oauth.dto.post.response.PostListResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        select new org.example.oauth.dto.post.response.PostListResponse(
            p.id, p.title, a.name
        )
        from Post p
        join p.author a
        order by p.id desc
    """)
    List<PostListResponse> findPostList();

    @EntityGraph(attributePaths = "author")
    Optional<Post> findById(Long id);
}
