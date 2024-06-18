<%@ page import="java.util.*" %>
<html>
<head>
    <title>nav</title>
    <style>
        body {
            background: url(../img/background3.jpg);
            background-size: 100% auto;
            background-attachment: fixed;
        }
        ::-webkit-scrollbar {
            display: none;
        }
        header {
            font-family: 'Pretendard';
            font-style: normal;
            font-weight: 700;
            font-size: 4vw;
            line-height: 76px;
            letter-spacing: 0.4em;
            margin-top: 50px;
            margin-bottom: 50px;
            margin-left: auto;
            margin-right: auto;
            text-align: center;
        }
        header a {
            text-decoration: none;
            color: black;
        }
        .menu {
            text-align: center;
            border-top: 3px solid rgb(0, 0, 0);
            border-bottom: 3px solid rgb(0, 0, 0);
            padding: 10px 0;
            display: flex;
            justify-content: space-around;
            margin-left: 3%;
            margin-right: 3%;
        }
        .menu a {
            font-family: 'Pretendard';
            font-style: normal;
            font-weight: 600;
            font-size: 1.75vw;
            line-height: 29px;
            letter-spacing: 0.1em;
            text-decoration: none;
            color: black;
            display: inline;
        }
    </style>
</head>
<body>
    <header><a href="/">KNITTING LIFE</a></header>
    <div class="menu">
        <a href="/writePost">WRITE POST</a>
        <a href="/board">POSTS</a>
        <a href="/users">USERS</a>
        <a href="/followBoard">FOLLOWING POSTS</a>
        <%
            String userName = (String) session.getAttribute("userName");
            if (userName != null) {
        %>
            <a href="/mvc_out">LOG OUT</a>
        <%
            } else {
        %>
            <a href="/mvc">LOG IN</a>
        <%
            }
        %>
    </div>
</body>
</html>
