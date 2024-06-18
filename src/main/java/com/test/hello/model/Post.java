package com.test.hello.model;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private int id;
    private String title;
    private String content;
    private String fileName;
    private int likes; // 좋아요 필드 추가
    private String authorName; // 작성자 이름 추가
    private List<Comment> comments;

    public Post() {

    }

    public Post(int id, String title, String content, String fileName, int likes, String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.likes = likes;
        this.authorName = authorName;
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // getters and setters

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
