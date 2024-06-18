<%@ page contentType="text/html; charset=utf-8"%>
<head>
    <title>Knitting Life</title>
    <style>
        body {
    background: url(../img/background3.jpg);
    background-size: 100% auto;
    background-repeat: no-repeat;
    background-attachment: fixed;
}
::-webkit-scrollbar {
    display: none;
}
header {
    font-family: 'Pretendard';
    font-style: normal;
    font-weight: 700;
    font-size: 64px;
    line-height: 76px;
    letter-spacing: 0.4em;
    margin-top: 50px;
    margin-bottom: 50px;
    margin-left: auto;
    margin-right: auto;
    text-align: center;
}

header a{
    text-decoration: none;
    color: black;
}


.postRe {
    overflow: scroll;
    margin-top: 20px;
    margin-bottom: 20px;
    margin-left: 5%;
    margin-right: 5%;
    overflow: auto;
    white-space: nowrap;
}

.postRe::-webkit-scrollbar {
    height: 10px;  /* 스크롤바의 너비 */
}

.postRe::-webkit-scrollbar-thumb {
    width: 30%; /* 스크롤바의 길이 */
    background: #a5a5a5; /* 스크롤바의 색상 */
    
    border-radius: 10px;
}

.postRe::-webkit-scrollbar-track {
    display: none;  /*스크롤바 뒷 배경 색상*/
}
.postRe::-webkit-scrollbar-button {
    display: none;  /*스크롤바 뒷 배경 색상*/
}


h1{
    margin-top: 20px;
    margin-bottom: 20px;
    margin-left: 5%;
    margin-right: 5%;
    font-family: 'Pretendard';
    font-style:normal;
    font-weight: 600;
    font-size: 24px;
    line-height: 29px;
    letter-spacing: 0.1em;
    text-decoration: none;
    color: black;
    display: inline-block;
}

.mpost{
    width: 300px;
    height: 300px;
    display: inline-block;
    background: #FFFFFF;
    mix-blend-mode: normal;
    box-shadow: inset -4px -4px 4px rgba(0, 0, 0, 0.25), inset 4px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 10px;
    margin-right: 20px;
    position: relative;
}


.mpost {
    background-size: cover;
}

.postImg {
    width: 100%;
    height: auto;
    object-fit: cover; /* 이미지가 부모 크기에 맞춰 잘림 */
    border-radius: 10px;
}


.pro {
    margin-top: 20px;
    margin-bottom: 20px;
    margin-left: 5%;
    margin-right: 5%;
    display: flex;
    justify-content: space-around;
    align-items: center;
}

.pro img {
    background-color: #FFFFFF;
    width: 300px;
    height: 300px;
    border-radius: 50%;
    box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
    display: inline-block;
}

.info {
    justify-content: center;
    align-items: center;
    text-align: center;
    display: inline-block;
    font-family: 'Pretendard';
    font-style: normal;
    font-weight: 700;
    font-size: 20px;
    line-height: 76px;
    width: 20%;
    height: 300px;
    background-color: #FFFFFF;
    box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
    border-radius: 10px;
    padding-left: 10px;
    padding-right: 10px;
    padding-top: auto;
}
    </style>
</head>
<body>
<%@ include file="nav.jsp"%>
    <h1>MY WORKS</h1>
    <div class="postRe">
        <div class="mpost">
            <img class="postImg" src="./img/post1.jpg">
        </div>
        <div class="mpost">
            <img class="postImg" src="./img/post2.jpg">
        </div>
        <div class="mpost">
            <img class="postImg" src="./img/post3.jpg">
        </div>
        <div class="mpost">
            <img class="postImg" src="./img/post4.jpg">
        </div>
        <div class="mpost">
            <img class="postImg" src="./img/post5.jpg">
        </div>
        <div class="mpost">
            <img class="postImg" src="./img/post6.jpg">
        </div>
    </div>
</body>
</html>