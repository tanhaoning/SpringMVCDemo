<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://mycompany.com/fn" %>
<%@ taglib prefix="p" uri="http://mycompany.com/p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>
        This is a JSP page.
    </title>
</head>
<body>
<h2>Hello ${test.paramCode}</h2>

<p>${test.paramValue}</p>

<h2>${fn:toString("Haha!")}</h2>
<p:page pageNo="5" total="20" theme="text"/>
</body>
</html>