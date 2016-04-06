<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: sangzhenya
  Date: 2016/4/3
  Time: 21:29
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
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="adminJS/dist/css/skins/_all-skins.min.css">

    <link rel="stylesheet" href="adminJS/style/adminindex.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">


    <s:include value="headerSide.jsp"></s:include>

    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                后台管理主页
                <small>
                    展示&预览
                </small>
            </h1>

            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
                <li class="active">主页</li>
            </ol>
        </section>
        <section class="content">

            <div class="row">
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <div class="small-box bg-aqua">
                        <div class="inner">
                            <h3><s:property value="#session.totalCount"/> </h3>
                            <p>总浏览</p>
                        </div>
                        <div class="icon">
                            <i class="fa fa-shopping-cart"></i>
                        </div>
                     <%--   <a href="" class="small-box-footer">
                            More info <i class="fa fa-arrow-circle-right"></i>
                        </a>--%>
                    </div>
                </div><!-- ./col -->
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <div class="small-box bg-green">
                        <div class="inner">
                            <h3><s:property value="#session.weekCount"/></h3>
                            <p>本周</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <%--<a href="" class="small-box-footer">
                            More info <i class="fa fa-arrow-circle-right"></i>
                        </a>--%>
                    </div>
                </div><!-- ./col -->
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <div class="small-box bg-yellow">
                        <div class="inner">
                            <h3><s:property value="#session.dayCount"/></h3>
                            <p>今天</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-person-add"></i>
                        </div>
                   <%--     <a href="" class="small-box-footer">
                            More info <i class="fa fa-arrow-circle-right"></i>
                        </a>--%>
                    </div>
                </div><!-- ./col -->
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <div class="small-box bg-red">
                        <div class="inner">
                            <h3><s:property value="#session.currentCount"/></h3>
                            <p>当前</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                       <%-- <a href="" class="small-box-footer">
                            More info <i class="fa fa-arrow-circle-right"></i>
                        </a>--%>
                    </div>
                </div><!-- ./col -->
            </div><!-- /.row -->
            <div class="row">
                <div class="col-md-6 col-xs-12" id="week-counts"></div>
                <div class="col-md-6 col-xs-12" id="class-counts"></div>
            </div>
        </section>

        <section class="content">
            <div class="row">
                <div class="col-md-6 col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="ion ion-clipboard"></i>
                            <h3 class="box-title">To Do List</h3>
                            <div class="box-tools pull-right">
                                <ul class="pagination pagination-sm inline">
                                    <li><a href="#">&laquo;</a></li>
                                    <li><a href="#">1</a></li>
                                    <li><a href="#">2</a></li>
                                    <li><a href="#">3</a></li>
                                    <li><a href="#">&raquo;</a></li>
                                </ul>
                            </div>
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <ul class="todo-list">
                                <li>
                                    <!-- drag handle -->
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                                    <!-- checkbox -->
                                    <input type="checkbox" value="" name="">
                                    <span class="text">Design a nice theme</span>
                                    <!-- Emphasis label -->
                                    <small class="label label-danger"><i class="fa fa-clock-o"></i> 2 mins</small>
                                    <!-- General tools such as edit or delete-->
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                                    <input type="checkbox" value="" name="">
                                    <span class="text">Make the theme responsive</span>
                                    <small class="label label-info"><i class="fa fa-clock-o"></i> 4 hours</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                                    <input type="checkbox" value="" name="">
                                    <span class="text">Let theme shine like a star</span>
                                    <small class="label label-warning"><i class="fa fa-clock-o"></i> 1 day</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                                    <input type="checkbox" value="" name="">
                                    <span class="text">Let theme shine like a star</span>
                                    <small class="label label-success"><i class="fa fa-clock-o"></i> 3 days</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                                    <input type="checkbox" value="" name="">
                                    <span class="text">Check your messages and notifications</span>
                                    <small class="label label-primary"><i class="fa fa-clock-o"></i> 1 week</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                                    <input type="checkbox" value="" name="">
                                    <span class="text">Let theme shine like a star</span>
                                    <small class="label label-default"><i class="fa fa-clock-o"></i> 1 month</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                            </ul>
                        </div><!-- /.box-body -->
                        <div class="box-footer clearfix no-border">
                            <button class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add item</button>
                        </div>
                    </div><!-- /.box -->
                </div>

                <div class="col-md-6 col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="fa fa-send"></i>
                            <h3 class="box-title">comment activity</h3>
                            <div class="box-tools pull-right">
                                <ul class="pagination pagination-sm inline">
                                    <li><a href="#">&laquo;</a></li>
                                    <li><a href="#">1</a></li>
                                    <li><a href="#">2</a></li>
                                    <li><a href="#">3</a></li>
                                    <li><a href="#">&raquo;</a></li>
                                </ul>
                            </div>
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <ul class="todo-list">
                                <li>
                                    <!-- drag handle -->
                      <span class="handle">
                        <i class="fa fa-tv "></i>
                      </span>
                                    <!-- checkbox -->
                                    <span class="text">Design a nice theme</span>
                                    <!-- Emphasis label -->
                                    <small class="label label-danger"><i class="fa fa-clock-o"></i> 2 mins</small>
                                    <!-- General tools such as edit or delete-->
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                             <i class="fa fa-tv "></i>
                      </span>
                                    <span class="text">Make the theme responsive</span>
                                    <small class="label label-info"><i class="fa fa-clock-o"></i> 4 hours</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                         <i class="fa fa-tv "></i>
                      </span>
                                    <span class="text">Let theme shine like a star</span>
                                    <small class="label label-warning"><i class="fa fa-clock-o"></i> 1 day</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                             <i class="fa fa-tv "></i>
                      </span>
                                    <span class="text">Let theme shine like a star</span>
                                    <small class="label label-success"><i class="fa fa-clock-o"></i> 3 days</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                         <i class="fa fa-tv "></i>
                      </span>
                                    <span class="text">Check your messages and notifications</span>
                                    <small class="label label-primary"><i class="fa fa-clock-o"></i> 1 week</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                                <li>
                      <span class="handle">
                         <i class="fa fa-tv "></i>
                      </span>
                                    <span class="text">Let theme shine like a star</span>
                                    <small class="label label-default"><i class="fa fa-clock-o"></i> 1 month</small>
                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                </li>
                            </ul>
                        </div><!-- /.box-body -->
                        <div class="box-footer clearfix no-border">
                            <button class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add item</button>
                        </div>
                    </div><!-- /.box -->
                </div>

            </div>

            <div class="row">
                <div class="col-md-4 col-xs-12">
                    <!-- DIRECT CHAT PRIMARY -->
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Recently Added Products</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                            </div>
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <ul class="products-list product-list-in-box">
                                <li class="item">
                                    <div class="product-img">
                                        <img src="adminJS/dist/img/default-50x50.gif" alt="Product Image">
                                    </div>
                                    <div class="product-info">
                                        <a href="javascript:;" class="product-title">Bicycle </a>
                        <span class="product-description">
                          26" Mongoose Dolomite Men's 7-speed, Navy Blue.
                        </span>
                                    </div>
                                </li><!-- /.item -->
                                <li class="item">
                                    <div class="product-img">
                                        <img src="adminJS/dist/img/default-50x50.gif" alt="Product Image">
                                    </div>
                                    <div class="product-info">
                                        <a href="javascript:;" class="product-title">Xbox One </a>
                        <span class="product-description">
                          Xbox One Console Bundle with Halo Master Chief Collection.
                        </span>
                                    </div>
                                </li><!-- /.item -->
                                <li class="item">
                                    <div class="product-img">
                                        <img src="adminJS/dist/img/default-50x50.gif" alt="Product Image">
                                    </div>
                                    <div class="product-info">
                                        <a href="javascript:;" class="product-title">PlayStation 4 </a>
                        <span class="product-description">
                          PlayStation 4 500GB Console (PS4)
                        </span>
                                    </div>
                                </li><!-- /.item -->
                            </ul>
                        </div><!-- /.box-body -->
                        <div class="box-footer text-center">
                            <a href="javascript:;" class="uppercase">View All Products</a>
                        </div><!-- /.box-footer -->
                    </div><!-- /.box -->
                </div><!-- /.col -->

                <div class="col-md-4 col-xs-12">
                    <!-- DIRECT CHAT PRIMARY -->
                    <div class="box box-primary direct-chat direct-chat-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Send Message</h3>
                            <div class="box-tools pull-right">
                                <span data-toggle="tooltip" title="3 New Messages" class="badge bg-light-blue">3</span>
                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                            </div>
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <!-- Conversations are loaded here -->
                            <div class="direct-chat-messages">
                                <!-- Message. Default to the left -->
                                <div class="direct-chat-msg">
                                    <div class="direct-chat-info clearfix">
                                        <span class="direct-chat-name pull-left">Alexander Pierce</span>
                                        <span class="direct-chat-timestamp pull-right">23 Jan 2:00 pm</span>
                                    </div><!-- /.direct-chat-info -->
                                    <img class="direct-chat-img" src="adminJS/dist/img/user1-128x128.jpg"
                                         alt="message user image"><!-- /.direct-chat-img -->
                                    <div class="direct-chat-text">
                                        Is this template really for free? That's unbelievable!
                                        Is this template really for free? That's unbelievable!
                                    </div><!-- /.direct-chat-text -->
                                </div><!-- /.direct-chat-msg -->

                                <div class="direct-chat-msg">
                                    <div class="direct-chat-info clearfix">
                                        <span class="direct-chat-name pull-left">Alexander Pierce</span>
                                        <span class="direct-chat-timestamp pull-right">23 Jan 2:00 pm</span>
                                    </div><!-- /.direct-chat-info -->
                                    <img class="direct-chat-img" src="adminJS/dist/img/user1-128x128.jpg"
                                         alt="message user image"><!-- /.direct-chat-img -->
                                    <div class="direct-chat-text">
                                        Is this template really for free? That's unbelievable!
                                        Is this template really for free? That's unbelievable!
                                    </div><!-- /.direct-chat-text -->
                                </div><!-- /.direct-chat-msg -->


                            </div><!--/.direct-chat-messages-->

                            <!-- Contacts are loaded here -->
                            <div class="direct-chat-contacts">
                                <ul class="contacts-list">
                                    <li>
                                        <a href="#">
                                            <img class="contacts-list-img" src="adminJS/dist/img/user1-128x128.jpg">
                                            <div class="contacts-list-info">
                            <span class="contacts-list-name">
                              Count Dracula
                              <small class="contacts-list-date pull-right">2/28/2015</small>
                            </span>
                                                <span class="contacts-list-msg">How have you been? I was...</span>
                                            </div><!-- /.contacts-list-info -->
                                        </a>
                                    </li><!-- End Contact Item -->
                                </ul><!-- /.contatcts-list -->
                            </div><!-- /.direct-chat-pane -->
                        </div><!-- /.box-body -->
                        <div class="box-footer">
                            <form action="#" method="post">
                                <div class="input-group">
                                    <input type="text" name="message" placeholder="Type Message ..."
                                           class="form-control">
                      <span class="input-group-btn">
                        <button type="button" class="btn btn-primary btn-flat">Send</button>
                      </span>
                                </div>
                            </form>
                        </div><!-- /.box-footer-->
                    </div><!--/.direct-chat -->
                </div><!-- /.col -->

                <div class="col-md-4 col-xs-12">
                    <!-- DIRECT CHAT PRIMARY -->
                    <div class="box box-primary direct-chat direct-chat-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Direct Chat</h3>
                            <div class="box-tools pull-right">
                                <span data-toggle="tooltip" title="3 New Messages" class="badge bg-light-blue">3</span>
                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                            </div>
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <!-- Conversations are loaded here -->
                            <div class="direct-chat-messages">
                                <!-- Message. Default to the left -->
                                <div class="direct-chat-msg">
                                    <div class="direct-chat-info clearfix">
                                        <span class="direct-chat-name pull-left">Alexander Pierce</span>
                                        <span class="direct-chat-timestamp pull-right">23 Jan 2:00 pm</span>
                                    </div><!-- /.direct-chat-info -->
                                    <img class="direct-chat-img" src="adminJS/dist/img/user1-128x128.jpg"
                                         alt="message user image"><!-- /.direct-chat-img -->
                                    <div class="direct-chat-text">
                                        Is this template really for free? That's unbelievable!
                                        Is this template really for free? That's unbelievable!
                                    </div><!-- /.direct-chat-text -->
                                </div><!-- /.direct-chat-msg -->

                                <!-- Message to the right -->
                                <div class="direct-chat-msg right">
                                    <div class="direct-chat-info clearfix">
                                        <span class="direct-chat-name pull-right">Sarah Bullock</span>
                                        <span class="direct-chat-timestamp pull-left">23 Jan 2:05 pm</span>
                                    </div><!-- /.direct-chat-info -->
                                    <img class="direct-chat-img" src="adminJS/dist/img/user3-128x128.jpg"
                                         alt="message user image"><!-- /.direct-chat-img -->
                                    <div class="direct-chat-text">
                                        You better believe it!
                                    </div><!-- /.direct-chat-text -->
                                </div><!-- /.direct-chat-msg -->
                            </div><!--/.direct-chat-messages-->

                            <!-- Contacts are loaded here -->
                            <div class="direct-chat-contacts">
                                <ul class="contacts-list">
                                    <li>
                                        <a href="#">
                                            <img class="contacts-list-img" src="adminJS/dist/img/user1-128x128.jpg">
                                            <div class="contacts-list-info">
                            <span class="contacts-list-name">
                              Count Dracula
                              <small class="contacts-list-date pull-right">2/28/2015</small>
                            </span>
                                                <span class="contacts-list-msg">How have you been? I was...</span>
                                            </div><!-- /.contacts-list-info -->
                                        </a>
                                    </li><!-- End Contact Item -->
                                </ul><!-- /.contatcts-list -->
                            </div><!-- /.direct-chat-pane -->
                        </div><!-- /.box-body -->
                        <div class="box-footer">
                            <form action="#" method="post">
                                <div class="input-group">
                                    <input type="text" name="message" placeholder="Type Message ..."
                                           class="form-control">
                      <span class="input-group-btn">
                        <button type="button" class="btn btn-primary btn-flat">Send</button>
                      </span>
                                </div>
                            </form>
                        </div><!-- /.box-footer-->
                    </div><!--/.direct-chat -->
                </div><!-- /.col -->


            </div>


        </section>



    </div><!-- /.content-wrapper -->


    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 2.3.0
        </div>
        <strong>Copyright &copy; 2014-2015 <a href="http://almsaeedstudio.com">Almsaeed Studio</a>.</strong> All rights
        reserved.
    </footer>


</div>


<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<!-- jQuery 2.1.4 -->
<script src="adminJS/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="adminJS/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="adminJS/dist/js/app.min.js"></script>


<script>
    var myCounts = new Array();
    <s:iterator value="#session.weekDay" var="weekDay" status="status">
        myCounts[${status.index}] = ${weekDay};
    </s:iterator>
</script>

<script src="adminJS/js/adminindex.js"></script>


</body>
</html>

