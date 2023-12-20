package com.rhoopoe.site.controllers;


import com.rhoopoe.site.DTOs.PostDTO;
import com.rhoopoe.site.entities.Post;
import com.rhoopoe.site.mappers.PostMapper;
import com.rhoopoe.site.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer limit,
                                                  @RequestParam(defaultValue = "createdAt") String sortBy,
                                                  @RequestParam(defaultValue = "false") String sortDesc,
                                                  @RequestParam(required = false) String contains){
        if (page < 1 || limit < 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (!sortDesc.equals("true") && !sortDesc.equals("false")){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        page--;
        Direction direction = Boolean.parseBoolean(sortDesc) ? Direction.DESC : Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        return new ResponseEntity<>(postService.getAllPosts(pageRequest, contains), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO){
        Post post = PostMapper.dtoToEntity(postDTO);
        Post createdPost = postService.createPost(post);
        if (createdPost == null){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @PutMapping("{uuid}")
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO postDTO,
                                           @PathVariable UUID uuid){
        Post updatedPost = postService.updatePost(PostMapper.dtoToEntity(postDTO), uuid);
        if (updatedPost == null){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedPost, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("{uuid}")
    public ResponseEntity<String> deletePost(@PathVariable UUID uuid){
        postService.deletePost(uuid);
        try {
            postService.getPostById(uuid);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>("Post deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not delete post", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}