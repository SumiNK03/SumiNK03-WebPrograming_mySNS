package com.test.hello.controller;

import com.test.hello.model.Post;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {
    private final String URL = "jdbc:mysql://localhost:3306/testdb";
    private final String USER = "root";
    private final String PASSWORD = "0417";

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ServletContext servletContext;

    @GetMapping("/writePost")
    public String writePost() {
        return "writePost";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("authorName") String authorName) {
        String fileName = "";
        if (!file.isEmpty()) {
            fileName = file.getOriginalFilename();
        }
        if (authorName == null || authorName.trim().isEmpty()) {
            authorName = "익명";
        }
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO posts (title, content, file_name, author_name, likes) VALUES (?, ?, ?, ?, 0)")) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, fileName);
            pstmt.setString(4, authorName); // 작성자 이름 추가
            pstmt.executeUpdate();

            // 파일 업로드 처리 코드
            if (!file.isEmpty()) {
                String uploadDir = servletContext.getRealPath("/") + "uploads";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                file.transferTo(new File(uploadDirFile, fileName));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return "redirect:/board";
    }

    @GetMapping("/editPost")
    public String editPost(@RequestParam("id") int id, Model model) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM posts WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Post post = new Post(rs.getInt("id"), rs.getString("title"),
                                         rs.getString("content"), rs.getString("file_name"),
                                         rs.getInt("likes"), rs.getString("author_name"));
                    model.addAttribute("post", post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "editPost";
    }

    @PostMapping("/editPost")
    public String editPost(
            @ModelAttribute("post") Post updatedPost,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "newFile", required = false) MultipartFile file,
            Model model
    ) {
        if (id == null) {
            // id 파라미터가 없는 경우 처리
            model.addAttribute("error", "게시물 ID가 필요합니다.");
            return "editPost";
        }
    
        String fileName = updatedPost.getFileName(); // 기존 파일명 가져오기
        if (file != null && !file.isEmpty()) {
            fileName = file.getOriginalFilename();
            try {
                String uploadDir = servletContext.getRealPath("/") + "uploads"; // servletContext로 파일 경로 가져오기
    
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
    
                file.transferTo(new File(uploadDirFile, fileName)); // 파일 업로드
            } catch (IOException e) {
                e.printStackTrace();
                // 파일 업로드 실패 시 처리
                model.addAttribute("error", "파일 업로드 실패");
                return "editPost";
            }
        }
    
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE posts SET title = ?, content = ?, file_name = ? WHERE id = ?")) {
            pstmt.setString(1, updatedPost.getTitle());
            pstmt.setString(2, updatedPost.getContent());
            pstmt.setString(3, fileName);
            pstmt.setInt(4, id); // id 파라미터 사용
            pstmt.executeUpdate(); // 게시물 업데이트
        } catch (SQLException e) {
            e.printStackTrace();
            // 데이터베이스 업데이트 실패 시 처리
            model.addAttribute("error", "데이터베이스 업데이트 실패");
            return "editPost";
        }
    
        return "redirect:/board"; // 게시판 페이지로 리다이렉트
    }
    

@GetMapping("/downloadFile")
public void downloadFile(@RequestParam("id") int id, HttpServletResponse response) {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement("SELECT file_name FROM posts WHERE id = ?")) {
        pstmt.setInt(1, id);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                String fileName = rs.getString("file_name");
                String uploadPath = servletContext.getRealPath("/") + "uploads";
                File file = new File(uploadPath, fileName);
                if (file.exists()) {
                    // 다운로드할 파일 이름 설정
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                    // 파일 내용을 response 스트림으로 전송
                    try (InputStream inStream = new FileInputStream(file); OutputStream outStream = response.getOutputStream()) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inStream.read(buffer)) != -1) {
                            outStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    } catch (SQLException | IOException e) {
        e.printStackTrace();
    }
}



    @PostMapping("/toggleLike")
    @ResponseBody
    public String toggleLike(@RequestParam("id") int id) {
        // 좋아요를 처리하여 likes 증가
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE posts SET likes = likes + 1 WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"success\": false}";
        }

        // 업데이트된 likes 값 반환
        int likes = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT likes FROM posts WHERE id = ?")) {
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

    @PostMapping("/toggleDislike")
    @ResponseBody
    public String toggleDislike(@RequestParam("id") int id) {
        // 싫어요를 처리하여 likes 감소 (0보다 작아지지 않도록)
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE posts SET likes = likes - 1 WHERE id = ? AND likes > 0")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"success\": false}";
        }

        // 업데이트된 likes 값 반환
        int likes = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT likes FROM posts WHERE id = ?")) {
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

    @GetMapping("/deletePost")
public String deletePost(@RequestParam("id") int id) {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        // 포스트 삭제
        try (PreparedStatement pstmtPost = conn.prepareStatement("DELETE FROM posts WHERE id = ?")) {
            pstmtPost.setInt(1, id);
            pstmtPost.executeUpdate();
        }

        // 댓글 삭제
        try (PreparedStatement pstmtComment = conn.prepareStatement("DELETE FROM comments WHERE post_id = ?")) {
            pstmtComment.setInt(1, id);
            pstmtComment.executeUpdate();
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("삭제 실패");
        // 삭제 실패 시 처리 (로그 추가 등)
    }

    return "redirect:/board"; // 삭제 후 게시판 페이지로 리다이렉트
}


}
