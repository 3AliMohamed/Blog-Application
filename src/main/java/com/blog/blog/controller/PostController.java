package com.blog.blog.controller;

import com.blog.blog.dto.PostDTO;
import com.blog.blog.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/post")

public class PostController {

    /**
     * The Service.
     */
    @Autowired
    PostService service;


    /**
     * Gets post  by id.
     *
     * @param id the id
     * @return the post by id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        log.info("Getting  Post By Id");
        PostDTO post = service.getById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    /**
     * Gets all posts
     *
     * @return list of all posts
     */
    @GetMapping(path = "/")
    public ResponseEntity<List<PostDTO>> getAll() {
        log.info("Getting  All Posts");
        List<PostDTO> allPost = service.getAll();
        if (!allPost.isEmpty())
            return new ResponseEntity<>(allPost, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Add post .
     *
     * @param postDTO the post
     * @return the created post
     */
    @PostMapping(path = "/")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO postDTO) {
        log.info("Adding  Post ");
        PostDTO post = service.create(postDTO);

        if (post != null)
            return new ResponseEntity<>(post, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Update post .
     *
     * @param id      the id
     * @param newPost the new post
     * @return the updated post
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO newPost) {
        log.info("Updating  Post By Id");
        PostDTO post = service.updateById(newPost, id);

        if (post != null)
            return new ResponseEntity<>(post, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        log.info("Deleting  Post By Id");
         service.deleteById(id);
         return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

}
