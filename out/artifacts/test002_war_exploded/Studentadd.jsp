<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: sangz
  Date: 3/22/2016
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
User Success!
from valuestack: <s:property value="t"/><br/>
from actioncontext: <s:property value="#parameters.t"/>
<s:debug></s:debug>
<br />
</body>
</html>
