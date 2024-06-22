package com.test.hello.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.hello.model.LoginBean;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyController {

    private final LoginBean loginBean;

    String url = "jdbc:mysql://localhost:3306/testdb";
    String dbUsername = "root";
    String dbPassword = "0417";

    @Autowired
    public MyController(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @GetMapping("/")
    public String homee() {
        return "home";
    }
    

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // 로그인 폼 페이지
    }

    @PostMapping("/login")
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {
        LoginBean bean = new LoginBean();
        bean.setName(name);
        bean.setPassword(password);

        if (bean.validate()) {
            session.setAttribute("userName", bean.getName()); // 세션에 사용자 이름 저장
            return "login_success"; // 로그인 성공 
        } else {
            model.addAttribute("loginError", "이름 또는 비밀번호가 잘못되었습니다.");
            return "login_error"; // 로그인 실패 
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/"; 
    }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "sign_up"; // 회원가입 폼 페이지
    }

    @PostMapping("/signUp")
    public String signUp(@RequestParam("name") String name,
                         @RequestParam("password") String password,
                         Model model) {

        if (name.equals("익명") || !checkUserName(name)){
            return "sign_up_error";
        }

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO student (name, password) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, password);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "sign_up"; // 회원가입 폼 페이지
        }
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String userList(Model model, HttpSession session) {
    
        List<String> users = new ArrayList<>();
        List<Boolean> isfollow = new ArrayList<>();
        String currentUser = (String) session.getAttribute("userName");
    
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT name FROM student";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String userName = resultSet.getString("name");
                    users.add("'" + userName + "'");
                    isfollow.add(isFollowing(currentUser, userName, conn));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error"; // 오류 페이지
        }

        model.addAttribute("isfollowing", isfollow);
        model.addAttribute("users", users);
    
        return "users";
    }

    private boolean checkUserName(String name) {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT COUNT(*) FROM student WHERE name = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return false; // 중복 사용자 이름이 존재함
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true; // 중복 사용자 이름이 없음
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

    @GetMapping("/error")
    public String ErrorPage(@RequestParam String param) {
        return "error";
    }
    
    
}
