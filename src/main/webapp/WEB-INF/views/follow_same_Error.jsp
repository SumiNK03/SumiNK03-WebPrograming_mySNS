<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cannot Follow</title>
    <style>
        .cover {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 350px;
            text-align: center;
            margin-right: auto;
            margin-left: auto;
            margin-top: 50px;
        }

        .cover h1 {
            margin-bottom: 20px;
        }

        .cover p {
            margin-bottom: 10px;
        }

        .cover a {
            display: block;
            margin-top: 20px;
            color: #007bff;
            text-decoration: none;
        }

        .cover a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>
    <%@ include file="nav.jsp"%>
    <div class="cover">
        <h1>팔로우 에러</h1>
        <p>자기 자신은 팔로우할 수 없습니다.</p>
        <a href="/users">사용자 목록으로</a>
    </div>
</body>
</html>
