<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.test.hello.model.Post" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시물 수정</title>
    <style>

        /* 글 쓰기 컨테이너 스타일 */
        .writeContainer {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 80%;
            text-align: center;
            margin-top:50px;
            margin-left:auto;
            margin-right:auto;
        }

        .writeContainer h1 {
            margin-bottom: 20px;
        }

        .writeContainer form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .writeContainer form input[type="file"],
        .writeContainer form input[type="text"],
        .writeContainer form textarea {
            width: calc(100% - 20px);
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid lightgray;
            border-radius: 4px;
            background-color: whitesmoke;
            margin-top: 20px;
        }

        .writeContainer form textarea {
            min-height: 150px; /* 최소 높이 설정 */
            max-width: calc(100% - 20px);
            min-width: calc(100% - 20px);
        }

        .writeContainer form input[type="submit"] {
            padding: 10px 20px;
            background-color: lightgray;
            border: solid lightgray 3px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            width: calc(100% - 20px);
        }

        .writeContainer form input[type="submit"]:hover {
            background-color: gray;
            border: solid gray 3px;
            color: white; /* 마우스 오버 시 색상 변경 */
        }

        .writeContainer a {
            display: block;
            margin-top: 10px;
            color: #007bff;
            text-decoration: none;
        }

        .writeContainer a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>
<%@ include file="nav.jsp"%>
<div class="writeContainer">
    <h1>게시물 수정</h1>
    <form action="editPost" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${post.id}">
        제목 <input type="text" name="title" value="${post.title}"><br>
        내용 <textarea name="content">${post.content}</textarea><br>
        <p>첨부 파일 <a href="downloadFile?id=${post.id}">${post.fileName}</a></p>
        <p>새로운 파일 선택 <input type="file" name="newFile"></p>
        <p>*첨부 파일명은 한글을 포함할 수 없습니다.*</p>
        <input type="submit" value="수정">
    </form>
    <a href="board">목록으로</a>
    </div>
</body>
</html>
