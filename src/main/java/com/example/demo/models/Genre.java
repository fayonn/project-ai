package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "posts"})
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String genreName;

    // Тупо ManyToMany
    @JsonIgnore
    @ManyToMany(mappedBy = "genres",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Post> posts = new HashSet<>();

    public Genre(){

    }

    public Genre(String genre){
        this.genreName = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genre) {
        this.genreName = genre;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}