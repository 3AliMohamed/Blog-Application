package com.blog.blog.dto;


import com.blog.blog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {

    private Long id;
    private  String content;
    private PostDTO post;

}
