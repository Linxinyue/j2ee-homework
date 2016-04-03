<%--
  Created by IntelliJ IDEA.
  User: sangzhenya
  Date: 2016/4/3
  Time: 21:04
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
    <base href=" <%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>404 error</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="adminJS/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="adminJS/dist/css/AdminLTE.min.css">
</head>
<body class="hold-transition login-page">
<section class="content-header">
    <h1>
        404 Error Page
    </h1>
    <ol class="breadcrumb">
        <li><a href="index"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">404 error</li>
    </ol>
</section>

<!-- Main content -->
<section class="content" style="margin-top: 10%;">
    <div class="error-page">
        <h2 class="headline text-yellow"> 404</h2>
        <div class="error-content">
            <h3><i class="fa fa-warning text-yellow"></i> Oops! Page not found.</h3>
            <p>
                We could not find the page you were looking for.
                Meanwhile, you may <a href="index">return to dashboard</a> or try using the search
                form.
            </p>
            <form class="search-form">
                <div class="input-group">
                    <input type="text" name="search" class="form-control" placeholder="Search">
                    <div class="input-group-btn">
                        <button type="submit" name="submit" class="btn btn-warning btn-flat"><i
                                class="fa fa-search"></i></button>
                    </div>
                </div><!-- /.input-group -->
            </form>
        </div><!-- /.error-content -->
    </div><!-- /.error-page -->
</section><!-- /.content -->



<!-- jQuery 2.1.4 -->
<script src="adminJS/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="adminJS/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
