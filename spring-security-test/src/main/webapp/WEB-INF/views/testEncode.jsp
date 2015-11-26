<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/testEncode" method="post">
	<input type="text" name="targetStr" value="${encodeStr }"/>
	<button type="submit">암호화</button>
</form>
</body>
</html>