<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>禁止访问该页面</title>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-store,no-cache,must-revalidate">
    <META HTTP-EQUIV="Expires" CONTENT="0">
</head>
<body>
<!--内容-->
<div class="main_div" style="margin-top: 70px">
    <div class="main_content">
        <div class="m_top20">
            <div class="main_warp">
                <div class="error_page">
                    <div class="error_body">
                        <div class="error_body_l">
                            <div class="error_img405"></div>
                        </div>
                        <div class="error_body_r">
                            <h1>禁止访问</h1>

                            <p class="line">HTTP 405 错误 – 方法不被允许 (Method not allowed)</p>

                            <p>☉ 您可以：</p>
                            <ul>
                                <li>单击 <a href="javascript:void(0);" onclick="javascript:ec.util.back();"><font
                                        class="f_red">后退</font></a> 链接，访问其它页面。
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--内容 end-->
</body>
</html>