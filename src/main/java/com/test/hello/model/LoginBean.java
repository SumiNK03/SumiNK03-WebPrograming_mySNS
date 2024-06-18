package com.test.hello.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class LoginBean {
    private String name; // 사용자의 이름
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validate() {
        String dbPassword = null;

        // DB 연결 정보
        String URL = "jdbc:mysql://localhost:3306/testdb";
        String USER = "root";
        String PASSWORD = "0417";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Database connection established.");

            String sql = "SELECT password FROM student WHERE name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);

                System.out.println("Executing SQL query: " + sql);
                System.out.println("Setting parameter name: " + name);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        dbPassword = rs.getString("password");
                        System.out.println("Retrieved password from database: " + dbPassword);
                    } else {
                        System.out.println("No user found with name: " + name);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection or query execution error: " + e.getMessage());
            return false; // DB 연결 오류 등으로 인한 실패 처리
        }

        // 비밀번호 비교
        if (dbPassword != null && dbPassword.equals(password)) {
            System.out.println("Password matches. Login successful.");
            return true; // 비밀번호가 일치할 경우 로그인 성공
        } else {
            System.out.println("Password does not match. Login failed.");
            return false; // 비밀번호가 일치하지 않을 경우 로그인 실패
        }
    }
}
