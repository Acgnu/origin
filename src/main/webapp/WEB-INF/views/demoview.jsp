<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${creator}</title>
    <style type = "text/css">
        img{-webkit-animation: rotate 15s infinite linear;width:300px;height:300px;}
        div{width:300px; height:300px; margin: 15% auto;border-radius:50%;box-shadow:1px 2px 15px 1px gray; position:relative;}
        @-webkit-keyframes rotate { from {-webkit-transform: rotate(0deg) } to {-webkit-transform: rotate(360deg) } }
        @-webkit-keyframes fades {from {opacity:0;} to {opacity:1;}}
    </style>
    <script type = "text/javascript">
        function pause(){document.getElementById("IMG").style.animationPlayState = "paused";}
        function docontinue(){ document.getElementById("IMG").style.animationPlayState = "running";}
    </script>
</head>
<body>
    <div>
        <img id="IMG" onmouseover="pause()" onmouseout="docontinue()"
             src="https://i.loli.net/2019/08/31/7OhSvApiasWlxn6.png" style="border-radius:50%;"/>
        <span></span>
    </div>
    <audio src="https://raw.githubusercontent.com/Acgnu/Aserv/master/xtt_xmt.mp3" autoplay loop></audio>
</body>
</html>
