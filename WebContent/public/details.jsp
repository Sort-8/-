<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<link rel="stylesheet" href="../static/layui/css/layui.css">
<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
<script src="../static/layui/layui.js" type="text/javascript"></script>
<script src="../static/js/router.js" charset="utf-8"></script>
<title>图书详情</title>
</head>
<body>
	<div id="more_div" style="margin: 8%;">
		<form class="layui-form layui-form-pane" method="post" id="more_form">
			<div class="layui-form-item">
				<label class="layui-form-label">借阅次数</label>
				<div class="layui-input-block">
					<input type="text" id="add_type" name="add_type" required lay-verify="required" placeholder="未知"
					 autocomplete="off" class="layui-input" style="width: 150px;">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">图书类别</label>
				<div class="layui-input-block">
					<input type="text" id="add_name" name="add_name" required lay-verify="required" placeholder="未知"
					 autocomplete="off" class="layui-input" style="width: 150px;">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">馆藏</label>
				<div class="layui-input-block">
					<input type="text" id="add_type" name="add_type" required lay-verify="required" placeholder="未知"
					 autocomplete="off" class="layui-input" style="width: 150px;">
				</div>
			</div>
		</form>
	</div>
</body>
</html>