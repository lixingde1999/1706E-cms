<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户列表</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>
<script type="text/javascript">


$(function(){
	
	
	
	$(".form-control-sm").change(function(){
		
		$("#content-wrapper").load("/admin/list?locked="+$(this).val())
	})
	//下拉框回显
	$(".form-control-sm").val('${locked}')
})


//用户管理 禁用和解封
function update(id,locked){
	var result = confirm("您确定要禁止该用户吗？");
	if(!result)
		return;
	$.post("userupadte",{id:id,locked:locked},function(data){
		if(data)
			$("#content-wrapper").load("list");
		})
	
}

</script>
</head>
<body>

<div class="container-fluid">
<table>
<tr>
<td>
		     用户状态
			  <select class="form-control-sm" >
			    <option value="1">被禁止</option>
			    <option value="0">未禁止</option>
			  </select>
</tr>
</table>

<table class="table" border="1" align="right" >
	<tr align="center">
		<td>编号</td>
		<td>用户名</td>
		<td>创建时间</td>
		<td>用户状态</td>
		<td>管理操作</td>
	</tr>
	<c:forEach items="${pageInfo.list }" var="u" varStatus="index">
	<tr align="center">
		<td>${index.index+1 }</td>
		<td>${u.username }</td>
		<td><fmt:formatDate value="${u.create_time }" pattern="yyyy年MM月dd日  HH:mm:ss"/> </td>
		<td>${u.locked==0?"未禁止":u.locked==1?"已禁止":"禁止"}</td>
		<td>
		<c:if test="${u.locked==0}">
		<button type="button" class="btn btn-outline-warning"  onclick="update(${u.id},'1')">禁止该用户</button>
		</c:if> 
		<c:if test="${u.locked==1}">
		<button type="button" class="btn btn-outline-success"  onclick="update(${u.id},'0')">解封该用户</button>
		</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
	<table>
		<tr>
		<td>${page}</td>
		</tr>
	</table>			
</div>		
</body>
</html>