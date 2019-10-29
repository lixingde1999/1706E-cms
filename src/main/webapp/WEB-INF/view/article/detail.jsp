<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/resource/css/bootstrap.min.css">
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
</head>
<body>
	<div  class="container">
		<div style="float: left;">
			<dl>
				<dt>
					<a href="javascript:window.close()">关闭窗口</a>
				</dt>
				<dt>${article.title }</dt>
				<hr />

				<dd>${article.content }</dd>
				<dd>文章标签：${article.tags}</dd>
				<dd>
					<ul class="pagination">
						<li class="page-item">${adjStr}</li>
					</ul>
				</dd>

				<dd>
					<div>
						<form action="">
							<input type="text" name="content">
							 <input type="button" value="评论" onclick="commnent()">
						</form>
					</div>
					<hr>
					评论数量：${article.commentCnt }
				</dd>
				<dd>
					<div id="commnentList"></div>  
				</dd>
			</dl>
		</div>
		<div style="float: right;" class="col-md-3">
			<div class="card">
				<div class="card-header">排行榜</div>
				<div class="card-body">
					<ol>
						<c:forEach items="${lastList}" var="article">
							<li class="text-truncate"><a  href="javascript:myopen(${article.id })">${article.title}</a></li>
						</c:forEach>
					</ol>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">


/* 
$(function(){
	$("#commentList").load("/commnent/getlist?articleId=${article.id}" );
}); */


/**
* 发表评论
*/
function commnent(){
	//获取评论内容
	var retext=$("[name='content']").val();
	//获取评论的id
	var id=${article.id}
	//评论内容不为空才可以发表
	if(retext!=""){
		$.ajax({ 
			type:"post",//请求的方式
			data:{content:retext,articleId:id},//请求的参数
			url:"/article/comment",//请求地址
			success:function(msg){  //成功后回调
				if(msg.result==1){ 
					alert("发表成功") 
					//刷新评论列表
					$("#commnentList").load("/article/getclist?articleId=${article.id}" );
				}else{
					//提示失败的原因
					alert(msg.errorMsg)
				}
			}
		})
	}else{
		alert("请输入评论内容")
	}
}

$("#commnentList").load("/article/getclist?articleId=${article.id}" );
//增加点击次数
	$.post("/article/addHits",{id:${article.id}},function(data){
	
	},"json");
		
	</script>


</body>
</html>