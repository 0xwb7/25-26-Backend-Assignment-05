package org.example.oauth.dto.comment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long authorId;
    private String authorName;
    private String content;
}
