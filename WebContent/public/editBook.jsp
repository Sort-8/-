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
	<script src="../static/js/upload.js" type="text/javascript"></script>
	<title>添加图书</title>
</head>
<body>
	<div id="add_div" style="margin: 8%; ">
		<form class="layui-form layui-form-pane" method="post" id="add_form">
			<input type="text" name="book_id" value="0" class="layui-hide">
			<input type="text" name="lend_stu" value="1" class="layui-hide">
			<input type="text" name="url" id="url" value="" class="layui-hide">
			
			<!-- 图书名称 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">图书名称</label>
			    <div class="layui-input-block">
			      <input type="text" name="name" id="name" placeholder="请输入图书名称" class="layui-input">
			    </div>
		  	</div>
			
			<!-- 上传图片 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">图片</label>
		    	 <!--上传图片 -->
			    <div class="layui-input-block  file-box">
			      <input type="file" id="fileSelector" onchange="changeImg(this)" name="filename" class="file-btn">
			      <input id="submitBtn" type="button" onclick="onloadFile()" class="layui-btn layui-btn-sm"  value="上传文件">
			      <button type="reset" class="layui-btn layui-btn-primary layui-btn-sm" onclick="cancelOnload()">取消上传</button>
			    </div>
			    <div>
			    	<img src="" id="preview_img" alt="">
			    </div>
		  	</div>
			
			<!-- IBSN号 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">IBSN号</label>
			    <div class="layui-input-block">
			      <input type="text" name="code" id="code" placeholder="请输入IBSN号" class="layui-input">
			    </div>
		  	</div>
			
			<!-- 图书类型 -->
			<div class="layui-form-item">
				<label class="layui-form-label">图书类型</label>
				<div class="layui-input-block layui-form" lay-filter="selFilter">
					<select name="type_id" id="sel">
				      <option value="">请选择</option>
				    </select>
				</div>
			</div>
			
			<!-- 作者 -->
		    <div class="layui-form-item">
		    <label class="layui-form-label">作者</label>
			    <div class="layui-input-block">
			      <input type="text" name="author" id="author" placeholder="请输入作者" class="layui-input">
			    </div>
		    </div>
		    
		    <!-- 出版社 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">出版社</label>
			    <div class="layui-input-block">
			      <input type="text" name="press" id="press" placeholder="请输入出版社" class="layui-input">
			    </div>
		  	</div>
		    
		    <!-- 数量 -->
		    <div class="layui-form-item">
		    	<label class="layui-form-label">数量</label>
			    <div class="layui-input-block">
			      <input type="text" name="number" id="number" placeholder="请输入数量" class="layui-input">
			    </div>
		  	</div>
		    
		    <!-- 提交重置 -->
			<div class="layui-form-item">
			    <div class="layui-input-block">
			      <button type="button" class="layui-btn" lay-filter="bookForm">立即提交</button>
			      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
		  	</div>
		</form>
	</div>
</body>
</html>