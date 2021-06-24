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
	<title>导入图书</title>
</head>
<script type="text/javascript">
	$(function(){
		var projectPath = getProjectPath();
		var user = getUser();
		document.getElementById("template").href = projectPath+'/excel?entity=book&method=template';
	})
</script>
<body>
	<div id="add_div" style="margin: 8%; ">
		<form action="ExcelController" method="post" enctype="multipart/form-data"
		 class="layui-form layui-form-pane" id="add_form">
		 <div class="layui-form-item">
		     <div class="layui-input-block">
		     	<input type="file" name="filePath" class="file-btn" /> <input type="submit" value="导入" />
		     </div>
		  </div>
		 <div class="layui-form-item">
		  	<center><a href="" id="template" style="color:#2A00FF">下载导入模板</a></center>
		 </div>
		</form>
	</div>
</body>
</html>