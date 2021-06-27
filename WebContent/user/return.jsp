<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<link rel="stylesheet" href="../static/css/table.css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" type="text/javascript"></script>
	<script src="../static/js/time.js" type="text/javascript"></script>
	<title>我的借阅</title>
</head>
<body>
	<table class="layui-hide" id="cardTable" lay-filter="formFilter"></table>
	<script type="text/html" id="headBar">
 		搜索方式：
		<div class="layui-inline" style="width:120px">
	  	  <select id="isFuzzyQuery" name="isFuzzyQuery" lay-verify="required">
        	<option value=""></option>
			<option value="0">精确搜索</option>
        	<option value="1">模糊搜索</option>
		  </select>
	 	 </div>
		&nbsp;
		图书名称：
  		<div class="layui-inline">
    		<input class="layui-input" id="value" name="value" style="width:150px">
  		</div>
		&nbsp;
  		<button class="layui-btn" name="condition" data-type="reload"  lay-event="search">搜索</button>
	</script>
	
	<script type="text/javascript" src="../static/js/return.js" ></script>

</body>
</html>