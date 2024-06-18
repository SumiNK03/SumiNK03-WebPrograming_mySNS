<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
    <title>MVC</title>
    <style>

        /* 로그인 폼 스타일 */
        .loginForm {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 300px;
            margin-right: auto;
            margin-left: auto;
            margin-top: 50px;
        }

        .loginForm h2 {
            margin-bottom: 20px;
            text-align: center;
        }

        .loginForm p {
            margin-bottom: 10px;
        }

        .loginForm input[type="text"],
        .loginForm input[type="password"] {
            width: calc(100% - 20px);
            padding: 8px;
            border: 1px solid lightgray;
            border-radius: 4px;
            background-color: whitesmoke;
        }

        .loginForm input[type="submit"] {
            width: calc(100% - 20px);
            padding: 10px 20px;
            background-color: lightgray;
            border: solid lightgray 3px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .loginForm input[type="submit"]:hover {
            background-color: gray;
            border: solid gray 3px;
            color: white;
        }

        .loginLink {
            margin-top: 10px;
            text-align: center;
        }

        .loginLink a {
            color: #007bff;
            text-decoration: none;
        }

        .loginLink a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <%@ include file="nav.jsp"%>
    <form method="post" action="/Controller" class="loginForm">
        <h2>로그인</h2>
        <p>
            <input type="text" name="name" placeholder="아이디" required>
        </p>
        <p>
            <input type="password" name="password" placeholder="비밀번호" required>
        </p>
        <p>
            <input type="submit" value="로그인">
        </p>
    </form>
    <div class="loginLink">
        <p>계정이 없으신가요? <a href="/signUp">회원가입</a></p>
    </div>
</body>
</html>
