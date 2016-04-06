<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: sangzhenya
  Date: 2016/3/30
  Time: 12:46
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <base href=" <%=basePath%>">
    <meta charset="utf-8">
    <title>${session.myArticle.title}</title>
    <link rel="stylesheet" href="./style/top.css">
    <link rel="stylesheet" href="./style/page.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
<!-- 网站LOGO和主要入口 -->
<s:include value="header.jsp" />

<div class="pagePage">
    <div class="pageMainContent">
        <div class="title">
            <h1>${session.myArticle.title}</h1>
        </div>

        <div class="formTime">
            <div class="comeFrom">
                ${session.myArticle.author}
            </div>
            <div class="comeTime">
                ${session.myArticle.pub_date}
            </div>
        </div>

        <div class="pageContent">
            ${session.myArticle.content}
        </div>

        <div class="pageTags">
            <div class="tagTip">相关标签：</div>
            <div class="pageTag">文本编辑器</div>
            <div class="pageTag">编程语言</div>
            <div class="pageTag">科技</div>
            <div class="pageTag">Vim</div>
        </div>
        <div class="pageTools">
            <div class="pageColloect">
                <img class="colloectIcon" src="./img/shoucang.png" />
                <img class="colloectIcon" src="./img/fenxiang.png" />
            </div>
        </div>
        <div class="pageClass">
            <div class="classImgDiv">
                <img class="classImg" src="./img/userphoto/head.jpg" />
            </div>
            <div class="classDesc">
                <div class="classTitle">
                    <h3><s:property value="#session.myClassification.classification_name" /></h3>
                </div>
                <div class="classContent">
                    <h3><s:property value="#session.myClassification.classification_desc" /></h3>
                </div>
            </div>

            <s:if test="#session.currentUser != null">
                <s:if test="#session.isSubClass == 'true' ">
                    <div class="subscribe" id="<s:property value="#session.myClassification.classification_id" />">已订阅</div>
                </s:if>
                <s:else>
                    <div class="subscribe" id="<s:property value="#session.myClassification.classification_id" />">+ 订阅</div>
                </s:else>
            </s:if>
            <s:else>
                <div class="pleaselogin" id="<s:property value="#session.myClassification.classification_id" />">+ 订阅</div>
            </s:else>


        </div>

        <div class="pageComments">
            <div class="comentTip">
                评论
            </div>
            <div class="pageComentNumber">
                <s:property value="#session.commentCount"/>条评论
            </div>
            <div class="userPhotoDiv">
                <img class="userPhoto" src="./img/userphoto/head.jpg" />
            </div>
            <div class="commentsTextarea">
                <textarea class="commentsText" oninput="haveText()"></textarea>
                <div class="textareaTools">
                    分享到：
                    <img class="commentsShare" src="./img/qzone.png"  />
                    <img class="commentsShare" src="./img/weibo.png"  />
                    <s:if test="#session.currentUser != null">
                        <div class="sendComment">发表</div>
                    </s:if>
                    <s:else>
                        <div class="sendCommentLogin">发表</div>
                    </s:else>
                    <div class="commentTip"></div>

                </div>
            </div>
            <div id="showComments">
                <s:include value="comments.jsp"/>
            </div>
        </div>

    </div>
</div>
<script src="js/page.js" ></script>
</body>
</html>

