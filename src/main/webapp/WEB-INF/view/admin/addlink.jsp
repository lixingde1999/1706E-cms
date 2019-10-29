<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>

<script type="text/javascript">
 //修改
function add(){
	$.post("/admin/addlink",$("#linkadd").serialize(),function(obj){
		if(obj.result==1){
			alert("处理成功")
			$("#content-wrapper").load("linklist")
		}else{
			alert(obj.errorMsg);
		}
	})
	
}
 
</script>
</head>
<body>
<form action=""  id="linkadd">
	<table class="table" border="1" align="right" >
	<tr align="center">
		<td>地址</td>
		<td><input type="text" name="http" id="http"> </td>
	</tr>
	<tr align="center">
		<td>名称</td>
		<td><input type="text" name="name"  id="name"> </td>
	</tr>
	<tr align="center">
		<td></td>
		<td><input type="button" value="添加"  onclick="add()"> </td>
	</tr>	
</table>
</form>
</body>
</html>