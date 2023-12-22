package com.myblog.demo.repositories;

import com.myblog.demo.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
