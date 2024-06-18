<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.test.hello.model.Post" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>게시판</title>
    <style>
        .boardCover {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-right: 4%;
            margin-left: 4%;
            border-bottom: solid 1px black;
        }
        .p {
            width: 90%;
            height: 1150px;
            background: white;
            border-radius: 10px;
            margin-right: auto;
            margin-left: auto;
            padding: 20px;
            padding-top: 0;
            margin-top: 10px;
            margin-bottom: 10px;
        }
       .authorCover {
    display: flex;
    align-items: center; /* 자식 요소들을 수직 가운데 정렬 */
}

.profile {
    width: 50px;
    height: 50px;
    margin-right: 10px;
}

.author {
    font-size: 30px;
    font-weight: bold;
}
        
        .titleCover {
            width: 100%;
            height: 60px;
            background: whitesmoke;
            border: solid lightgray 3px;
            font-size: 15px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding-left: 10px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-sizing: border-box;
        }
        .titleCover h1 {
            width: 100%;
            height: 40px;
            overflow: hidden;
            font-size: 20px;
        }
        .contentCover {
            width: 100%;
            height: 400px;
            background: whitesmoke;
            border: solid lightgray 3px;
            padding: 10px;
            border-radius: 10px;
            box-sizing: border-box;
        }
        .editCover {
            margin-top: 10px;
        }
        .likeCover {
            margin-top: 10px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            padding-left: 10px;
            padding-right: 10px;
            box-sizing: border-box;
        }
        .likeCover button {
            width:40px;
            height:40px;
            border: none;
            background-color: white;
            margin-right: 10px;
            margin-left:10px;
        }
        .likeCover .like {
            background-image: url(../img/like.png);
            background-size: cover;
        }
        .likeCover .like:hover {
            background-image: url(../img/like_hover.png);
        }
        .likeCover .dislike {
            background-image: url(../img/dislike.png);
            background-size: cover;
        }
        .likeCover .dislike:hover {
            background-image: url(../img/dislike_hover.png);
        }
        .comments {
            width:100%;
            height:300px;
            overflow:scroll;
            padding: 10px;
            box-sizing: border-box;
            background: whitesmoke;
            border: solid lightgray 3px;
            border-radius: 10px;
            margin-bottom:10px;
        }
        .commentActions button {
            width:20px;
            height:20px;
            border: none;
            background-color: white;
            margin-right:2px;
            margin-left:2px;
        }
        .commentActions .clike {
            background-image: url(../img/like.png);
            background-size: cover;
        }
        .commentActions .clike:hover {
            background-image: url(../img/like_hover.png);
        }
        .commentActions .cdislike {
            background-image: url(../img/dislike.png);
            background-size: cover;
        }
        .commentActions .cdislike:hover {
            background-image: url(../img/dislike_hover.png);
        }
        .comments .comment {
            width: 100%;
            background-color: white;
            padding-left:10px;
            padding-right: 10px;
            padding-bottom: 5px;
            padding-top:1px;
            margin-bottom: 10px;
            border-radius: 10px;
            box-sizing: border-box;
            border: solid lightgray 1px;
        }
        .commentForm {
            width: 100%;
            background: whitesmoke;
            border: solid lightgray 3px;
            height: 50px;
            border-radius: 10px;
            align-content: center;
            padding-left: 10px;
            padding-right: 10px;
            box-sizing: border-box;
            display: flex; /* 추가: flex로 자식들을 배치 */
            align-items: center; /* 추가: 수직 중앙 정렬 */
            justify-content: space-between; /* 추가: 자식 요소 사이에 공간 분배 */
        }

        .commentForm .cc {
            width: calc(100% - 40px); /* 버튼 너비를 제외한 나머지 공간을 채움 */
            height: 30px;
            border-radius: 10px;
            border: solid lightgray 1px;
            vertical-align: top;
            outline: none;
            padding-left: 10px;
            overflow: scroll;
            flex-shrink: 1; /* 추가: 축소 가능 */
        }

        .commentForm .ccb {
            width: 30px;
            aspect-ratio: 1; /* 정사각형 비율 유지 */
            vertical-align: top;
            background-color: whitesmoke;
            border: none;
            background-image: url(../img/send.png);
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
            outline: none;
            cursor: pointer; /* 추가: 클릭할 수 있는 느낌 */
            flex-shrink: 0; /* 추가: 축소 불가 */
        }
        .commentForm .ccb:hover {
            background-image: url(../img/send_hover.png);
        }
        .editCover{
            text-align: right;
        }
        .editCover a img {
            width: 30px;
            height: 30px;
            margin-top: 5px;
            margin-left: 5px;
            margin-right: 5px;
        }
        .editCover .edit img {
            margin-top: 15px;
        }
        .editCover .delete img {
            margin-top: 15px;
        }
        .editCover .edit img:hover {
            content: url(./img/edit_hover.png);
        }
        .editCover .delete img:hover {
            content: url(./img/delete_hover.png);
        }
        .editCover .file img:hover {
            content: url(./img/file_hover.png);
        }

        .follow {
            background-color: lightgray;
            border-radius: 4px;
            margin-bottom: 20px;
            transition: background-color 0.3s ease;
            text-decoration:none;
            padding: 10px;
            color: black;
            display: inline-block;
            font-weight: bold;
        }
        .follow:hover {
            background-color: gray;
            color: white;
        }
    </style>
</head>
<body>
<%@ include file="nav.jsp"%>
<div class="boardCover">
    <h2>게시물 목록</h2>
    <a href="writePost">글쓰기</a>
</div>
<c:forEach var="post" items="${posts}">
    <div class="p">
        <div class="authorCover">
        <img src="./img/profile.png" class="profile"/>
        <p class="author">${post.authorName}</p>
        </div>
        <c:if test="${post.authorName ne '익명'}">
        <a href="/follow?followeeName=${post.authorName}" class="follow"> 팔로우 / 언팔로우</a>
        </c:if>
        <div class="titleCover"><h1>${post.title}</h1></div>
        <div class="contentCover"><p>${post.content}</p></div>
        <div class="likeCover">
            <span id="likeCount-${post.id}">${post.likes}</span>
            <button class="like" onclick="toggleLike(${post.id})"></button>
            <button class="dislike" onclick="toggleDislike(${post.id})"></button>
        </div>

        <!-- 댓글 목록 -->
        <div class="comments">
            <c:if test="${empty post.comments}">
                <p>No Comments :( </p>
            </c:if>
            <c:forEach var="comment" items="${post.comments}">
                <div class="comment">
                <p>${comment.authorName} : ${comment.content}</p>
                <div class="commentActions">
                    <span id="commentLikeCount-${comment.id}">${comment.likes}</span>
                    <button class="clike" onclick="toggleCommentLike(${comment.id})"></button>
                    <button class="cdislike" onclick="toggleCommentDislike(${comment.id})"></button>
                </div>
                </div>
            </c:forEach>
        </div>

        <!-- 댓글 입력 폼 -->
        <form action="/comments/create" method="post" class="commentForm">
            <input type="hidden" name="postId" value="${post.id}">
            <input class="cc" type="text" name="content" placeholder="댓글을 입력하세요">
            <input type="hidden" name="authorName" value="<%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "익명" %>">
            <input type="hidden" name="pageType" value="board">
            <button class="ccb" type="submit"></button>
        </form>

        <div class="editCover">
            <c:if test="${not empty post.fileName}">
            <a class="file" href="downloadFile?id=${post.id}">${post.fileName}<img src="./img/file.png"/></a>
            </c:if>
            <br>
            <a class="edit" href="editPost?id=${post.id}"><img src="./img/edit.png"/></a>
            <a class="delete" href="deletePost?id=${post.id}"><img src="./img/delete.png"></a>
        </div>
    </div>
</c:forEach>
<script>
    function toggleLike(postId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/toggleLike", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    document.getElementById("likeCount-" + postId).innerText = response.likes;
                } else {
                    alert("좋아요 처리 중 오류가 발생했습니다.");
                }
            }
        };
        xhr.send("id=" + postId);
    }

    function toggleDislike(postId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/toggleDislike", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    document.getElementById("likeCount-" + postId).innerText = response.likes;
                } else {
                    alert("싫어요 처리 중 오류가 발생했습니다.");
                }
            }
        };
        xhr.send("id=" + postId);
    }

    function toggleCommentLike(commentId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/comments/toggleLike", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    document.getElementById("commentLikeCount-" + commentId).innerText = response.likes;
                } else {
                    alert("댓글 좋아요 처리 중 오류가 발생했습니다.");
                }
            }
        };
        xhr.send("id=" + commentId);
    }

    function toggleCommentDislike(commentId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/comments/toggleDislike", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    document.getElementById("commentLikeCount-" + commentId).innerText = response.likes;
                } else {
                    alert("댓글 싫어요 처리 중 오류가 발생했습니다.");
                }
            }
        };
        xhr.send("id=" + commentId);
    }
</script>
</body>
