package com.myblog.demo.controllers;

import com.myblog.demo.dtos.PostDTO;
import com.myblog.demo.dtos.PostResponse;
import com.myblog.demo.services.PostService;
import com.myblog.demo.utilities.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<PostResponse> getAll(
            @RequestParam(
                    value = "page_no",
                    defaultValue = AppConstants.PAGE_NUMBER_DEFAULT,
                    required = false
            ) int pageNumber,
            @RequestParam(
                    value = "page_size",
                    defaultValue = AppConstants.PAGE_SIZE_DEFAULT,
                    required = false
            ) int pageSize,
            @RequestParam(
                    value = "sort_by",
                defaultValue = AppConstants.ORDER_BY_DEFAULT,
                    required = false
            ) String sortBy,
            @RequestParam(
                    value = "sort_dir",
                    defaultValue = AppConstants.ORDER_DIR_DEFAULT,
                    required = false
            ) String sortDir
            )
    {
        return ResponseEntity.ok(this.postService.getAll(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(this.postService.getById(id));
    }


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> save(@Valid @RequestBody PostDTO postDTO)
    {
        return ResponseEntity.ok(this.postService.save(postDTO));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<PostDTO> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody PostDTO postDTO
    )
    {
        return ResponseEntity.ok(this.postService.updatePost(postDTO, id));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<String> deletePost(@PathVariable("id") Long id)
    {
        this.postService.deletePost(id);
        return ResponseEntity.ok("Post " + id + " was deleted successfully");
    }
}