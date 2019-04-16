<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%-- 引入jstl --%>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <!-- 屏幕适配 -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>秒杀列表页</title>

    <!-- 静态包含：最终jsp会合并，作为一个jsp输出;
    动态包含是一个jsp作为一个独立的servlet，先把header.jsp转化为servlet将运行结果于list.jsp转化的servlet运行结果合并-->
    <%@include file="common/header.jsp"%>
</head>
<body>
    <!-- 页面显示部分 -->
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panle-body">
                <table class="table table-hover">
                    <thead>
                        <th>名称</th>
                        <th>库存</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <%--<th>创建时间</th>--%>
                        <th>详情页</th>
                    </thead>
                    <tbody>
                        <c:forEach var="sk" items="${list}">
                            <tr>
                                <td>${sk.name}</td>
                                <td>${sk.number}</td>
                                <td>
                                    <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <%--<td>
                                    <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>--%>
                                <td>
                                    <a class="btn btn-info" href="/seckill/${seckillId}/detail" target="_blank">link</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="/assets/javascripts/jquery/jquery.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/assets/javascripts/bootstarp/js/bootstrap.js"></script>
</html>