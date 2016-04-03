<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: sangzhenya
  Date: 2016/3/29
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>关于</title>
    <link rel="stylesheet" href="../web/style/about.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
<!-- 网站LOGO和主要入口 -->

<s:include value="../header.jsp" />
<s:include value="navbar.jsp" />

<!-- 导航栏 -->

<div class="mainContent">
    <div class="mainPage">
        ${session.media.conent}
    </div>
</div>

</body>
</html>

