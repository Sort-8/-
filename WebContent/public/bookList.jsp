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
	<title>图书展示</title>
	
</head>
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
			<option value=""></option>
			<option value="code">ISBN</option>
			<option value="name">图书名称</option>
			<option value="author">作者</option>
			<option value="press">出版社</option>
			</select>
		</div>
		<div class="layui-inline">
    		<input class="layui-input" id="value" name="value"  style="width:200px">
  		</div>
		<button class="layui-btn" name="search" data-type="reload"  lay-event="search">搜索</button>
		<!-- 管理员页面-->
		<%if("1".equals(request.getParameter("t"))){%>
			<br>
			<br>
			<button class="layui-btn layui-btn-sm" name="addBook" data-type="reload"  lay-event="addBook">添加</button>
			<button class="layui-btn layui-btn-sm layui-btn-warm " name="updateBook" data-type="reload" lay-event="updateBook">修改</button>
			
			<button class="layui-btn layui-btn-sm layui-btn-normal" data-type="reload" id="importBook" lay-event="importBook">导入</button>
			<button class="layui-btn layui-btn-sm layui-btn-normal" data-type="reload" id="exportBook" lay-event="exportBook">导出</button>
		<%}%>

	</script>
	<!-- 操作按钮 -->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs" lay-event="details">详情</a>
		{{#  if(d.borrow_num >= 1){ }}
			<!-- 用户页面-->
			<%if("0".equals(request.getParameter("t"))){%>
				<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="lend">借阅</a>
			<%}%>
		{{#  } }}
		<!-- 管理员页面-->
		<%if("1".equals(request.getParameter("t"))){%>
			<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
		<%}%>
	</script>
	<script src="../static/js/bookList.js" charset="utf-8" type="text/javascript"></script>
</body>
</html>