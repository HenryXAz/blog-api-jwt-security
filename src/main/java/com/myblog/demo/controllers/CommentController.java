package com.myblog.demo.controllers;

import com.myblog.demo.dtos.CommentDTO;
import com.myblog.demo.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{post_id}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable("post_id") Long postId)
    {
        return this.commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{post_id}/comments/{comment_id}")
    public  ResponseEntity<CommentDTO> getCommentByPostId(
            @PathVariable("post_id") Long postId,
            @PathVariable("comment_id") Long commentId
    )
    {
        return ResponseEntity.ok(this.commentService.getCommentByPostId(postId, commentId));
    }

    @PostMapping("/posts/{post_id}/comments")
    public ResponseEntity<CommentDTO> saveComment(@PathVariable("post_id") Long postId, @Valid @RequestBody CommentDTO commentDTO)
    {
        return ResponseEntity.ok(this.commentService.saveComment(postId, commentDTO));
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable(value="postId") Long postId,
            @PathVariable(value="id") Long commentId,
            @Valid @RequestBody CommentDTO commentUpdated
    )
    {
        return ResponseEntity.ok(this.commentService.updateComment(postId, commentId, commentUpdated));
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value="postId") Long postId,
            @PathVariable(value="id") Long commentId
    )
    {
        commentService.deleteComment(postId, commentId);

        return  ResponseEntity.ok("The comment was successfully deleted");
    }
}