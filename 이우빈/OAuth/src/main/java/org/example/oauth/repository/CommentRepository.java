package org.example.oauth.repository;

import org.example.oauth.domain.comment.Comment;
import org.example.oauth.dto.comment.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        select new org.example.oauth.dto.comment.response.CommentResponse(
            c.id, a.id, a.name, c.content
        )
        from Comment c
        join c.author a
        where c.post.id = :postId
        order by c.id desc
    """)
    List<CommentResponse> findCommentResponsesByPostId(@Param("postId") Long postId);

    @Modifying
    @Query("delete from Comment c where c.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}
