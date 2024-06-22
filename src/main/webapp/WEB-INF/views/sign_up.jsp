<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
    <title>회원가입</title>
    <style>

        .signUpForm {
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            margin-right: auto;
            margin-left: auto;
            margin-top: 50px;
        }

        .signUpForm h2 {
            margin-bottom: 20px;
        }

        .signUpForm p {
            margin: 10px 0;
            width: 100%;
        }

        .signUpForm label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .signUpForm input[type="text"], 
        .signUpForm input[type="password"] {
            width: calc(100% - 20px);
            padding: 8px;
            border: 1px solid lightgray;
            border-radius: 4px;
            background-color: whitesmoke;
        }

        .signUpForm input[type="submit"] {
            padding: 10px 20px;
            background-color: lightgray;
            border: solid lightgray 3px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            width: calc(100% - 20px);
        }

        .signUpForm input[type="submit"]:hover {
            background-color: gray;
            border: solid gray 3px;
            color:white; /* 마우스 오버 시 색상 변경 */
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
    <form method="post" action="/signUp" class="signUpForm" onsubmit="return check()">
        <h2>회원가입</h2>
        <p>
            <label for="name">사용자 이름:</label>
            <input type="text" id="name" name="name" required placeholder="user name">
        </p>
        <p>
            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required placeholder="password">
        </p>
        <p>
            <input type="submit" value="회원가입" onclick="check()">
        </p>
        <div class="loginLink">
            <p>계정이 있으신가요? <a href="/login">로그인</a></p>
        </div>
    </form>
</body>
</html>
