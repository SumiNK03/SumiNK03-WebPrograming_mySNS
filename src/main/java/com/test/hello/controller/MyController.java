package com.test.hello.controller;

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
import java.sql.Connection;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyController {

    private final LoginBean loginBean;

    @Autowired
    public MyController(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @GetMapping("/")
    public String homee() {
        return "home";
    }
    

    @GetMapping("/mvc")
    public String loginForm() {
        return "mvc"; // 로그인 폼 페이지
    }

    @PostMapping("/Controller")
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {
        LoginBean bean = new LoginBean();
        bean.setName(name);
        bean.setPassword(password);

        if (bean.validate()) {
            session.setAttribute("userName", bean.getName()); // 세션에 사용자 이름 저장
            return "mvc_success"; // 로그인 성공 시 리다이렉트
        } else {
            model.addAttribute("loginError", "이름 또는 비밀번호가 잘못되었습니다.");
            return "mvc_error"; // 로그인 실패 시 에러 페이지
        }
    }

    @GetMapping("/mvc_out")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // 로그인 화면으로 리다이렉트
    }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "sign_up"; // 회원가입 폼 페이지
    }

    @PostMapping("/signUp")
    public String signUp(@RequestParam("name") String name,
                         @RequestParam("password") String password,
                         Model model) {

        // JDBC를 사용하여 회원 정보를 데이터베이스에 저장
        String url = "jdbc:mysql://localhost:3306/testdb";
        String dbUsername = "root";
        String dbPassword = "0417";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO student (name, password) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, password);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("signUpError", "회원 가입 중 오류가 발생하였습니다.");
            return "sign_up"; // 회원가입 폼 페이지로 리턴
        }

        // 회원가입 성공 시 로그인 페이지로 리다이렉트
        return "redirect:/mvc";
    }

    @GetMapping("/users")
    public String userList(Model model) {
    
        // JDBC를 사용하여 DB에서 사용자 목록을 조회
        String url = "jdbc:mysql://localhost:3306/testdb";
        String dbUsername = "root";
        String dbPassword = "0417";
    
        List<String> users = new ArrayList<>();
    
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT name FROM student";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String userName = resultSet.getString("name");
                    // 각 사용자 이름을 따옴표로 감싸서 리스트에 추가
                    users.add("'" + userName + "'");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("usersError", "사용자 목록을 불러오는 중 오류가 발생하였습니다.");
            return "error"; // 오류 페이지로 리턴
        }
    
        // 모델에 사용자 목록 추가
        model.addAttribute("users", users);
    
        // 사용자 목록 페이지로 이동
        return "users";
    }
    
}
