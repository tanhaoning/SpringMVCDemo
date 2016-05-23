<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE>
<html>
	<head>
		<meta charset="UTF-8"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>请求出错</title>
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
		                <div class="error_page" >
		                    <div   class="error_body">
		                        <div class="error_body_l">
		                        		<div class="error_img"></div>
		                        </div>
		                        <div class="error_body_r">
		                            <h1>请求出错</h1>
		                             <p class="line">HTTP 400 请求错误 – 由于语法格式有误，服务器无法理解此请求。</p> 
		                             <p>☉ 您可以：</p>
		                             <ul>
		                                 <li>检查请求地址，并保证地址正确后再访问。</li>
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