<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>图书统计</title>
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" type="text/javascript"></script>
</head>
<style>
	body{
		background-color: #F2F2F2;
	}
	.layui-card{
		background-color: #FFFFFF;
	}
	.layui-card-header{
		font-size: 18px;
	}
	.layui-btn{
		height: 100px;
		background-color: #F2F2F2;
		margin: 30px 0px 0px 60px;
	}
	.layui-btn p{
		font-size: 20px;
	}
	.layui-btn span{
		color: #009688; 
		font-size: 26px;
	}
</style>
<body>
	<div class="layui-row layui-col-space20">
		<div class="layui-col-md6">
			<div class="layui-card" id="one" style="margin: 20px 10px">
				<div class="layui-card-header"><b>图书类别统计</b></div>
				<div class="layui-card-body">
					<div id="type" style="width: 680px;height:400px;"></div>
				</div>
			</div>
		</div>
		<div class="layui-col-md6">
			<div class="layui-card" style="margin: 20px 0px 0px 10px">
				<div class="layui-card-header"><b>图书出版社统计</b></div>
				<div class="layui-card-body">
					<div id="press" style="width: 550px;height:400px;"></div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="../static/layuimini/js/lay-module/echarts/echarts.js"></script>
	<script type="text/javascript" src="../static/js/statistics.js"></script>
</body>
</html>