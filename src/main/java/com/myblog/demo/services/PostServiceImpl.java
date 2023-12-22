package com.myblog.demo.services;

import com.myblog.demo.dtos.PostDTO;
import com.myblog.demo.dtos.PostResponse;
import com.myblog.demo.exceptions.ResourceNotFoundException;
import com.myblog.demo.models.Post;
import com.myblog.demo.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements  PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    private  PostDTO mappingDto(Post post)
    {
        return this.modelMapper.map(post, PostDTO.class);
    }

    private  Post mappingEntity(PostDTO postDTO)
    {
        return this.modelMapper.map(postDTO, Post.class);
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        Post post = this.mappingEntity(postDTO);
        Post newPost = this.postRepository.save(post);
        return this.mappingDto(newPost);
    }

    @Override
    public PostResponse getAll(
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> posts = this.postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDTO> content = postList.stream().map(this::mappingDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalItems(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getById(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "id",
                        id
                ));
        return this.mappingDto(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "id",
                        id
                ));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post PostUpdated = this.postRepository.save(post);
        return this.mappingDto(PostUpdated);
    }

    @Override
    public void deletePost(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "id",
                        id
                ));
        this.postRepository.delete(post);
    }
}