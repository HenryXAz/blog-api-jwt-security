package com.myblog.demo.repositories;

import com.myblog.demo.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPostId(Long postId);
}