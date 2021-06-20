<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
    <link rel="stylesheet" href="static/layui/css/layui.css">
    <link rel="stylesheet" href="static/css/login.css">
    <link rel="shortcut icon" href="favicon.ico" />
	<link rel="bookmark"href="favicon.ico" />
</head>
<body>
    <div class="wrap">
        <img src="static/img/background.jpg" class="imgStyle">
        <div class="loginForm">
            <form>
                <div class="logoHead">
                    <h2 style="margin-top: 15px;text-align:center">图书管理系统</h2>
                </div>
                <div class="usernameWrapDiv">
                    <div class="usernameLabel">
                        <label>用户名:</label>
                    </div>
                    <div class="usernameDiv">
                        <i class="layui-icon layui-icon-username adminIcon"></i>
                        <input id="loginUsername" class="layui-input adminInput" type="text" name="username" placeholder="输入用户名" >
                    </div>
                </div>
                <div class="usernameWrapDiv">
                    <div class="usernameLabel">
                        <label>密码:</label>
                    </div>
                    <div class="passwordDiv">
                        <i class="layui-icon layui-icon-password adminIcon"></i>
                        <input id="loginPassword" class="layui-input adminInput" type="password" name="password" placeholder="输入密码">
                    </div>
                </div>
                <div class="usernameWrapDiv">
                    <div class="usernameLabel">
                        <label>验证码:</label>
                    </div>
                    <div class="cardDiv">
                        <input id="loginCard" class="layui-input cardInput" type="text" name="card" placeholder="输入验证码">
                    </div>
                    <div class="codeDiv" >
                        <a href="#">
                        	<img id="loginCode" src="http://localhost:8080/library-management/getcode">
                        </a>
                    </div>
                </div>
                <div class="usernameWrapDiv">
                    <div class="submitLabel">
                        <label>没有账号？<a href="#" id="loginRegister">点击注册</a></label>
                    </div>
                    <div class="submitDiv">
                        <input id="loginBtn" type="button" class="submit layui-btn layui-btn-primary" value="登录"></input>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <script src="static/layui/layui.js" type="text/javascript"></script>
    <script src="static/js/router.js" charset="utf-8"></script>
    <script src="static/js/login.js" type="text/javascript"></script>
	
</body>
<div class="registerPage">
    <div class="registerDiv">
        <form>
            <div class="usernameWrapDiv">
                <div class="usernameLabel">
                    <label>用户名:</label>
                </div>
                <div class="usernameDiv">
                    <i class="layui-icon layui-icon-username adminIcon"></i>
                    <input id="registerUsername" onblur="isExist()" class="layui-input adminInput" type="text" name="username" placeholder="输入用户名" >
                </div>
            </div>
            <div class="usernameWrapDiv">
                <div class="usernameLabel">
                    <label>密码:</label>
                </div>
                <div class="passwordDiv">
                    <i class="layui-icon layui-icon-password adminIcon"></i>
                    <input id="registerPassword" class="layui-input adminInput" type="password" name="password" placeholder="输入密码">
                </div>
            </div>
            <div class="usernameWrapDiv">
                <div class="usernameLabel">
                    <label>确认密码:</label>
                </div>
                <div class="passwordDiv">
                    <i class="layui-icon layui-icon-password adminIcon"></i>
                    <input id="registerWellPassword" class="layui-input adminInput" type="password" name="password" placeholder="输入密码">
                </div>
            </div>
            <div class="usernameWrapDiv">
                <div class="usernameLabel">
                    <label>角色类型:</label>
                </div>
                <div class="passwordDiv">
                    <select id="roleSelect" class="layui-select">
                        <option value="">请选择...</option>
                        <option value="0">学生</option>
                        <option value="1">老师</option>
                        <option value="2">其他</option>
                    </select>
                    <input type="text" id="else" name="else" style="display:none">
                </div>
            </div>
        </form>
    </div>
</div>
</html>
