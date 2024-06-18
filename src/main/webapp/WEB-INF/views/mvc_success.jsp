<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Success</title>
    <style>

        /* 로그인 성공 컨테이너 스타일 */
        .loginSuccessContainer {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 300px;
            text-align: center;
            margin-right: auto;
            margin-left: auto;
            margin-top: 50px;
        }

        .loginSuccessContainer h1 {
            margin-bottom: 20px;
        }

        .loginSuccessContainer p {
            margin-bottom: 10px;
        }

        .loginSuccessContainer a {
            display: block;
            margin-top: 20px;
            color: #007bff;
            text-decoration: none;
        }

        .loginSuccessContainer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <%@ include file="nav.jsp"%>
    <div class="loginSuccessContainer">
        <h1>Login Successful!</h1>
        <p>환영합니다, <%= session.getAttribute("userName") %>님!</p>
        <a href="/">메인으로</a>
    </div>
</body>
</html>
