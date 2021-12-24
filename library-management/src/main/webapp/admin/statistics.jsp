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
		text-align:center;
	}
	.layui-btn{
		height: 100px;
		background-color: #F2F2F2;
	}
	.layui-btn p{
		font-size: 20px;
	}
	.layui-btn span{
		color: #009688; 
		font-size: 26px;
	}
	#one{
		 text-align: center;
	}
</style>
<body>
	<div class="layui-row layui-col-space30">
		<div class="layui-col-sm12 layui-inline">
			<div class="layui-card" id="one">
				<div class="layui-card-header"><b>统计</b></div>
				<div class="layui-card-body">
					<button type="button" class="layui-btn layui-btn-primary layui-btn-lg">
						<p>在线人数</p>
						<span id="onlineNum">0</span>
					</button>
					<button type="button" class="layui-btn layui-btn-primary layui-btn-lg">
						<p>图书总数</p>
						<span id="bookNum">0</span>
					</button>
					<button type="button" class="layui-btn layui-btn-primary layui-btn-lg">
						<p>借阅数量</p>
						<span id="LendNum">0</span>
					</button>
				</div>
			</div>
		</div>
		<div class="layui-col-md6">
			<div class="layui-card" >
				<div class="layui-card-header"><b>出版社统计</b></div>
				<div class="layui-card-body">
					<div id="press" style="width: 550px;height:400px;"></div>
				</div>
			</div>
		</div>
		<div class="layui-col-md6">
			<div class="layui-card" >
				<div class="layui-card-header"><b>图书分类统计</b></div>
				<div class="layui-card-body">
					<div id="type" style="width: 680px;height:400px;"></div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="../static/layuimini/js/lay-module/echarts/echarts.js"></script>
	<script type="text/javascript" src="../static/js/statistics.js"></script>
	<script type="text/javascript">
		var projectPath = getProjectPath();
		var user = getUser();
		$(function(){
			//在线人数统计
			$.ajax({
				type:'get',
				url:projectPath+"/statistics",
				data:{
					"method":"getStatistics",
					"user_id":user.user_id,
					"sessionID":localStorage.sessionID,
				},
				success: function(res) {
					if (res.code == 1000) {
						InsertValue("LendNum", res.data.LendNum);
						InsertValue("bookNum", res.data.bookNum);
						InsertValue("onlineNum", res.data.onlineNum);
					}
				}
			})
		})
		
		
		function InsertValue(id, value) {
			document.getElementById(id).innerText = value;
		}
	</script>
</body>
</html>