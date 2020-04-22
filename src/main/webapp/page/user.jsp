<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        //初始化一个表格
        $("#table").jqGrid({
            url: "${path}/user/queryAllMethod",
            editurl:"${path}/user/UsersMethod",
            datatype: "json",
            rowNum: 2,
            pager: "#page",  //工具栏
            viewrecords: true,   //是否显示总条数
            styleUI: "Bootstrap",//表格样式
            height: "auto",
            autowidth: true,
            colNames: ["ID","用户名","手机号","头像","性别","签名","城市","微信","状态","注册时间"],
            colModel: [
                {name:"id",align:"center",width:30},
                {name:"username",align:"center",editable:true,width:60},
                {name:"phone",align:"center",editable:true,width:70},
                {name:"img",align:"center",editable:true,width:130,edittype:"file",
                    //参数;               img列的值, 操作, 行对象(数据)
                    formatter:function (cellvalue,options,rowObject) {
                        return "<img src='${path}/img/"+rowObject.img+"' width='230px' height='100px'/>"
                    }
                },
                {name:"sex",align:"center",editable:true,width:55},
                {name:"sign",align:"center",editable:true,width:95},
                {name:"city",align:"center",editable:true,width:55},
                {name:"wechat",align:"center",editable:true,width:55},
                {name:"status",align:"center",width:55,
                    formatter:function (cellvalue,options,rowObject) {
                        if (cellvalue == 0) {
                            return "<button onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-success'>正常[可冻结]</button>";
                        } else {
                            return "<button onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-danger'>解除冻结</button>";
                        }
                    }
                },
                {name:"registerday",align:"center",width:85,edittype:"data"}
            ],
        });
        //表格工具栏
        $("#table").jqGrid('navGrid', '#page', {add : true,edit : true,del : true,
                                                addtext:"添加",edittext:"修改",deltext:"删除"},
            //修改
            {
                //关闭对话框
                closeAfterAdd:true,
            },
            //添加
            {
                //关闭对话框
                closeAfterAdd:true,
                //文件上传
                afterSubmit:function(response) {
                    $.ajaxFileUpload({
                        url:"${path}/user/uploadImgUsersMethod",
                        type:"post",
                        dataType:"text",
                        fileElementId:"img",
                        data:{id:response.responseText},
                        success:function () {
                            $("#table").trigger("reloadGrid");
                        }
                    });

                    return "Hello";
                }
            },
            //删除
            {}
        );
    });
    //用户状态修改
    function updateStatus(id,status) {
        if (status==0) {
            $.ajax({
                url:"${path}/user/UsersMethod",
                type:"post",
                data:{"id":id,"status":"1","oper":"edit"},
                success:function () {
                    $("#table").trigger("reloadGrid");
                }
            });
        } else {
            $.ajax({
                url:"${path}/user/UsersMethod",
                type:"post",
                data:{"id":id,"status":"0","oper":"edit"},
                success:function () {
                    $("#table").trigger("reloadGrid");
                }
            });
        }
    }
    //用户信息导出Excel
    function easyPoiInport() {
        $.ajax({
            url:"${path}/user/easyPoiUsersPort",
            type:"post",
            success:function (data) {
                $("#table").trigger("reloadGrid");
            }
        });
    }
</script>

<!--初始化一个面板-->
<div class="panel panel-info">

    <!--面板头-->
    <div class="panel panel-heading">
        <h2>用户展示</h2>
    </div>

    <!--标签页-->
    <div class="nav nav-tabs">
        <li class="active">
            <a href="#">用户信息</a>
        </li>
    </div>

    <!--警告框-->
    <div id="deljgk" class="alert alert-warning alert-dismissable" role="alert" style="display: none">
        <span id="delspan"/>
    </div>

    <!--按钮-->
    <button class="btn bg-success" onclick="easyPoiInport()">导出用户信息</button>
    <button class="btn bg-info">导入用户</button>
    <button class="btn bg-danger">测试按钮</button>

    <!--初始化表单-->
    <div class="panel panel-body">
        <table id="table" align="center"/>
    </div>

    <!--分页工具栏-->
    <div id="page"/>

</div>