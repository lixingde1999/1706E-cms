<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>

<script type="text/javascript">
//友情链接的删除
function deleteone(id){
	var result = confirm("您确定要删除吗？");
	if(!result)
		return;
	$.post("deletelink",{id:id},function(data){
		if(data)
			$("#content-wrapper").load("linklist");
		})
}
function add(){
	$("#content-wrapper").load("/admin/addlink")
}
 
</script>
</head>
<body>
<table class="table" border="1" align="right" >
	<tr align="center">
		<td>编号</td>
		<td>地址</td>
		<td>名称</td>
		<td>管理操作</td>
	</tr>
	<c:forEach items="${linklist }" var="u">
	<tr align="center">
		<td>${u.id}</td>
		<td>${u.http}</td>
		<td>${u.name}</td>
		<td><button type="button" class="btn btn-outline-success" onclick="deleteone(${u.id})">删除</button> </td>
	</tr>
	</c:forEach>
</table>
<button type="button" class="btn btn-outline-success" onclick="add()">添加</button> 
</body>
</html>