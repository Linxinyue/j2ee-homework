<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: sangzhenya
  Date: 2016/4/5
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="adminJS/style/deleteContent.css">
<div class="col-xs-12">
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">Hover Data Table</h3>
        </div><!-- /.box-header -->
        <div class="box-body">
            <table id="example2" class="table table-bordered table-hover">
                <s:if test="#session.adminArticle != null">
                <thead>
                <tr>
                    <th width="5%">ID</th>
                    <th width="15%">标题</th>
                    <th width="10%">作者</th>
                    <th>摘要</th>
                    <th>日期</th>
                    <th width="5%" style="text-align: center">修改</th>
                    <th width="5%" style="text-align: center">删除</th>
                </tr>
                </thead>
                <tbody>
                    <s:iterator value="#session.adminArticle" var="articles">
                        <tr id="article${articles.article_id}">
                            <td>${articles.article_id}</td>
                            <td><a href="article/${articles.article_id}.html" target="_blank">${articles.title}</a> </td>
                            <td>${articles.author}</td>
                            <td>${articles.abstracts}</td>
                            <td>${articles.pub_date}</td>
                            <td style="text-align: center" ><img class="modifi" src="img/admin/iconfont-xiugai.png" /></td>
                            <td style="text-align: center"><img class="delete" src="img/admin/iconfont-shanchu.png" /></td>
                        </tr>
                    </s:iterator>
                </s:if>


                <s:if test="#session.myComments != null">
                    <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th width="50%">内容</th>
                        <th width="10%">作者</th>
                        <th>标题</th>
                        <th>日期</th>
                        <th width="5%" style="text-align: center">删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="#session.myComments" var="myComments">
                        <tr id="comment${myComments[0]}">
                            <td>${myComments[0]}</td>
                            <td>${myComments[1]}</td>
                            <td>${myComments[2]}</td>
                            <td><a href="article/${myComments[5]}.html" target="_blank">${myComments[3]}</a> </td>
                            <td>${myComments[4]}</td>
                            <td style="text-align: center"><img class="commentDelete" src="img/admin/iconfont-shanchu.png" /></td>
                        </tr>
                    </s:iterator>
                </s:if>

                    <s:if test="#session.myUsers != null">
                    <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th width="15%">用户名</th>
                        <th width="5%" style="text-align: center">删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="#session.myUsers" var="myUsers">
                        <tr id="comment${myUsers.user_id}">
                            <td>${myUsers.user_id}</td>
                            <td>${myUsers.name}</td>
                            <td style="text-align: center"><img class="commentDelete" src="img/admin/iconfont-shanchu.png" /></td>
                        </tr>
                    </s:iterator>
                    </s:if>

                </tbody>
            </table>
        </div><!-- /.box-body -->
    </div><!-- /.box -->
</div><!-- /.col -->
<div class="col-md-4">
    <nav>
        <ul class="pagination">

            <s:if test="#session.adminArticle != null">
                <li class="first">
                    <a href="admin/admin_articleDel#contents" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <s:if test="#session.indexPage <= 3 || #session.pages <= 7">
                    <s:iterator value="{1,2,3,4,5,6,7}" status="status">
                        <s:if test="#status.index < #session.pages">
                            <s:if test="#status.index+1 == #session.indexPage">
                                <li class="active"><a href="admin/admin_articleDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                            </s:if>
                            <s:else>
                                <li class="getArticles"><a href="admin/admin_articleDel#contents"><s:property /> </a></li>
                            </s:else>
                        </s:if>
                    </s:iterator>
                </s:if>
                <s:elseif test="#session.indexPage <= #session.pages - 3">
                    <s:iterator value="{#session.indexPage-3,#session.indexPage-2,#session.indexPage-1,#session.indexPage,#session.indexPage+1,#session.indexPage+2,#session.indexPage+3}" var="pages">
                        <s:if test="#pages == #session.indexPage">
                            <li class="active"><a href="admin/admin_articleDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                        </s:if>
                        <s:else>
                            <li class="getArticles"><a href="admin/admin_articleDel#contents"><s:property /> </a></li>
                        </s:else>
                    </s:iterator>
                </s:elseif>
                <s:elseif test="#session.indexPage <= #session.pages">
                    <s:iterator value="{#session.pages-6,#session.pages-5,#session.pages-4,#session.pages-3,#session.pages-2,#session.pages-1,#session.pages}" var="pages">
                        <s:if test="#pages == #session.indexPage">
                            <li class="active"><a href="admin/admin_articleDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                        </s:if>
                        <s:else>
                            <li class="getArticles"><a href="admin/admin_articleDel#contents"><s:property /> </a></li>
                        </s:else>
                    </s:iterator>
                </s:elseif>
                <li class="last" id="last${session.pages}">
                    <a href="admin/admin_articleDel#contents" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </s:if>



            <s:if test="#session.myComments != null">
                <li class="commentfirst">
                    <a href="admin/admin_commentDel#contents" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <s:if test="#session.indexPage <= 3 || #session.pages <= 7">
                    <s:iterator value="{1,2,3,4,5,6,7}" status="status">
                        <s:if test="#status.index < #session.pages">
                            <s:if test="#status.index+1 == #session.indexPage">
                                <li class="active"><a href="admin/admin_commentDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                            </s:if>
                            <s:else>
                                <li class="getComments"><a href="admin/admin_commentDel#contents"><s:property /> </a></li>
                            </s:else>
                        </s:if>
                    </s:iterator>
                </s:if>
                <s:elseif test="#session.indexPage <= #session.pages - 3">
                    <s:iterator value="{#session.indexPage-3,#session.indexPage-2,#session.indexPage-1,#session.indexPage,#session.indexPage+1,#session.indexPage+2,#session.indexPage+3}" var="pages">
                        <s:if test="#pages == #session.indexPage">
                            <li class="active"><a href="admin/admin_commentDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                        </s:if>
                        <s:else>
                            <li class="getComments"><a href="admin/admin_commentDel#contents"><s:property /> </a></li>
                        </s:else>
                    </s:iterator>
                </s:elseif>
                <s:elseif test="#session.indexPage <= #session.pages">
                    <s:iterator value="{#session.pages-6,#session.pages-5,#session.pages-4,#session.pages-3,#session.pages-2,#session.pages-1,#session.pages}" var="pages">
                        <s:if test="#pages == #session.indexPage">
                            <li class="active"><a href="admin/admin_commentDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                        </s:if>
                        <s:else>
                            <li class="getComments"><a href="admin/admin_commentDel#contents"><s:property /> </a></li>
                        </s:else>
                    </s:iterator>
                </s:elseif>
                <li class="commentLast" id="last${session.pages}">
                    <a href="admin/admin_commentDel#contents" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </s:if>

            <s:if test="#session.myUsers != null">
                <li class="userFirst">
                    <a href="admin/admin_userDel#contents" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <s:if test="#session.indexPage <= 3 || #session.pages <= 7">
                    <s:iterator value="{1,2,3,4,5,6,7}" status="status">
                        <s:if test="#status.index < #session.pages">
                            <s:if test="#status.index+1 == #session.indexPage">
                                <li class="active"><a href="admin/admin_userDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                            </s:if>
                            <s:else>
                                <li class="getUsers"><a href="admin/admin_userDel#contents"><s:property /> </a></li>
                            </s:else>
                        </s:if>
                    </s:iterator>
                </s:if>
                <s:elseif test="#session.indexPage <= #session.pages - 3">
                    <s:iterator value="{#session.indexPage-3,#session.indexPage-2,#session.indexPage-1,#session.indexPage,#session.indexPage+1,#session.indexPage+2,#session.indexPage+3}" var="pages">
                        <s:if test="#pages == #session.indexPage">
                            <li class="active"><a href="admin/admin_userDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                        </s:if>
                        <s:else>
                            <li class="getUsers"><a href="admin/admin_userDel#contents"><s:property /> </a></li>
                        </s:else>
                    </s:iterator>
                </s:elseif>
                <s:elseif test="#session.indexPage <= #session.pages">
                    <s:iterator value="{#session.pages-6,#session.pages-5,#session.pages-4,#session.pages-3,#session.pages-2,#session.pages-1,#session.pages}" var="pages">
                        <s:if test="#pages == #session.indexPage">
                            <li class="active"><a href="admin/admin_userDel#contents"><s:property /> <span class="sr-only">(current)</span></a></li>
                        </s:if>
                        <s:else>
                            <li class="getUsers"><a href="admin/admin_userDel#contents"><s:property /> </a></li>
                        </s:else>
                    </s:iterator>
                </s:elseif>
                <li class="userLast" id="last${session.pages}">
                    <a href="admin/admin_userDel#contents" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </s:if>

        </ul>
    </nav>
</div>
<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="adminJS/js/deleteContent.js"></script>


