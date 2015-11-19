<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인화면</title>
</head>
<body>
	<sec:authorize access="isAnonymous()">
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
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
	${name}님 반갑습니다.
	
	<a href="/j_spring_security_logout">로그아웃</a>
	</sec:authorize>
	
	<ul>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li>관리자 화면</li>
		</sec:authorize>
		
		<sec:authorize access="permitAll">
		<li>비회원게시판</li>
		</sec:authorize>
		
		<sec:authorize access="isAuthenticated()">
		<li>준회원게시판</li>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('ROLE_MEMBER2','ROLE_ADMIN')">
		<li>정회원게시판</li>
		</sec:authorize>
	</ul>
</body>
</html>