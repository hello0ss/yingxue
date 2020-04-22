<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script src="${path}/bootstrap/js/jquery.min.js"></script>

<script>
    $(function () {

        //点击搜索按钮
        $("#btnId").click(function(){

            //清空表单
            $("#table").empty();

            $("#table").append("<tr>" +
                "<th>ID</th>" +
                "<th>标题</th>" +
                "<th>描述</th>" +
                "<th>封面</th>" +
                "<th>上传时间</th>" +
                "</tr>");

            //获取内容
            var content = $("#name").val();

            //向后台发请求查询数据
            $.ajax({
                url:"${path}/video/querySearchVideoMethod",
                type:"post",
                dataType:"JSON",
                data:{"content":content},
                success:function (data) {
                    //遍历取出数据
                    $.each(data,function (index, video) {
                        //获取数据渲染页面
                        $("#table").append("<tr>" +
                            "<td>"+video.id+"</td>" +
                            "<td>"+video.title+"</td>" +
                            "<td>"+video.brief+"</td>" +
                            "<td><img src='"+video.imgpath+"' style='width: 200px;height: 100px'/></td>" +
                            "<td>"+video.publishday+"</td>" +
                            "</tr>");
                    })
                }
            });
        });
    });


</script>

<!--展示搜索内容-->
<div class="panel panel-success">
    <!--面版头-->
    <div class="panel panel-heading" align="center">
        <div class="input-group" style="width: 500px">
            <input id="name" type="text" class="form-control" placeholder="请输入视频标题|简介" aria-describedby="basic-addon2">
            <span class="input-group-btn" id="basic-addon2">
                <button class="btn bg-info" id="btnId">搜索</button>
            </span>
        </div>
    </div>
    <!--搜索内容-->
    <h3>搜索结果:</h3>

        <table id="table" class="table" align="center"/>

</div>