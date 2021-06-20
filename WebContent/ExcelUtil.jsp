<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	选择导入的文件
	<form action="excel" method="post"
		enctype="multipart/form-data">
		<input type="file" name="filePath" /> <input type="submit" value="导入" />
	</form>
</body>
</html>