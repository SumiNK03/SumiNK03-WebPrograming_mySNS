package com.test.hello.model;

public class Comment {
    private int id;
    private int postId;
    private String content;
    private int likes;
    private String authorName;
    
    public Comment() {
        
    }

    public Comment(int id, int postId, String content, int likes, String authorName) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.likes = likes;
        this.authorName = authorName;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
