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
	layui.use(['layer'],function () {
		layer = layui.layer;
	})
	$(function(){
		getUserInfo();
		getUserLimit();
		getUserBorrow();
		$('#old_password').blur(function() {
			var pwd = $('#old_password')
			if(pwd.val()!='')
				validatePwd(pwd);
			else{
				layer.msg("密码不能为空", {icon: 2,time: 1000});
			}
		})
		$('#two_password').blur(function() {
			var one_pwd = $('#new_password')
			var two_pwd = $('#two_password')
			if(one_pwd.val()!=two_pwd.val()){
				layer.msg("密码不一致", {icon: 2,time: 1000});
			}
		})
	})
</script>

<script type="text/javascript">
	var user = getUser();
	var sessionID = localStorage.sessionID;
	var currentPath = getProjectPath();
	var LoadIndex;
	var layer;
	function getUserInfo(){
		layui.use(['layer'],function () {
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
	                setUserInfo(data);
	                layer.close(LoadIndex);
	            },
	            error:function () {
	                layer.close(LoadIndex);
	                layer.msg("请求超时", {icon: 2,time: 1000});
	            }
	        });
		})
	}
	
	//设置基本信息
	function setUserInfo(data){
		document.getElementById('usr').value=(data.data[0].usr);
		document.getElementById('name').value=(data.data[0].name);
		document.getElementById('sex').value=(data.data[0].sex);
		document.getElementById('email').value=(data.data[0].email);
	}
	
	//获取用户限制
	function getUserLimit(){
		layui.use(['layer'],function () {
		    layer = layui.layer;
		    LoadIndex = layer.load(2);
			$.ajax({
	            type:"post",
	            url:currentPath +"/record",
	            data:{
	            	"sessionID":sessionID,
	                "user_id":user.user_id,
	                "method":'getMyLend',
	            },
	            success:function (data) {
	                if (data.code == 1000){ //登录成功
	                	setUserLimit(data);
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
	
	//设置借阅总数
	function setUserLimit(data){
		var lend_num = data.data.length;
		document.getElementById('lendtotal').value=(lend_num);
	}
	
	function getUserBorrow(){
		layui.use(['layer'],function () {
		    layer = layui.layer;
		    LoadIndex = layer.load(2);
			$.ajax({
	            type:"post",
	            url:currentPath +"/limit",
	            data:{
	            	"sessionID":sessionID,
	                "user_id":user.user_id,
	                "method":'searchLimit',
	                "role_id":user.role_id,
	            },
	            success:function (data) {
	                if (data.code == 1000){ //登录成功
	                	setUserBorrow(data);
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
	
	function setUserBorrow(data){
		var use_num = data.data[0].max_number;
		var lendtotal = document.getElementById('lendtotal').value
		document.getElementById('uselend').value=(use_num-lendtotal);
	}
	
	//验证密码
	function validatePwd(pwd){
		$.ajax({
            type:"post",
            url:currentPath +"/user",
            data:{
            	"sessionID":sessionID,
                "user_id":user.user_id,
                "method":'searchUser',
                "parmName":'usr;pwd',
                "parmValue":user.usr+";"+pwd.val(),
            },
            success:function (data) {
                if (data.count == 0){ //验证密码失败
                	layer.msg("密码错误", {icon: 2,time: 1000});
                	pwd.val('');
                	pwd.focus();
                }
                layer.close(LoadIndex);
            },
            error:function () {
                layer.close(LoadIndex);
                layer.msg("请求超时", {icon: 2,time: 1000});
            }
        });
	}

	//提交保存
	function saveChange(){
		$.ajax({
            type:"post",
            url:currentPath +"/user",
            data:{
            	"sessionID":sessionID,
                "user_id":user.user_id,
                "role_id":user.role_id,
                "method":'updateUser',
                "usr":document.getElementById('usr').value,
                "name":document.getElementById('name').value,
                "email":document.getElementById('email').value,
                "sex":document.getElementById('sex').value,
                "re_pwd":document.getElementById('new_password').value,
                "pwd":document.getElementById('old_password').value,
                "auth":user.auth,
            },
            success:function (data) {
                if (data.count == 1){ //验证密码失败
                	layer.msg("修改成功", {icon: 1,time: 1000});
                	document.getElementById('new_password').value='';
                	document.getElementById('old_password').value='';
                	document.getElementById('two_password').value='';
                }else{
                	layer.msg("修改失败", {icon: 2,time: 1000});
                }
                
                layer.close(LoadIndex);
            },
            error:function () {
                layer.close(LoadIndex);
                layer.msg("请求超时", {icon: 2,time: 1000});
            }
        });
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
		            <input type="text" name="usr" id="usr" readOnly placeholder="用户名" class="layui-input">
		        </div>
		    </div>
		
			<div class="layui-form-item" style="margin-left: 1%;">
		        <label class="layui-form-label">真实姓名</label>
		        <div class="layui-input-block">
		            <input type="text" name="name" id="name" lay-verify="required" value="" class="layui-input" >
		        </div>
		    </div>
			
			<div class="layui-form-item" style="margin-left: 1%;">
			    <label class="layui-form-label">邮箱</label>
			    <div class="layui-input-block">
			        <input type="text" name="email" id="email" lay-verify="required" value="" class="layui-input" >
			    </div>
			</div>
			
			<div class="layui-form-item" style="margin-left: 1%;">
			    <label class="layui-form-label">性别</label>
			    <div class="layui-input-block">
			        <input type="text" name="sex" id="sex" lay-verify="required" value="" class="layui-input" >
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
		        <div class="layui-input-block">
		            <button class="layui-btn" type="button" onclick="saveChange()">保存</button>
		        </div>
		    </div>
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
			    <legend>借阅状况</legend>
			</fieldset>
			
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
</body>
</html>