<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: sangz
  Date: 4/1/2016
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<s:iterator value="#session.comments" var="comments" status="status">
    <div class="singleComment">
        <div class="comentRelate">
            <img class="commentUserPhoto" src="./img/userphoto/head.jpg" />
            <div class="commentRelateUC">
                <div class="commentUserName">
                    ${comments[4]}
                </div>
                <div class="commentUserContent">
                    <s:property value="#comments[2]"></s:property>
                </div>
            </div>
            <div class="commentTimePri" >
                <div class="commentTime">
                        ${comments[3]}
                </div>
                <div class="getCount" id="comment${comments[1]}">

                    <s:if test="#session.currentUser != null">
                        <s:if test="#comments[6] == 1">
                            <img class="commentzan" title="yizan" src="img/iconfont-zanzan2.png" />
                        </s:if>
                        <s:else>
                            <img class="commentzan" title="zan" src="img/iconfont-zanzan1.png" />
                        </s:else>
                    </s:if>
                    <s:else>
                        <img class="pleaseLogin" src="img/iconfont-zanzan1.png" />
                    </s:else>
                    ${comments[5]}
                </div>

            </div>
        </div>
    </div>
</s:iterator>


<%--底部的分页组--%>
<s:if test="#session.totalCommentPage > 1">
    <nav style="clear: both;margin-left: 30px;margin-top: 30px;float:left;">
        <ul class="pagination">

            <%-- 首页按钮 --%>
            <li> <a href="#showComments" aria-label="Previous"> <span aria-hidden="true">&laquo;</span> </a>   </li>

            <%--当前的页面小于3或者总页面小于7的时候--%>
            <s:if test="#session.currentCommentPage <= 3 || #session.totalCommentPage < 7 ">
                <s:iterator value="{1,2,3,4,5,6,7}" status="status" >
                    <s:if test="#status.index < #session.totalCommentPage">
                        <s:if test="#status.index+1 == #session.currentCommentPage">
                            <li class="active"><a href="#showComments"><s:property /></a></li>
                        </s:if>
                        <s:else>
                            <li class="getComments"><a href="#showComments"><s:property /> </a></li>
                        </s:else>
                    </s:if>
                </s:iterator>
            </s:if>

                <%--当总页面数量大于7，当前页面大于4且小于总页面数减3 的时候--%>
            <s:elseif test="#session.currentCommentPage <= #session.totalCommentPage - 3">
                <s:iterator value="{#session.currentCommentPage-3,#session.currentCommentPage-2,#session.currentCommentPage-1,#session.currentCommentPage,#session.currentCommentPage+1,#session.currentCommentPage+2,#session.currentCommentPage+3}" var="pages">
                    <s:if test="#pages == #session.currentCommentPage">
                        <li class="active"><a href="#showComments"><s:property /> </a></li>
                    </s:if>
                    <s:else>
                        <li class="getComments"><a href="#showComments"><s:property /> </a></li>
                    </s:else>
                </s:iterator>
            </s:elseif>

                <%--当当前页面在后三页的时候--%>
            <s:elseif test="#session.currentCommentPage <= #session.totalCommentPage">
                <s:iterator value="{#session.totalCommentPage-6,#session.totalCommentPage-5,#session.totalCommentPage-4,#session.totalCommentPage-3,#session.totalCommentPage-2,#session.totalCommentPage-1,#session.totalCommentPage}" var="pages">
                    <s:if test="#pages == #session.currentCommentPage">
                        <li class="active"><a href="#showComments"><s:property /> </a></li>
                    </s:if>
                    <s:else>
                        <li class="getComments"><a href="#showComments"><s:property /> </a></li>
                    </s:else>
                </s:iterator>
            </s:elseif>

            <li> <a href="#showComments" aria-label="Next"> <span aria-hidden="true">&raquo;</span> </a> </li>
        </ul>
    </nav>
</s:if>

<script src="js/comments.js"></script>

