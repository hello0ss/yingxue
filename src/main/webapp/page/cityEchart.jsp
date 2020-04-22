<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<!--Echarts js-->
<script src="${path}/echarts/js/echarts.js"></script>
<script src="${path}/echarts/js/china.js"></script>
<!--GoEasy js-->
<script type="text/javascript" src="${path}/echarts/js/goeasy-1.0.5.js"></script>
<script type="text/javascript">
    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        $.post("${path}/user/cityUsersMethod",function (data) {

            var series = [];

            for (var i = 0;i<data.length;i++) {
                var ss = data[i];

                series.push({
                    name: ss.title,  //
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:ss.list
                })
            }

            var option = {
                title: {
                    text: '每月用户注册分布图', //标题
                    subtext: '纯属虚构',  //副标题
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ["男", "女"]
                },
                visualMap: {
                    min: 0,
                    max: 50,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: series
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

        },"json");
    });
</script>
<script >
    /*初始化GoEasy对象*/
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-e69ae525ac0b44da9f7baba787ef6306", //替换为您的应用appkey
    });
    //GoEasy-OTP可以对appkey进行有效保护,详情请参考​

    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        //接收消息
        goEasy.subscribe({
            channel: "小囤囤", //替换为您自己的channel
            onMessage: function (message) {
                //alert("Channel:" + message.channel + " content:" + message.content);

                //获取GoEasy数据
                var ss = message.content;

                //将json字符串转为javascript对象
                var data = JSON.parse(ss);

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户月注册统计',  //标题
                        subtext:"纯属虚构",  //副标题
                        link:"${path}/main/main.jsp",  //文本超链接
                        target:"self"  //self：打开当前窗户    blank: 打开新窗口

                    },
                    tooltip: {},  //鼠标提示
                    legend: {
                        data:['男','女']  //选项卡
                    },
                    xAxis: {
                        data: data.month
                    },
                    yAxis: {},  //自适应
                    series: [{
                        name: '男',
                        type: 'line',
                        data: data.boys
                    },{
                        name: '女',
                        type: 'line',
                        data: data.girls
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        })
    })
</script>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div align="center">

        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="main" style="width: 600px;height:400px;"></div>

    </div>

</body>
</html>