package com.blog.blog.service;

import com.blog.blog.dto.CommentDto;
import com.blog.blog.dto.PostDTO;
import com.blog.blog.model.Comment;
import com.blog.blog.model.Post;
import com.blog.blog.repository.CommentRepository;
import com.blog.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl  implements  CommentService{

    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostService postService;

    @Autowired
    ModelMapper modelMapper ;
    @Override
    public List<CommentDto> getAll() {
        List<Comment> comments = commentRepository.findAll();
        comments.stream().forEach(comment -> {
            comment.getPost().setComments(null);
        });
        if( !comments.isEmpty()){
            return   comments.stream().map(comment -> modelMapper.map(comment,CommentDto.class)).collect(Collectors.toList());
        }
        else {
            return  null;
        }


    }


    @Override
    public List<CommentDto> getByPostId(Long post_id) {
        List<Comment> commentsOptional = commentRepository.findByPostId(post_id);

        // Check if commentsOptional contains a value
        //            return commentDtoList;
        // or throw an exception as needed
        return commentsOptional.stream()
                .map(comment -> {
                    comment.setPost(null);
                    return modelMapper.map(comment, CommentDto.class);
                }
                )
                .collect(Collectors.toList());
    }


    @Override
    public CommentDto create(CommentDto commentDto ,Long postId) {
        Comment comment =modelMapper.map(commentDto,Comment.class);

        PostDTO returnedPost = postService.getById(postId);
        if(returnedPost ==null)
        {
            return  null;
        }
        Post post =modelMapper.map(returnedPost,Post.class);

        comment.setPost(post);

        Comment returnedComment = commentRepository.save(comment);

        return  modelMapper.map(returnedComment,CommentDto.class);

    }


    @Override
    public CommentDto updateById(CommentDto commentDto, Long id) {


        // find the post first
        Optional<Comment>optionalComment= commentRepository.findById(id);


        if(optionalComment.isPresent())
        {
            Comment comment =optionalComment.get();

            comment.setContent(commentDto.getContent());
            Comment returnedComment = commentRepository.save(comment);
            return modelMapper.map(returnedComment,CommentDto.class);

        }else{
            return null;
        }

    }

    @Override
    public void deleteById(Long id) {

        commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException()
        );
        commentRepository.deleteById(id);
    }
}
