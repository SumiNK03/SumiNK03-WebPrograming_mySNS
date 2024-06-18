package com.test.hello.controller;

import com.test.hello.model.Comment;
import com.test.hello.model.Post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


@Controller
public class CommentController {
    private final String URL = "jdbc:mysql://localhost:3306/testdb";
    private final String USER = "root";
    private final String PASSWORD = "0417";

    @PostMapping("/comments/create")
    public String createComment(@RequestParam("postId") int postId,
                                @RequestParam("content") String content,
                                @RequestParam("pageType") String pageType,
                                @RequestParam("authorName") String authorName) {
        if (authorName == null || authorName.trim().isEmpty() ) {
            authorName = "익명";
        }
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO comments (post_id, content, likes, author_name) VALUES (?, ?, 0, ?)")) {
            pstmt.setInt(1, postId);
            pstmt.setString(2, content);
            pstmt.setString(3, authorName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ("board".equals(pageType)) {
            return "redirect:/board";
        } else if ("followBoard".equals(pageType)) {
            return "redirect:/followBoard";
        } else {
            // 페이지 유형이 지정되지 않은 경우 처리
            return "errorPage"; // 예를 들어, 에러 페이지로 리다이렉트 또는 오류 메시지 반환
        }
    }

    @PostMapping("/comments/toggleLike")
    @ResponseBody
    public String toggleLike(@RequestParam("id") int id) {
        // 좋아요를 처리하여 likes 증가
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE comments SET likes = likes + 1 WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"success\": false}";
        }

        // 업데이트된 likes 값 반환
        int likes = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT likes FROM comments WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    likes = rs.getInt("likes");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"success\": false}";
        }

        return "{\"success\": true, \"likes\": " + likes + "}";
    }

    @PostMapping("/comments/toggleDislike")
    @ResponseBody
    public String toggleDislike(@RequestParam("id") int id) {
        // 싫어요를 처리하여 likes 감소 (0보다 작아지지 않도록)
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE comments SET likes = likes - 1 WHERE id = ? AND likes > 0")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"success\": false}";
        }

        // 업데이트된 likes 값 반환
        int likes = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT likes FROM comments WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    likes = rs.getInt("likes");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"success\": false}";
        }

        return "{\"success\": true, \"likes\": " + likes + "}";
    }

    @GetMapping("/board")
    public String board(Model model) {
    List<Post> posts = getAllPostsWithComments();
    Collections.reverse(posts);
    model.addAttribute("posts", posts);
    return "board";
    }

    @GetMapping("/followBoard")
public String followBoard(Model model, HttpSession session) {
    if((String) session.getAttribute("userName") == null) {
        return "follow_error";
    }
    String currentUser = (String) session.getAttribute("userName");

    // 현재 사용자가 팔로우하는 사용자들의 게시물만 필터링
    List<Post> allPosts = getAllPostsWithComments();
    Collections.reverse(allPosts);
    List<Post> filteredPosts = new ArrayList<>();

    for (Post post : allPosts) {
        // 게시물 작성자가 현재 사용자가 팔로우하는 사용자인지 확인
        if (isFollowing(currentUser, post.getAuthorName())) {
            filteredPosts.add(post);
        }
    }

    // 필터링된 게시물 목록을 모델에 추가
    model.addAttribute("posts", filteredPosts);

    return "followBoard";
}

    // 현재 사용자가 특정 사용자를 팔로우하는지 여부를 확인하는 메서드
private boolean isFollowing(String currentUser, String authorName) {
    boolean result = false;
    String query = "SELECT COUNT(*) AS count FROM follow WHERE follower_name = ? AND followee_name = ?";
    
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, currentUser);
        pstmt.setString(2, authorName);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    result = true; // 팔로우 중인 경우
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // 예외 처리: 데이터베이스 연결 오류 등
    }
    
    return result;
}
    

// 모든 게시물과 각 게시물의 댓글을 조회하는 메서드
private List<Post> getAllPostsWithComments() {
    List<Post> posts = new ArrayList<>();
    String postQuery = "SELECT * FROM posts";
    String commentQuery = "SELECT * FROM comments WHERE post_id = ?";
    
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement postStmt = conn.prepareStatement(postQuery);
         ResultSet postRs = postStmt.executeQuery()) {
        
        while (postRs.next()) {
            Post post = new Post();
            post.setId(postRs.getInt("id"));
            post.setTitle(postRs.getString("title"));
            post.setContent(postRs.getString("content"));
            post.setAuthorName(postRs.getString("author_name"));
            post.setLikes(postRs.getInt("likes"));
            post.setFileName(postRs.getString("file_name")); // 파일 이름 추가

            // 각 게시물에 대한 댓글 조회
            try (PreparedStatement commentStmt = conn.prepareStatement(commentQuery)) {
                commentStmt.setInt(1, post.getId());
                try (ResultSet commentRs = commentStmt.executeQuery()) {
                    List<Comment> comments = new ArrayList<>();
                    while (commentRs.next()) {
                        Comment comment = new Comment();
                        comment.setId(commentRs.getInt("id"));
                        comment.setPostId(commentRs.getInt("post_id"));
                        comment.setContent(commentRs.getString("content"));
                        comment.setLikes(commentRs.getInt("likes"));
                        comment.setAuthorName(commentRs.getString("author_name"));
                        comments.add(comment);
                    }
                    post.setComments(comments); // 게시물에 댓글 설정
                }
            }

            posts.add(post);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return posts;
}

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable("id") int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM comments WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/board";
    }
}
