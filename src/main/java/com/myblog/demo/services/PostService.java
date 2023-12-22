package com.myblog.demo.services;

import com.myblog.demo.dtos.PostDTO;
import com.myblog.demo.dtos.PostResponse;

public interface PostService {
    public PostDTO save(PostDTO postDTO);
    public PostResponse getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
    public PostDTO getById(Long id);
    public  PostDTO updatePost(PostDTO postDTO, Long id);
    public void deletePost(Long id);
}