package com.myblog.demo.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class PostDTO {
    private  Long id;

    @NotEmpty
    @Size(min = 2, message = "the title must be 2 characters or more")
    private  String title;

    @NotEmpty
    @Size(min = 10, message = "the description must be 2 characters or more")
    private  String description;

    @NotEmpty
    private  String content;

    private Set<CommentDTO> comments;

    public PostDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }
}
