<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        //初始化一个表格
        $("#table").jqGrid({
            url: "${path}/admin/showAllLogMethod",
            datatype: "json",
            //rowNum: 2,
            pager: "#page",  //工具栏
            viewrecords: true,   //是否显示总条数
            styleUI: "Bootstrap",//表格样式
            height: "auto",
            autowidth: true,
            colNames: ["ID","操作者名称","时间","具体操作","状态"],
            colModel: [
                {name:"id",align:"center",width:30},
                {name:"adminname",align:"center",width:60},
                {name:"date",align:"center",width:70},
                {name:"operation",align:"center",width:55},
                {name:"status",align:"center",width:55}
            ],
        });
        //表格工具栏
        $("#table").jqGrid('navGrid', '#page',
            {add : false,edit : false,del : false,
             addtext:"添加",edittext:"修改",deltext:"删除"}
            //修改{},//添加{},//删除{}
        );
    });
</script>

<!--初始化一个面板-->
<div class="panel panel-info">

    <!--面板头-->
    <div class="panel panel-heading">
        <h2>日志展示</h2>
    </div>

    <!--标签页-->
    <div class="nav nav-tabs">
        <li class="active">
            <a href="#">日志信息</a>
        </li>
    </div>

    <!--初始化表单-->
    <div class="panel panel-body">
        <table id="table" align="center"/>
    </div>

    <!--分页工具栏-->
    <div id="page"/>

</div>