<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: sangzhenya
  Date: 2016/4/3
  Time: 22:13
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

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ADLTest</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <link rel="stylesheet" href="adminJS/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="adminJS/dist/css/AdminLTE.min.css">

    <link rel="stylesheet" href="adminJS/dist/css/skins/_all-skins.min.css">

    <link rel="stylesheet" href="adminJS/style/adminArticleAdd.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">


   <s:include value="headerSide.jsp"></s:include>

    <div class="content-wrapper">

        <section class="content-header">
            <h1>
                后台管理主页
                <small>
                    展示&预览
                </small>
            </h1>

            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">404 error</li>
            </ol>
        </section>

        <section class="content" id="myCustom">
            <div class="row">
                <div class="col-md-2 col-xs-6">
                    <div class="input-group">
                        <span class="input-group-addon">ID</span>
                        <input type="text" class="form-control" placeholder="id" id="id">
                    </div>
                </div>
                <div class="col-md-1 col-xs-2">
                    <button type="button" class="btn btn-block btn-primary" id="check">查询</button>
                </div>
            </div>
            <div class="row" >
                <div class="col-md-4 col-xs-6">
                    <div class="input-group">
                        <span class="input-group-addon">Title</span>
                        <input type="text" class="form-control" placeholder="title" id="title">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-2 col-xs-3">
                    <div class="input-group">
                        <span class="input-group-addon">CommentUserId</span>
                        <input type="text" class="form-control" placeholder="author" id="userId">
                    </div>
                </div>
                <div class="col-md-1 col-xs-2">
                    <button type="button" class="btn btn-block btn-primary" id="checkUser">查询</button>
                </div>
            </div>
            <div class="row" >
                <div class="col-md-4 col-xs-6">
                    请检查标题是否正确后，再进行添加评论的操作！
                </div>
            </div>
            <div class="row">
                <div class="col-md-3 col-xs-6">
                    <div class="input-group">
                        <span class="input-group-addon">CommentUserName</span>
                        <input type="text" class="form-control" placeholder="author" id="userName">
                    </div>
                </div>
                <div class="col-md-3 col-xs-6">
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask id="date">
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-9 col-xs-12">
                    <div class="form-group">
                        <label>内容</label>
                        <textarea class="form-control" rows="3" placeholder="Enter ..." style="height: 100px;" id="content"></textarea>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 col-xs-6 col-md-offset-6" id="tips">

                </div>
                <div class="col-md-1 col-xs-6 ">
                    <div class="form-group">
                        <button type="button" class="btn btn-block btn-primary" id="commentSUbmit">提交</button>
                    </div><!-- /.form-group -->
                </div>
            </div>
        </section>


    </div>


    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 2.3.0
        </div>
        <strong>Copyright &copy; 2014-2015 <a href="http://almsaeedstudio.com">Almsaeed Studio</a>.</strong> All rights
        reserved.
    </footer>


</div>




<script src="adminJS/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="adminJS/bootstrap/js/bootstrap.min.js"></script>
<script src="adminJS/plugins/input-mask/jquery.inputmask.js"></script>
<script src="adminJS/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="adminJS/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script src="adminJS/dist/js/app.min.js"></script>


<script src="adminJS/js/adminArticleAdd.js"></script>

</body>
</html>

