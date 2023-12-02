package com.rhoopoe.site.controllers;


import com.rhoopoe.site.DTOs.PostDTO;
import com.rhoopoe.site.entities.Post;
import com.rhoopoe.site.mappers.PostMapper;
import com.rhoopoe.site.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("posts")
public class TestController {
    private final PostService postService;

    public TestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("test")
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>("It works!",HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer limit){
        if (page < 1 || limit < 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        page--;
        System.out.println(page);
        System.out.println(limit);
        PageRequest pageRequest = PageRequest.of(page, limit);
        return new ResponseEntity<>(postService.getAllPosts(pageRequest), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO){
        Post post = PostMapper.dtoToEntity(postDTO);
        Post createdPost = postService.createPost(post);
        if (createdPost == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable UUID uuid){
        postService.deletePost(uuid);
    }
}
