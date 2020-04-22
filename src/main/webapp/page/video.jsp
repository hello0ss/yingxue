<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        //初始化一个表格
        //父表格
        $("#table").jqGrid({
            url: "${path}/video/queryAllVideoMethod",
            editurl:"${path}/video/VideoMethod",
            datatype: "json",
            rowNum: 10,
            pager: "#page",  //工具栏
            viewrecords: true,   //是否显示总条数
            styleUI: "Bootstrap",//表格样式
            height: "auto",
            autowidth: true,
            colNames: ["ID","标题","视频(图片)","视频封面","上传时间","描述","所属类别id","所属类别","所属分组","所属用户"],
            colModel: [
                {name:"id",align:"center",width:30},
                {name:"title",align:"center",editable:true,width:60},
                {name:"vipath",align:"center",editable:true,width:70,edittype:"file",
                    //参数;               img列的值, 操作, 行对象(数据)
                    formatter:function (cellvalue,options,rowObject) {
                        return "<video src='"+rowObject.vipath+"' controls poster='"+rowObject.imgpath+"' width='230px' height='100px'/>";
                    }
                },
                {name:"imgpath",align:"center",hidden:true,editable:true,editrules:{edithidden:true},edittype:"file"},
                {name:"publishday",align:"center",width:70},
                {name:"brief",align:"center",editable:true,width:70},
                {name:"classid",align:"center",hidden:true,editable:true,editrules:{edithidden:true}},
                {name:"classes.classname",align:"center",width:70},
                {name:"groupid",align:"center",editable:true,width:70},
                {name:"userid",align:"center",editable:true,width:70},

            ],
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
                //文件上传
                afterSubmit:function(response) {
                    $.ajaxFileUpload({
                        url:"${path}/video/viuploadVideoMethod",
                        type:"post",
                        dataType:"text",
                        fileElementId:"vipath",
                        data:{id:response.responseText},
                        success:function () {
                            $("#table").trigger("reloadGrid");
                        }
                    });
                    return "Hello";

                }
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


</script>

<div class="panel panel-success">

    <!--面版头-->
    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>
    <!--标签页-->
    <div class="nav nav-tabs">
        <li class="active">
            <a href="#">视频信息a</a>
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