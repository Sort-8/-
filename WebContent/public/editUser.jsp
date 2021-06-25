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
	<title>编辑用户</title>
</head>
<script type="text/javascript">
layui.use(['layer','form'],function () {
	var layer = layui.layer;
	var form = layui.form
	form.render();
})
$(function(){
	$('#usr').blur(function() {
		var usr = $('#usr')
		if(usr.val()!='')
			validatePwd(usr);
		else{
			layer.msg("密码不能为空", {icon: 2,time: 1000});
		}
	})
})
</script>
<script type="text/javascript">
//验证密码
function validatePwd(usr){
	var projectPath = getProjectPath();
	var user = getUser();
	$.ajax({
        type:"post",
        url:projectPath +"/user",
        data:{
        	"sessionID":localStorage.sessionID,
            "user_id":user.user_id,
            "method":'searchUser',
            "parmName":'usr',
            "parmValue":usr.val(),
        },
        success:function (data) {
            if (data.count == 1){ //验证密码失败
            	layer.msg("用户已存在", {icon: 2,time: 2000});
            }
        },
        error:function () {
            layer.close(LoadIndex);
            layer.msg("请求超时", {icon: 2,time: 1000});
        }
    });
}
</script>
<body>
	<div id="add_div" style="margin: 12%; ">
		<form class="layui-form layui-form-pane" method="post" id="add_form">
			<input type="text" name="user_id" id="user_id" value="0" class="layui-hide">
			<input type="text" name="auth" id="auth" value="0" class="layui-hide">
			
			<%if("1".equals(request.getParameter("t"))){%>
			<!-- 登录名 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">登录名</label>
			    <div class="layui-input-block">
			      <input type="text" name="usr" id="usr" placeholder="请输入登录名" class="layui-input">
			    </div>
		  	</div>
			<%} %>
			<!-- 密码 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">密码</label>
			    <div class="layui-input-block">
			      <input type="text" name="pwd" id="pwd" placeholder="请输入密码" class="layui-input">
			    </div>
		  	</div>
			
			<!-- 姓名 -->
			<div class="layui-form-item">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-block layui-form" lay-filter="selFilter">
					<input type="text" name="name" id="name" placeholder="请输入姓名" class="layui-input">
				</div>
			</div>
			
			<!-- 邮箱 -->
		    <div class="layui-form-item">
		    <label class="layui-form-label">邮箱</label>
			    <div class="layui-input-block">
			      <input type="text" name="email" id="email" placeholder="请输入邮箱" class="layui-input">
			    </div>
		    </div>
		    
		    <!-- 性别 -->
		    <div class="layui-form-item">
		    <label class="layui-form-label">性别</label>
			    <div class="layui-input-block">
			      <select name="sex" id="sex">
				      <option value="">请选择</option>
				      <option value="男">男</option>
				      <option value="女">女</option>
				    </select>
			    </div>
		    </div>
		    
		    <!-- 角色 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">角色</label>
			    <div class="layui-input-block">
			    	<select name="role_id" id="role_id">
				      <option value="">请选择</option>
				      <option value="1">学生</option>
				      <option value="2">老师</option>
				      <option value="3">其他用户</option>
				    </select>
			    </div>
		  	</div>
		</form>
	</div>
</body>
</html>