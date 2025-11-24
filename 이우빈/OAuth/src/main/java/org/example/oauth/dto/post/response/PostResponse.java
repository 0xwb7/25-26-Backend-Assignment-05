package org.example.oauth.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private Long authorId;
    private String authorName;
    private String title;
    private String content;
}
