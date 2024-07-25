package com.blog.blog.service;

import com.blog.blog.dto.PostDTO;

import java.util.List;

public interface PostService {
    /**
     * Gets all posts.
     *
     * @return List of all posts
     */
    List<PostDTO> getAll();

    /**
     * Gets post by id.
     *
     * @param id the id
     * @return the post by id
     */
    PostDTO getById(Long id);

    /**
     * Delete post by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Create post.
     *
     * @param postDTO the post
     * @return the created post
     */
    PostDTO create(PostDTO postDTO);

    /**
     * Update post by id.
     *
     * @param PostDTO the post
     * @param id      the id
     * @return the updated post
     */
    PostDTO updateById(PostDTO PostDTO, Long id);
}