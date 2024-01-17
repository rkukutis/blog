package com.rhoopoe.site.controller;


import com.rhoopoe.site.dto.requests.PostDTO;
import com.rhoopoe.site.entity.Post;
import com.rhoopoe.site.exception.PostNotFoundException;
import com.rhoopoe.site.mapper.PostMapper;
import com.rhoopoe.site.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:5173/")
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
    public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer limit,
                                                  @RequestParam(defaultValue = "createdAt") String sortBy,
                                                  @RequestParam(defaultValue = "false") boolean sortDesc,
                                                  @RequestParam(required = false) String contains){
        page--;
        Direction direction = sortDesc ? Direction.DESC : Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        return ResponseEntity.ok().body(postService.getAllPosts(pageRequest, contains));
    }
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) throws IOException {
        Post post = PostMapper.dtoToEntity(postDTO);
        byte[] thumbnailBytes = Base64.getDecoder().decode(postDTO.getThumbnailBase64());
        Post createdPost = postService.createPost(post, thumbnailBytes);
        return ResponseEntity.created(URI.create(createdPost.getUuid().toString())).body(createdPost);
    }
    @PutMapping("{uuid}")
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO postDTO,
                                           @PathVariable UUID uuid) throws IOException {
        byte[] thumbnailBytes = Base64.getDecoder().decode(postDTO.getThumbnailBase64());
        return ResponseEntity.ok().body(postService.updatePost(PostMapper.dtoToEntity(postDTO), uuid, thumbnailBytes));
    }
    @DeleteMapping("{uuid}")
    public ResponseEntity<String> deletePost(@PathVariable UUID uuid) throws IOException, PostNotFoundException {
        postService.deletePost(uuid);
        return ResponseEntity.noContent().build();
    }
}
