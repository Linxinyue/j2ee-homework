<%--
  Created by IntelliJ IDEA.
  User: sangzhenya
  Date: 2016/3/30
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <base href=" <%=basePath%>">
    <title>登录</title>
    <link rel="stylesheet" href="./style/admin.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
<div class="bakcgroudPic">
    <img class="backgroundPic" src="./img/background.jpg" />
</div>
<div>
    <div class="adminLogin">
            <div class="inputUserName">
                <div class="adminPassPicDiv">
                    <img class="adminPassPic" src="./img/admin/iconfont-yonghu.png"  />
                </div>
                <input class="adminNameInput" id="username" type="text" name="admin" onblur="checkName()" />
            </div>
            <div class="inputUserName">
                <div class="adminPassPicDiv">
                    <img class="adminPassPic" src="./img/admin/iconfont-mima.png"  />
                </div>
                <input class="adminNameInput" id="password" type="text" name="pass"  />
            </div>
            <div class="inputUserName">
                <div class="adminPassPicDiv">
                    <img class="adminPassPic" src="./img/admin/iconfont-mima.png"  />
                </div>
                <input class="adminNameInput" id="password1" type="text" name="pass" onkeydown="submitNow(event)" />
            </div>
            <div class="adminSubmit" >注册</div>
            <%--<input class="adminSubmit" type="submit" value="注 册" />--%>
    </div>
</div>
<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="js/register.js"></script>
</body>
</html>

