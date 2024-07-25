package com.blog.blog.controller;

import com.blog.blog.dto.CommentDto;
import com.blog.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")

public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    CommentService commentService;

    @GetMapping(path = "/")
    public ResponseEntity<List<CommentDto>> getAll(){
        log.info("getting all comments ");
        List<CommentDto> allComments= commentService.getAll();

        if(!allComments.isEmpty())
        {
            return new ResponseEntity<>(allComments, HttpStatus.OK);

        }
        else{
            return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

        @GetMapping(path = "/{id}")
    public ResponseEntity<List<CommentDto>> getByPostId(@PathVariable Long id ){
        log.info("getting comments  by post id");
        List<CommentDto> allComments= commentService.getByPostId(id);


        if(!allComments.isEmpty())
        {
            return new ResponseEntity<>(allComments, HttpStatus.OK);

        }
        else{
            return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/{post_id}")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,@PathVariable Long post_id)
    {
        log.info("adding new comment");
        CommentDto comment=commentService.create(commentDto,post_id);
        if(comment !=null)
        {
            return  new ResponseEntity<>(comment,HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id , @RequestBody CommentDto commentDto)
    {
        CommentDto comment= commentService.updateById(commentDto,id);

        if (comment!=null)
            return new ResponseEntity<>(comment,HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id )
    {
        commentService.deleteById(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

}
