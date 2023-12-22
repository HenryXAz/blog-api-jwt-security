package com.myblog.demo.services;

import com.myblog.demo.dtos.CommentDTO;
import com.myblog.demo.models.Comment;

import java.util.List;

public interface CommentService {
    public CommentDTO saveComment(Long postId, CommentDTO commentDTO);
    public List<CommentDTO> getCommentsByPostId(Long postId);
    public  CommentDTO getCommentByPostId(Long postId, Long commentId);
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentUpdated);
    public  void deleteComment(Long postId, Long commentId);
}
