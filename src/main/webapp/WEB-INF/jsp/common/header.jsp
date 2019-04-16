<%
    //获取项目名
    String path = request.getContextPath();
    //ip+端口号+项目名
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 引入Bootstrap -->
<link href="/assets/javascripts/bootstarp/css/bootstrap.css" rel="stylesheet">

<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
<!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
<!--用来适配IE浏览器[if lt IE 9]>
<script src="/assets/javascripts/html5shiv/html5shiv.min.js"></script>
<script src="/assets/javascripts/respond/respond.min.js"></script>
<![endif]-->