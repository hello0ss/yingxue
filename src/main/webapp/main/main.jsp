<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>持明法州后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <!--引入jqgrid中主题css-->
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <!--引入js文件-->
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>

</head>
<body>

    <!--顶部导航-->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="#" class="navbar-brand">应学视频APP后台管理系统</a>
            </div>
            <div class="collapse navbar-collapse" id="collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="">欢迎：
                        <span class="text text-primary">${admin.name}</span>
                    </a></li>
                    <li><a href="${path}/admin/logoutAdmin">
                        <span class="glyphicon glyphicon-log-out"/>退出登录
                    </a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!--栅格系统-->
    <div class="container-fluid">
        <div class="row">
            <!--左边手风琴部分-->
            <div class="panel panel-group col-md-2" id="accordion" role="tablist" aria-multiselectable="true" align="center">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a role="button" data-toggle="collapse" href="#one" data-parent="#parent">
                                用户管理
                            </a>
                        </h3>
                    </div>
                    <div id="one" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><button class="btn btn-danger">
                                        <a href="javascript:$('#sg').load('${path}/page/user.jsp')"> 用户展示</a>
                                    </button>
                                </li>
                                <li>
                                    <button class="btn btn-danger">
                                        <a href="javascript:$('#sg').load('${path}/page/sexEchart.jsp')">用户统计</a>
                                    </button>
                                </li>
                                <li>
                                    <button class="btn btn-danger">
                                        <a href="javascript:$('#sg').load('${path}/page/cityEchart.jsp')">用户分布</a>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr/>

                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a role="button" data-toggle="collapse" href="#tow" data-parent="#parent">
                                分类管理
                            </a>
                        </h3>
                    </div>
                    <div id="tow" class="panel-collapse collapse">
                        <div class="panel-body">
                            <button class="btn btn-danger">
                                <a href="javascript:$('#sg').load('${path}/page/category.jsp')">分类展示</a>
                            </button>
                        </div>
                    </div>
                </div>

                <hr/>

                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a role="button" data-toggle="collapse" href="#three" data-parent="#parent">
                                视频管理
                            </a>
                        </h3>
                    </div>
                    <div id="three" class="panel-collapse collapse-in">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><button class="btn btn-danger">
                                        <a href="javascript:$('#sg').load('${path}/page/video.jsp')">视频展示</a>
                                    </button>
                                </li>
                                <li><button class="btn btn-danger">
                                    <a href="javascript:$('#sg').load('${path}/page/searchVideo.jsp')">视频搜索</a>
                                </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr/>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a role="button" data-toggle="collapse" href="#four" data-parent="#parent">
                                日志管理
                            </a>
                        </h3>
                    </div>
                    <div id="four" class="panel-collapse collapse">
                        <div class="panel-body">
                            <button class="btn btn-danger">
                                <a href="javascript:$('#sg').load('${path}/page/log.jsp')">日志展示</a>
                            </button>
                        </div>
                    </div>
                </div>

                <hr/>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a role="button" data-toggle="collapse" href="#five" data-parent="#parent">
                                反馈管理
                            </a>
                        </h3>
                    </div>
                    <div id="five" class="panel-collapse collapse">
                        <div class="panel-body">
                            <button class="btn btn-danger">反馈展示</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--右边部分-->
            <div class="col-md-10" id="sg">
                <!--巨幕开始-->
                <div class="jumbotron">
                    <div class="container">
                        <h1>欢迎来到应学视频APP后台管理系统</h1>
                    </div>
                </div>
                <!--轮播图部分-->
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" align="center">
                    <!-- 中间按钮 -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                    </ol>
                    <!-- 图片 -->
                    <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <img src="${path}/bootstrap/img/pic1.jpg" alt="...">
                            <%--<video src="https://ss-yingxue.oss-cn-beijing.aliyuncs.com/video/15.mp4" controls poster="${path}/bootstrap/img/pic2.jpg"/>--%>
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic2.jpg" alt="...">
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic3.jpg" alt="...">
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic4.jpg" alt="...">
                        </div>
                    </div>
                    <!-- 前后页的按钮 -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
    <!--栅格系统-->
        </div>
    </div>
    <!--页脚-->
    <div class="panel panel-footer" align="center">
        @百知教育 zhangcn@zparkhr.com
    </div>

</body>
</html>
