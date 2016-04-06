<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-md-6 col-xs-12">
        <div class="input-group">
            <span class="input-group-addon">标题</span>
            <s:if test="#session.modArticle != null">
                <input type="text" class="form-control" placeholder="title" id="title" value="${session.modArticle.title}">
            </s:if>
            <s:else>
                <input type="text" class="form-control" placeholder="title" id="title" >
            </s:else>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-3 col-xs-6">
        <div class="input-group">
            <span class="input-group-addon">作者</span>
            <s:if test="#session.modArticle != null">
                <input type="text" class="form-control" placeholder="title" id="author" value="${session.modArticle.author}">
            </s:if>
            <s:else>
                <input type="text" class="form-control" placeholder="author" id="author">
            </s:else>
        </div>
    </div>
    <div class="col-md-3 col-xs-6">
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-addon">
                    <i class="fa fa-calendar"></i>
                </div>
                <s:if test="#session.modArticle != null">
                    <input type="text" class="form-control" placeholder="date" id="date" value="<s:property value="#session.modArticle.pub_date.toString().substring(0,10)"/>">
                </s:if>
                <s:else>
                    <input type="text" class="form-control"  id="date">
                </s:else>

            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-9 col-xs-12">
        <div class="form-group">
            <label>摘要</label>
            <s:if test="#session.modArticle != null">
                <textarea class="form-control" rows="3" placeholder="Enter ..." id="abstracts" value="${session.modArticle.abstracts}">${session.modArticle.abstracts}</textarea>
            </s:if>
            <s:else>
                <textarea class="form-control" rows="3" placeholder="Enter ..."  id="abstracts"></textarea>
            </s:else>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-9 col-xs-12">
        <div class="form-group">
            <label>文章</label>
            <s:if test="#session.modArticle != null">
                <textarea class="form-control" rows="3" placeholder="Enter ..." style="height: 350px;" id="content" >${session.modArticle.content}</textarea>
            </s:if>
            <s:else>
                <textarea class="form-control" rows="3" placeholder="Enter ..." style="height: 350px;" id="content"></textarea>
            </s:else>
        </div>
    </div>
</div>


