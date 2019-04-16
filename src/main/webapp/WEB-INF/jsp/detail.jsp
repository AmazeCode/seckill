<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%-- 引入jstl --%>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!-- 屏幕适配 -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>秒杀详情页</title>

    <!-- 静态包含：最终jsp会合并，作为一个jsp输出;
    动态包含是一个jsp作为一个独立的servlet，先把header.jsp转化为servlet将运行结果于list.jsp转化的servlet运行结果合并-->
    <%@include file="common/header.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="pannel-heading">
                <h1>${seckill.name}</h1>
            </div>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <!--显示time图标-->
                <span class="glyphicon glyphicon-time"></span>
                <!--展示倒计时-->
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
<!-- 登陆弹出层,输入电话 -->
<div id="killPhoneModel" class="model fade">
    <div class="modal-dialog">
        <div class="model-content">
            <div class="model-header">
                <h3 class="model-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>
                </h3>
            </div>
            <div class="model-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                        placeholder="请填写手机号" class="form-control"/>
                    </div>
                </div>
            </div>
            <div class="model-footer">
                <!-- 验证信息 -->
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>
</body>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="/assets/javascripts/jquery/jquery.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/assets/javascripts/bootstarp/js/bootstrap.js"></script>
<!-- 使用CDN 获取公共js http://www.bootcdn.cn/ -->
<!-- jQuery cookie操作插件 -->
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- jQuery countDown倒计时插件 -->
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<!-- 开始编写交互逻辑 -->
<script src="/resources/scripts/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {

        //使用EL表达式传入参数
        seckill.detail.init({
            seckillId : ${seckill.seckillId},
            startTime : ${seckill.startTime.time},//毫秒
            endTime : ${seckill.endTime.time}
        });
    })
</script>
</html>