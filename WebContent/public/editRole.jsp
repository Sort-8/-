<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<link rel="stylesheet" href="../static/css/from.css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/cos-js-sdk-v5.js" type="text/javascript" charset="utf-8"></script>
	<script src="../static/js/router.js" type="text/javascript"></script>
	<title>编辑借阅规则</title>
</head>
<body>
	<div id="add_div" style="margin: 12%; ">
		<form class="layui-form layui-form-pane" method="post" id="add_form">
			<input type="text" name="limit_id" id="limit_id" value="0" class="layui-hide">
			<input type="text" name="role_id" id="user_id" value="0" class="layui-hide">
			
			<!-- 角色名 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">角色名</label>
			    <div class="layui-input-block">
			      <input type="text" name="role_name" id="role_name" ReadOnly placeholder="请输入角色名" class="layui-input">
			    </div>
		  	</div>
			
			<!-- 最大借阅数 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">最大借阅数</label>
			    <div class="layui-input-block">
			      <input type="text" name="max_number" id="max_number" placeholder="请输入最大借阅数" class="layui-input">
			    </div>
		  	</div>
			
			<!-- 最长借阅时间 -->
			<div class="layui-form-item">
				<label class="layui-form-label">最长借阅时间</label>
				<div class="layui-input-block layui-form" lay-filter="selFilter">
					<input type="text" name="max_time" id="max_time" placeholder="请输入最长借阅时间" class="layui-input">
				</div>
			</div>
			
		</form>
	</div>
</body>
</html>