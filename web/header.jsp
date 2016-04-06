<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="./style/top.css">
<div class="pageletNav">
    <div class="navLogo">
        <a href="index.html"><img src="./img/logo1.png" class="logo"></a>
    </div>
    <div class="navTitle">
        <div class="homedynamic">
            <h3>动态</h3></div>
        <div class="homedynamic"><h3>首页</h3></div>
    </div>
    <div class="navSubtitle">
        <div class="loginmore" >
            <h4 id="loginMore">更多</h4>
            <div class="moreOptions">
                <ul>
                    <li class="moreOption">关于我们</li>
                    <li class="moreOption">联系我们</li>
                    <li class="moreOption">用户协议</li>
                    <li class="moreOption">投诉指引</li>
                    <li class="moreOption">合作说明</li>
                </ul>
            </div>
        </div>

        <s:if test="#session.currentUser != null">
            <div class="loginmore">
                <img class="headerUserPhoto" src="img/userphoto/head.jpg"/>
                <div class="userName"><s:property value="#session.currentUser.name"/></div>
                <div class="userOptions">
                    <ul>
                        <li class="userOption">我的收藏</li>
                        <li class="userOption">我的订阅</li>
                        <li class="userOption">我的粉丝</li>
                        <li class="userOption">我的关注</li>
                        <li class="userOption">退出</li>
                    </ul>
                </div>
            </div>
        </s:if>
        <s:else>
            <div class="loginmore" id="loginEntry">
                <h4>登录</h4>
            </div>
        </s:else>

    </div>
</div>

<!-- 登录界面 -->
<div class="shadow"></div>
<div class="login">
    <div class="loginTitle">登录</div>
    <form  action="user" method="post" id="loginNow">
        <div class="loginContain" id="loginUser">
            <div class="loginIcons">
                <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
            </div>
            <input class="loginInput" placeholder="请输入注册邮箱" type="text" name="name" id="username" value="xinyue1"/>
        </div>
        <div class="loginContain">
            <div class="loginIcons">
                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
            </div>
            <input class="loginInput" placeholder="请输入密码" id="password" type="password" name="password" onkeydown="submitNow(event)" value="xinyue"/>
            <div class="openEye">
                <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
            </div>
        </div>
        <div class="remeberme" style="display: none;">
            <input name="remeberme" class="checkremeberme" type="checkbox" />
            <div class="checkremeberme">
                记住密码
            </div>
        </div>
        <div class="loginOptions" id="registerOption">
            注册
        </div>
        <div class="loginOptions" id="loginOption">
            登录
        </div>
    </form>
</div>
<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="js/top.js" ></script>
