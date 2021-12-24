<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<link rel="stylesheet" href="../static/css/table.css">

	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" charset="utf-8" type="text/javascript"></script>
	<title>用户管理</title>
	
</head>
<style>
	#operate{
		margin-left: 280px
	}
</style>
<body>
	<table class="layui-hide" id="cardTable" lay-filter="formFilter"></table>
	<!-- 头部工具栏 -->
	<script type="text/html" id="headBar">
		搜索方式：
		<div class="layui-inline" style="width:120px">
			<select id="isFuzzyQuery" name="isFuzzyQuery" >
			<option value=""></option>
			<option value="0">精确搜索</option>
			<option value="1">模糊搜索</option>
			</select>
			</div>
		&nbsp;
		条件搜索：
		<div class="layui-inline" style="width:120px">
			<select id="condition" name="condition" >
			<option value="usr"></option>
			<option value="usr">登录名</option>
			<option value="name">姓名</option>
			<option value="email">邮箱</option>
			<option value="sex">性别</option>
			</select>
		</div>
		<div class="layui-inline">
    		<input class="layui-input" id="value" name="value"  style="width:200px">
  		</div>
		<button class="layui-btn" name="search" data-type="reload"  lay-event="search">搜索</button>

		<div class="layui-inline" id="operate">
		<button class="layui-btn layui-btn-sm" name="addUser" data-type="reload"  lay-event="addUser">添加</button>
		<button class="layui-btn layui-btn-sm layui-btn-normal" data-type="reload" id="importUser" lay-event="importUser">导入</button>
		<button class="layui-btn layui-btn-sm layui-btn-normal" data-type="reload" id="importUser" lay-event="exportUser">导出</button>
		</div>
	</script>
	<!-- 操作按钮 -->
	<script type="text/html" id="barDemo">
		{{#  if(d.user_id != localStorage.user_id ){ }}
			<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="update">修改</a>
			<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
		{{#  }else{ }}
			<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="update">修改</a>
			<a class="layui-btn layui-btn-xs layui-btn-disabled">删除</a>
		{{#  } }}
	</script>
	<script type="text/javascript" src="../static/js/usermanager.js"></script>
</body>
</html>