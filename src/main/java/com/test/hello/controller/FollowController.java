package com.test.hello.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FollowController {

    // JDBC 연결 설정
    private final String url = "jdbc:mysql://localhost:3306/testdb";
    private final String dbUsername = "root";
    private final String dbPassword = "0417";

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/follow")
    public String followUser(@RequestParam("followeeName") String followeeName, Model model) {
        // 현재 로그인된 사용자 이름 가져오기
        String followerName = (String) httpSession.getAttribute("userName");
        
        // userName이 null이면 팔로우 동작 수행하지 않음
        if (followerName == null) {
            return "follow_error"; // 에러 페이지로 리턴
        }

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // 팔로우 여부 확인
            boolean isFollowing = isFollowing(followerName, followeeName, conn);

            if (!isFollowing) {
                // 팔로우 관계 추가
                String sql = "INSERT INTO follow (follower_name, followee_name) VALUES (?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, followerName);
                    statement.setString(2, followeeName);
                    statement.executeUpdate();
                }
            } else {
                // 이미 팔로우 중인 경우, 팔로우 취소
                String sql = "DELETE FROM follow WHERE follower_name = ? AND followee_name = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, followerName);
                    statement.setString(2, followeeName);
                    statement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error", "팔로우 동작 중 오류가 발생하였습니다.");
            return "follow_error"; // 에러 페이지로 리턴
        }

        // 팔로우 동작 성공 시 사용자 프로필 페이지로 리다이렉트
        return "redirect:/followBoard";
    }

    // 팔로우 여부 확인 메서드
    private boolean isFollowing(String followerName, String followeeName, Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM follow WHERE follower_name = ? AND followee_name = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, followerName);
            statement.setString(2, followeeName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        return false;
    }
}
