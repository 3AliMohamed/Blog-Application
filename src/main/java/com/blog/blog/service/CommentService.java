package com.blog.blog.service;

import com.blog.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAll();

    List<CommentDto> getByPostId(Long id);

    CommentDto create(CommentDto commentDto ,Long postId);

    CommentDto updateById(CommentDto commentDto ,Long id);

    void deleteById(Long id);


}
