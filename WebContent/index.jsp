<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="bookmark"href="favicon.ico" />
    <link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css" media="all">
    <link rel="stylesheet" href="static/layuimini/css/layuimini.css" media="all">
    <link rel="stylesheet" href="static/layuimini/css/themes/default.css" media="all">
    <link rel="stylesheet" href="static/layuimini/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js" charset="utf-8"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" charset="utf-8"></script>
	<script src="static/layuimini/js/lay-config.js" charset="utf-8"></script>
	<script src="static/js/router.js" charset="utf-8"></script>
	<script src="static/js/index.js" charset="utf-8"></script>
	<title>首页</title>
</head>
<body class="layui-layout-body layuimini-all">
	<div class="layui-layout layui-layout-admin">
	    <div class="layui-header header">
	        <div class="layui-logo layuimini-logo"></div>
	        <div class="layuimini-header-content">
	            <a><div class="layuimini-tool"><i title="展开" class="fa fa-outdent" data-side-fold="1"></i></div></a>
	            <!--电脑端头部菜单-->
	            <ul class="layui-nav layui-layout-left layuimini-header-menu layuimini-menu-header-pc layuimini-pc-show"></ul>
	            <!--手机端头部菜单-->
	            <ul class="layui-nav layui-layout-left layuimini-header-menu layuimini-mobile-show">
	                <li class="layui-nav-item">
	                    <a href="javascript:;"><i class="fa fa-list-ul"></i> 选择模块</a>
	                    <dl class="layui-nav-child layuimini-menu-header-mobile">
	                    </dl>
	                </li>
	            </ul>
	            <ul class="layui-nav layui-layout-right" style="margin-left: 50px;">
	                <li class="layui-nav-item layuimini-setting"style="margin-left: 50px; ">
	                    <a  href="javascript:;"><span id="username">用户</span></a>
	                    <dl class="layui-nav-child">
	                        <dd>
	                            <a href="javascript:;" layuimini-content-href="user/baseInfo.jsp" data-title="基本资料" data-icon="fa fa-gears">基本资料<span class="layui-badge-dot"></span></a>
	                        </dd>
	                        <dd>
	                            <hr>
	                        </dd>
	                        <dd>
	                            <a href="javascript:;" class="login-out">退出登录</a>
	                        </dd>
	                    </dl>
	                </li>
	                <li class="layui-nav-item layuimini-setting"style="margin-left: 50px;">
	                </li>
	            </ul>
	        </div>
	    </div>
	    <!--无限极左侧菜单-->
	    <div class="layui-side layui-bg-black layuimini-menu-left">
	    </div>
	    <!--初始化加载层-->
	    <div class="layuimini-loader">
	        <div class="layuimini-loader-inner"></div>
	    </div>
	    <!--手机端遮罩层-->
	    <div class="layuimini-make"></div>
	    <!-- 移动导航 -->
	    <div class="layuimini-site-mobile"><i class="layui-icon"></i></div>
	    <div class="layui-body">
	        <div class="layuimini-tab layui-tab-rollTool layui-tab" lay-filter="layuiminiTab" lay-allowclose="true">
	            <ul class="layui-tab-title">
	                <li class="layui-this" id="layuiminiHomeTabId" lay-id=""></li>
	            </ul>
	            <div class="layui-tab-control">
	                <li class="layuimini-tab-roll-left layui-icon layui-icon-left"></li>
	                <li class="layuimini-tab-roll-right layui-icon layui-icon-right"></li>
	                <li class="layui-tab-tool layui-icon layui-icon-down">
	                    <ul class="layui-nav close-box">
	                        <li class="layui-nav-item">
	                            <a href="javascript:;"><span class="layui-nav-more"></span></a>
	                            <dl class="layui-nav-child">
	                                <dd><a href="javascript:;" layuimini-tab-close="current">关 闭 当 前</a></dd>
	                                <dd><a href="javascript:;" layuimini-tab-close="other">关 闭 其 他</a></dd>
	                                <dd><a href="javascript:;" layuimini-tab-close="all">关 闭 全 部</a></dd>
	                            </dl>
	                        </li>
	                    </ul>
	                </li>
	            </div>
	            <div class="layui-tab-content">
	                <div id="layuiminiHomeTabIframe" class="layui-tab-item layui-show"></div>
	            </div>
	        </div>
	    </div>
	</div>
</body>
</html>