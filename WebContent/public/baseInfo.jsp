<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" type="text/javascript"></script>
	<title>基本信息</title>
</head>
<style>
body{
	background-color: #F2F2F2;
}
.layui-inline input{
	width:100px;
}
.layui-form input{
	width: 40%;
	font-size: medium;
}
.layui-card-header{
	font-size: 18px;
}
.layui-form{
	text-align:center;
}
</style>

<script type="text/javascript" src="../static/js/baseInfo.js"></script>

<body>
<div class="layui-row layui-col-space30">
	<div class="layui-col-sm6">
		<div class="layui-card" id="one">
			<div class="layui-card-header"><b>我的信息</b></div>
			<div class="layui-card-body">
				<div class="layui-form">
			    <div class="layui-form-item">
			        <label class="layui-form-label">登录名</label>
			        <div class="layui-input-block">
			            <input type="text" name="usr" id="usr" readOnly placeholder="用户名" class="layui-input">
			        </div>
			    </div>
			
				<div class="layui-form-item">
			        <label class="layui-form-label">真实姓名</label>
			        <div class="layui-input-block">
			            <input type="text" name="name" id="name" value="" class="layui-input" >
			        </div>
			    </div>
				
				<div class="layui-form-item">
				    <label class="layui-form-label">邮箱</label>
				    <div class="layui-input-block">
				        <input type="text" name="email" id="email"value="" class="layui-input" >
				    </div>
				</div>
				
				<div class="layui-form-item">
				    <label class="layui-form-label">性别</label>
				    <div class="layui-input-block">
				        <input type="text" name="sex" id="sex" value="" class="layui-input" >
				    </div>
				</div>
			
				<div class="layui-form-item">
			        <label class="layui-form-label">原密码</label>
			        <div class="layui-input-block">
			            <input type="password" name="old_password" id="old_password"class="layui-input">
			        </div>
			    </div>
			    
			    <div class="layui-form-item">
			        <label class="layui-form-label">新密码</label>
			        <div class="layui-input-block">
			            <input type="password" name="new_password" id="new_password" class="layui-input">
			        </div>
			    </div>
			
				<div class="layui-form-item">
			        <label class="layui-form-label">再次输入密码</label>
			        <div class="layui-input-block">
			            <input type="password" name="two_password" id="two_password" class="layui-input" >
			        </div>
			    </div>
			
			    <div class="layui-form-item">
			        <div class="layui-input-block">
			            <button class="layui-btn" type="button" onclick="saveChange()">保存</button>
			        </div>
			    </div>
			</div>
		</div>
		</div>
	</div>
	<div class="layui-col-sm4">
		<div class="layui-card" id="one">
			<div class="layui-card-header"><b>借阅限制</b></div>
			<div class="layui-card-body">
				<div class="layui-form-item" style="margin-left: 1%;">
				    <label class="layui-form-label" >当前借阅数</label>
				    <div class="layui-input-block">
				        <input type="text" name="lendtotal" id="lendtotal" readOnly placeholder="0" class="layui-input">
				    </div>
				</div>
				<div class="layui-form-item" style="margin-left: 1%;">
				    <label class="layui-form-label" >可借阅数</label>
				    <div class="layui-input-block">
				        <input type="text" name="uselend" id="uselend" readOnly placeholder="0" class="layui-input">
				    </div>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>