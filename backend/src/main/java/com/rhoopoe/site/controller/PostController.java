package com.rhoopoe.site.controller;


import com.rhoopoe.site.dto.requests.PostDTO;
import com.rhoopoe.site.entity.Post;
import com.rhoopoe.site.exception.ImageProcessingException;
import com.rhoopoe.site.exception.PostNotFoundException;
import com.rhoopoe.site.mapper.PostMapper;
import com.rhoopoe.site.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("{uuid}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID uuid) throws PostNotFoundException {
        Post post = postService.getPostById(uuid);
        return ResponseEntity.ok().body(post);
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(defaultValue = "1") @Min(0) Integer page,
                                                  @RequestParam(defaultValue = "10") @Min(0) Integer limit,
                                                  @RequestParam(defaultValue = "createdAt") String sortBy,
                                                  @RequestParam(defaultValue = "false") boolean sortDesc,
                                                  @RequestParam(required = false) String contains){
        Direction direction = sortDesc ? Direction.DESC : Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        return ResponseEntity.ok().body(postService.getAllPosts(pageRequest, contains));
    }

    // TODO: create post endpoint should receive multipart request
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody @Valid PostDTO postDTO) throws IOException,
            ImageProcessingException {
        Post createdPost = postService.createPost(postDTO);
        return ResponseEntity.created(URI.create(createdPost.getUuid().toString())).body(createdPost);
    }
    @PutMapping("{uuid}")
    public ResponseEntity<Post> updatePost(@RequestBody @Valid PostDTO postDTO,
                                           @PathVariable UUID uuid) throws IOException, ImageProcessingException {
        Post updatedPost = postService.updatePost(postDTO, uuid);
        return ResponseEntity.ok().body(updatedPost);
    }
    @DeleteMapping("{uuid}")
    public ResponseEntity<String> deletePost(@PathVariable UUID uuid) throws IOException, PostNotFoundException {
        postService.deletePost(uuid);
        return ResponseEntity.noContent().build();
    }
}
