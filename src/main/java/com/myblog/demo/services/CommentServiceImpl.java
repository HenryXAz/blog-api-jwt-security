package com.myblog.demo.services;

import com.myblog.demo.dtos.CommentDTO;
import com.myblog.demo.dtos.PostResponse;
import com.myblog.demo.exceptions.BlogAppException;
import com.myblog.demo.exceptions.ResourceNotFoundException;
import com.myblog.demo.models.Comment;
import com.myblog.demo.models.Post;
import com.myblog.demo.repositories.CommentRepository;
import com.myblog.demo.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements  CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    private  CommentDTO mappingDTO(Comment comment)
    {
        return this.modelMapper.map(comment, CommentDTO.class);
    }

    private  Comment mappingEntity(CommentDTO commentDTO)
    {
        return this.modelMapper.map(commentDTO, Comment.class);
    }
    @Override
    public CommentDTO saveComment(Long postId, CommentDTO commentDTO) {
        Comment comment = this.mappingEntity(commentDTO);
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "id",
                        postId
                ));
        comment.setPost(post);
        Comment newComment = this.commentRepository.save(comment);
        return this.mappingDTO(newComment);
    }



    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = this.commentRepository.findByPostId(postId);
        return comments.stream().map(this::mappingDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentByPostId(Long postId, Long commentId) {
       Post post = this.postRepository.findById(postId)
               .orElseThrow(() -> new ResourceNotFoundException(
                       "Post",
                       "id",
                       postId
               ));
       Comment comment = this.commentRepository.findById(commentId)
               .orElseThrow(() -> new ResourceNotFoundException(
                       "Comment",
                       "id",
                       commentId
               ));

       if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "The comment does not belong to the post");
       }

       return this.mappingDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentUpdated) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "id",
                        postId
                ));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment",
                        "id",
                        commentId
                ));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a este post");
        }

        comment.setName(commentUpdated.getName());
        comment.setBody(commentUpdated.getBody());
        comment.setEmail(commentUpdated.getEmail());

        Comment commentUpdate = commentRepository.save(comment);

        return mappingDTO(commentUpdate);
    }

    public  void deleteComment(Long postId, Long commentId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "id",
                        postId
                ));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment",
                        "id",
                        commentId
                ));

        if(!comment.getPost().getId().equals(postId)) {
            throw  new BlogAppException(HttpStatus.BAD_REQUEST, "The comment does not belog to the post");
        }

        commentRepository.delete(comment);
    }
}
