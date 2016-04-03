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
<ol>
    <li>访问值栈中的action的普通属性: username = <s:property value="username"/> </li>
    <li>访问值栈中对象的普通属性(get set方法)：<s:property value="user.age"/> | <s:property value="user['age']"/> | <s:property value="user[\"age\"]"/> | wrong: <%--<s:property value="user[age]"/>--%></li>
    <li>访问值栈中对象的普通属性(get set方法): <s:property value="cat.friend.name"/></li>
    <li>访问值栈中对象的普通方法：<s:property value="password.length()"/></li>
    <li>访问值栈中对象的普通方法：<s:property value="cat.miaomiao()" /></li>
    <li>访问值栈中action的普通方法：<s:property value="m()" /></li>
    <hr />
    <li>访问静态方法：<s:property value="@action.S@s()"/></li>
    <li>访问静态属性：<s:property value="@action.S@STR"/></li>
    <li>访问Math类的静态方法：<s:property value="@@max(2,3)" /></li>
    <hr />
    <li>访问普通类的构造方法：<s:property value="new action.User(8)"/></li>
    <hr />
    <li>访问List:<s:property value="users"/></li>
    <li>访问List中某个元素:<s:property value="users[1]"/></li>
    <li>访问List中元素某个属性的集合:<s:property value="users.{age}"/></li>
    <li>访问List中元素某个属性的集合中的特定值:<s:property value="users.{age}[0]"/> | <s:property value="users[0].age"/></li>
    <li>访问Set:<s:property value="dogs"/></li>
    <li>访问Set中某个元素:<s:property value="dogs[1]"/></li>
    <li>访问Map:<s:property value="dogMap"/></li>
    <li>访问Map中某个元素:<s:property value="dogMap.dog101"/> | <s:property value="dogMap['dog101']"/> | <s:property value="dogMap[\"dog101\"]"/></li>
    <li>访问Map中所有的key:<s:property value="dogMap.keys"/></li>
    <li>访问Map中所有的value:<s:property value="dogMap.values"/></li>
    <li>访问容器的大小：<s:property value="dogMap.size()"/> | <s:property value="users.size"/> </li>
    <hr />
    <li>投影(过滤)：<s:property value="users.{?#this.age==1}[0]"/></li>
    <li>投影：<s:property value="users.{^#this.age>1}.{age}"/></li>
    <li>投影：<s:property value="users.{$#this.age>1}.{age}"/></li>
    <li>投影：<s:property value="users.{$#this.age>1}.{age} == null"/></li>
    <hr />
    <li>[]:<s:property value="[0].username"/></li>

</ol>

<s:debug></s:debug>
<br />
</body>
</html>
