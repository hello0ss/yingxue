<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        //初始化一个表格
        //父表格
        $("#table").jqGrid({
            url: "${path}/classes/queryFatherClassesMethod",
            editurl:"${path}/classes/ClassesMethod",
            datatype: "json",
            rowNum: 10,
            pager: "#page",  //工具栏
            viewrecords: true,   //是否显示总条数
            styleUI: "Bootstrap",//表格样式
            height: "auto",
            autowidth: true,
            colNames: ["ID","名称","级别"],
            colModel: [
                {name:"id",align:"center",width:30},
                {name:"classname",align:"center",editable:true,width:60},
                {name:"level",align:"center",width:70},
            ],
            subGrid: true,
            
            subGridRowExpanded: function (subgridId, rowId) {
                //复制子表格内容到父表格
                addSubGrid(subgridId,rowId);
            }
        });
        //父表格工具栏
        $("#table").jqGrid('navGrid', '#page', {add : true,edit : true,del : true,
                                                addtext:"添加",edittext:"修改",deltext:"删除"},
            {
                //关闭对话框
                closeAfterEdit:true,
            },
            {
                //关闭对话框
                closeAfterAdd:true,
            },
            {
                //关闭对话框
                closeAfterDel:true,

                afterSubmit:function(response) {
                    $("#delspan").html(response.responseJSON.message);
                    $("#deljgk").show();
                    setTimeout(function () {
                        $("#deljgk").hide();
                    },3000);
                    return "Hello";
                }
            }
        )
    });

    //子表格
    function addSubGrid(subgridId,rowId) {
        var subgridTableId = subgridId+"table";
        var pagerId = subgridId+"page";

        $("#"+subgridId).html("<table id='"+subgridTableId+"'/> <div id='"+pagerId+"'/>");

        $("#"+subgridTableId).jqGrid({
            url: "${path}/classes/querySonClassesMethod?id="+rowId,
            editurl:"${path}/classes/ClassesMethod?ssname="+rowId,
            datatype: "json",
            rowNum: 4,
            pager: "#"+pagerId,  //工具栏
            viewrecords: true,   //是否显示总条数
            styleUI: "Bootstrap",//表格样式
            height: "auto",
            autowidth: true,
            colNames: ["ID","名称","级别","父类别id"],
            colModel: [
                {name:"id",align:"center",width:30},
                {name:"classname",align:"center",editable:true,width:60},
                {name:"level",align:"center",width:70},
                {name:"parentlevel",align:"center",width:70},
            ],
        });
        //子表格工具栏
        $("#"+subgridTableId).jqGrid('navGrid', '#'+pagerId, {add : true,edit : true,del : true,
                                                addtext:"添加",edittext:"修改",deltext:"删除"},
            {
                //关闭对话框
                closeAfterAdd:true,
            },
            {
                //关闭对话框
                closeAfterAdd:true,
            },
            {
                //关闭对话框
                closeAfterDel:true,

                afterSubmit:function(response) {
                    $("#delspan").html(response.responseJSON.message);
                    $("#deljgk").show();
                    setTimeout(function () {
                        $("#deljgk").hide();
                    },3000);
                    return "Hello";
                }
            })
    }
</script>

<div class="panel panel-success">

    <!--面版头-->
    <div class="panel panel-heading">
        <h2>类别管理</h2>
    </div>
    <!--标签页-->
    <div class="nav nav-tabs">
        <li class="active">
            <a href="#">类别管理</a>
        </li>
    </div>

    <!--警告框-->
    <div id="deljgk" class="alert alert-warning alert-dismissable" role="alert" style="display: none">
        <span id="delspan"/>
    </div>

    <!--初始化表格-->
    <div class="panel panel-body">
        <table id="table" align="center"/>
    </div>
    <!--工具栏-->
   <div id="page"/>

</div>