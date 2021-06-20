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
	.layui-input-block input{
		width:"60px"
	}
	.layui-form input{
		width: 30%;
		font-size: medium;
	}
</style>
<script type="text/javascript">
	$(function(){
		getUserInfo();
	})
</script>

<script type="text/javascript">
	var user = getUser();
	var sessionID = localStorage.sessionID;
	var currentPath = getProjectPath();
	var LoadIndex;
	var layer;
	var notice;
	function getUserInfo(){
		layui.use(['layer','notice'],function () {
			notice = layui.notice;
		    layer = layui.layer;
		    LoadIndex = layer.load(2);
			$.ajax({
	            type:"post",
	            url:currentPath +"/user",
	            data:{
	            	"sessionID":sessionID,
	                "user_id":user.user_id,
	                "method":'searchUser',
	                "parmName":'user_id',
	                "parmValue":user.user_id,
	            },
	            success:function (data) {
	                if (data.code == 1000){ //登录成功
	                	setUserInfo(data);
	                	notice.success("显示成功");
	                }
	                layer.close(LoadIndex);
	            },
	            error:function () {
	                layer.close(LoadIndex);
	                layer.msg("请求超时", {icon: 2,time: 1000});
	            }
	        });
		})
	}
	
	function setUserInfo(data){
		//alert(data)
		
	}
	
	function getUserLimit(){
		layui.use(['layer','notice'],function () {
			notice = layui.notice;
		    layer = layui.layer;
		    LoadIndex = layer.load(2);
			$.ajax({
	            type:"post",
	            url:currentPath +"/user",
	            data:{
	            	"sessionID":sessionID,
	                "user_id":user.user_id,
	                "method":'searchUser',
	                "parmName":'user_id',
	                "parmValue":user.user_id,
	            },
	            success:function (data) {
	                if (data.code == 1000){ //登录成功
	                	setUserInfo(data);
	                	notice.success("显示成功");
	                }
	                layer.close(LoadIndex);
	            },
	            error:function () {
	                layer.close(LoadIndex);
	                layer.msg("请求超时", {icon: 2,time: 1000});
	            }
	        });
		})
	}
</script>

<body>
	<div class="layuimini-container">
		<div class="layui-form">
		    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		        <legend>我的信息</legend>
		    </fieldset>
		    <div class="layui-form-item" style="margin-left: 1%;" >
		        <label class="layui-form-label">登录名</label>
		        <div class="layui-input-block">
		            <input type="text" name="userid" id="userid" readOnly placeholder="用户名" class="layui-input">
		        </div>
		    </div>
		
			<div class="layui-form-item" style="margin-left: 1%;">
		        <label class="layui-form-label">原密码</label>
		        <div class="layui-input-block">
		            <input type="password" name="old_password" id="old_password" lay-verify="required" class="layui-input">
		        </div>
		    </div>
		    
		    <div class="layui-form-item" style="margin-left: 1%;">
		        <label class="layui-form-label">新密码</label>
		        <div class="layui-input-block">
		            <input type="password" name="new_password" id="new_password" lay-verify="required" class="layui-input">
		        </div>
		    </div>
		
			<div class="layui-form-item" style="margin-left: 1%;">
		        <label class="layui-form-label">再次输入密码</label>
		        <div class="layui-input-block">
		            <input type="password" name="two_password" id="two_password" lay-verify="required" class="layui-input" >
		        </div>
		    </div>
		
		    <div class="layui-form-item" style="margin-left: 1%;">
		        <label class="layui-form-label">真实姓名</label>
		        <div class="layui-input-block">
		            <input type="text" name="name" id="name" lay-verify="required" value="" class="layui-input" >
		        </div>
		    </div>
		
			<div class="layui-form-item" style="margin-left: 1%;">
			    <label class="layui-form-label">性别</label>
			    <div class="layui-input-block">
			        <input type="text" name="sex" id="sex" lay-verify="required" value="" class="layui-input" >
			    </div>
			</div>
		    <div class="layui-form-item" style="margin-left: 1%;">
		        <div class="layui-input-block">
		            <button class="layui-btn" type="button">保存</button>
		        </div>
		    </div>
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
			    <legend>借阅状况</legend>
			</fieldset>
			
			<div class="layui-form-item" style="margin-left: 1%;">
			    <label class="layui-form-label" >借阅总数</label>
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
</body>
</html>