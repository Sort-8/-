<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" type="text/javascript"></script>
	<title>图书详情</title>
</head>
<style>
#exportBook,#importBook{
	margin-right:200px; 
}
</style>
<body>
	<div id="more_div" style="margin: 8%;">
		<form class="layui-form layui-form-pane" method="post" id="more_form">
			<div class="layui-form-item">
				<label class="layui-form-label">可借阅数</label>
				<div class="layui-input-block">
					<input type="text" id="borrow_num" readOnly name="borrow_num" placeholder="未知"
					class="layui-input" style="width: 150px;">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">借阅次数</label>
				<div class="layui-input-block">
					<input type="text" id="lend_num" readOnly name="lend_num" placeholder="未知"
					  class="layui-input" style="width: 150px;">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">图书类别</label>
				<div class="layui-input-block">
					<input type="text" id="book_type" readOnly name="book_type" placeholder="未知"
					  class="layui-input" style="width: 150px;">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">馆藏</label>
				<div class="layui-input-block">
					<input type="text" id="number" readOnly name="number" placeholder="未知"
					class="layui-input" style="width: 150px;">
				</div>
			</div>
		</form>
	</div>
</body>
</html>