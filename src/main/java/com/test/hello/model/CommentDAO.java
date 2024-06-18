package com.test.hello.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private final String URL = "jdbc:mysql://localhost:3306/testdb";
    private final String USER = "root";
    private final String PASSWORD = "0417";

    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM comments WHERE post_id = ?")) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment(rs.getInt("id"),
                                                  rs.getInt("post_id"),
                                                  rs.getString("content"),
                                                  rs.getInt("likes"),
                                                  rs.getString("author_name"));
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
