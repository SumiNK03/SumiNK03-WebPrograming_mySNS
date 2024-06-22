package com.test.hello.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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
