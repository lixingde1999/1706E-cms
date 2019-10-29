<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 声明变量，赋值 -->
<c:set var="path" value="{pageContext.request.contextPath}" scope="page"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入外部css样式 -->
<link href="<%=request.getContextPath() %>/css/index3.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.8.3.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<form action="addhead_picture" method="post" enctype="multipart/form-data">
<ul class="list-group">
  <li class="list-group-item "><input type="file" name="file"></li>
  <li class="list-group-item"><input type="submit" value="上传"></li>
</ul>
</form>

</body>
</html>