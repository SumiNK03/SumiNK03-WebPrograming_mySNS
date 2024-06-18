<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 목록</title>
    <style>

        /* 사용자 목록 컨테이너 스타일 */
        .usersContainer {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 400px;
            margin: 50px auto;
            text-align: center;
        }

        .usersContainer h1 {
            margin-bottom: 20px;
        }

        .userItem {
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 4px;
        }

        .userItem p {
            margin: 0;
        }

        .follow {
            background-color: lightgray;
            border-radius: 4px;
            transition: background-color 0.3s ease;
            text-decoration:none;
            padding: 10px;
            color: black;
            display: block;
            font-weight: bold;
            margin-top: 10px;
        }
        .follow:hover {
            background-color: gray;
            color: white;
        }
    </style>
</head>
<body>
    <%@ include file="nav.jsp"%>
    <div class="usersContainer">
        <h1>사용자 목록</h1>
        <div id="userListContainer" class="userList">
            <script>
                var users = ${users};
                var userListContainer = document.querySelector(".userList");

                users.forEach(function(user) {
                    var userItem = document.createElement("div");
                    userItem.classList.add("userItem");

                    var p = document.createElement("p");
                    p.textContent = user;

                    var a = document.createElement("a");
                    a.href = "/follow?followeeName=" + user;
                    a.textContent = "팔로우 / 언팔로우";
                    a.className = "follow";

                    userItem.appendChild(p);
                    userItem.appendChild(a);
                    userListContainer.appendChild(userItem);
                });
            </script>
        </div>
        <a href="/" class="follow">메인으로</a>
    </div>
</body>
</html>
