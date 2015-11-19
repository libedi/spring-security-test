<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인화면</title>
</head>
<body>
	<form id="loginfrm" name="loginfrm" method="post" action="/j_spring_security_check">
		<table>
			<tr>
				<td>ID</td>
				<td>
					<input type="text" id="loginid" name="loginid" />
				</td>
			</tr>
			<tr>
				<td>PASSWORD</td>
				<td>
					<input type="password" id="loginpw" name="loginpw" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" id="loginbtn" value="로그인"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>