package com.blog.blog.service;

import com.blog.blog.dto.PostDTO;
import com.blog.blog.model.Post;
import com.blog.blog.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class PostServiceImpl implements PostService {


    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<PostDTO> getAll() {
        log.info(" Getting 	All Post DTO");
        List<Post> returnedPost = postRepository.findAll();

        if (!returnedPost.isEmpty()) {
            returnedPost.stream().forEach(post -> post.getComments().stream().forEach(
                    comment -> comment.setPost(null)
            ));
            return returnedPost.stream().
                    map(entity -> modelMapper.map(entity, PostDTO.class)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public PostDTO getById(Long id) {
        log.info(" Getting 	 Post By ID");
        Optional<Post> returnedPost = postRepository.findById(id);
        if (returnedPost.isPresent()) {

            Post post = returnedPost.get();

            post.getComments().stream().forEach(comment -> {
                comment.setPost(null);
            });
            return modelMapper.map(post, PostDTO.class);
        } else {
            return null;
//            throw new ResourceNotFoundException("Post", "ID", id);
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info(" Deleting  Post Based On Id");

        postRepository.findById(id).orElseThrow(() ->
                        new NullPointerException()
//                new ResourceNotFoundException("Post",
//                "id", id)
        );
        postRepository.deleteById(id);
    }

    @Override
    public PostDTO create(PostDTO postdto) {
        log.info("Converting w DTO TO Post");


        Post post = modelMapper.map(postdto,
                Post.class);


        Post returnedPost = postRepository.save(post);

        return modelMapper.map(returnedPost,
                PostDTO.class);
    }

    @Override
    public PostDTO updateById(PostDTO postDTO, Long id) {
        log.info(" Converting Post DTO To Post");

        Post post = modelMapper.map(postDTO,
                Post.class);
        log.info(" Finding post based on ID");
        postRepository.findById(id).
                orElseThrow(() ->
                                new NullPointerException()
//                        new ResourceNotFoundException("Post", "id", id)
                );

        log.info(" Saving Post ");
        Post reTurnedPost = postRepository.save(post);

        return modelMapper.map(reTurnedPost,
                PostDTO.class);
    }
}

