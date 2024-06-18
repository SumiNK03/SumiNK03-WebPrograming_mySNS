<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.JSONObject" %>
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
    <script>
        $(document).ready(function () {
            $('#name').on('input', function () {
                var userName = $(this).val();
                $.ajax({
                    url: '<%= request.getContextPath() %>/signUp',
                    method: 'GET',
                    data: { checkName: userName },
                    success: function (response) {
                        var data = JSON.parse(response);
                        if (data.isDuplicate) {
                            alert('이 사용자 이름은 이미 존재하거나 "익명"일 수 없습니다.');
                            $('#name').focus();
                        }
                    }
                });
            });

            $('form').on('submit', function (e) {
                var userName = $('#name').val();
                $.ajax({
                    url: '<%= request.getContextPath() %>/signUp',
                    method: 'GET',
                    data: { checkName: userName },
                    async: false, // 동기적으로 처리하여 폼 제출 전에 검사
                    success: function (response) {
                        var data = JSON.parse(response);
                        if (data.isDuplicate) {
                            alert('이 사용자 이름은 이미 존재하거나 "익명"일 수 없습니다.');
                            e.preventDefault();
                        }
                    }
                });
            });
        });
    </script>
</head>
<body>
    <%@ include file="nav.jsp" %>
    <form method="post" action="/signUp" class="signUpForm">
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
            <input type="submit" value="회원가입">
        </p>
        <div class="loginLink">
            <p>계정이 있으신가요? <a href="/mvc">로그인</a></p>
        </div>
    </form>
</body>
</html>
