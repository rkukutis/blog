package com.rhoopoe.site.controllers;


import com.rhoopoe.site.DTOs.PostDTO;
import com.rhoopoe.site.entities.Post;
import com.rhoopoe.site.mappers.PostMapper;
import com.rhoopoe.site.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:5173")
public class PostController {
    private final PostService postService;
    @GetMapping("{uuid}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID uuid){
        Post post = postService.getPostById(uuid);
        return ResponseEntity.ok().body(post);
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
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) throws IOException {
        Post post = PostMapper.dtoToEntity(postDTO);
        byte[] thumbnailBytes = Base64.getDecoder().decode(postDTO.getThumbnailBase64());
        Post createdPost = postService.createPost(post, thumbnailBytes);
        if (createdPost == null){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @PutMapping("{uuid}")
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO postDTO,
                                           @PathVariable UUID uuid) throws IOException {
        byte[] thumbnailBytes = Base64.getDecoder().decode(postDTO.getThumbnailBase64());
        System.out.println("THUMBNAIL BYTES: " + thumbnailBytes.length);
        Post updatedPost = postService.updatePost(PostMapper.dtoToEntity(postDTO), uuid, thumbnailBytes);
        if (updatedPost == null){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedPost, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("{uuid}")
    public ResponseEntity<String> deletePost(@PathVariable UUID uuid) throws IOException {
        postService.deletePost(uuid);
        try {
            postService.getPostById(uuid);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>("Post deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not delete post", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
