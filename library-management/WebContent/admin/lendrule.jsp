<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>借阅规则</title>
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<link rel="stylesheet" href="../static/css/table.css">

	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" charset="utf-8" type="text/javascript"></script>
</head>
<body>
	<table class="layui-hide" id="cardTable" lay-filter="formFilter"></table>
	<!-- 操作按钮 -->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs layui-btn-warm" lay-event="update">修改</a>
	</script>
	
	<script type="text/javascript" src="../static/js/lendrule.js" ></script>
</body>
</html>