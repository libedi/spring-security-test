<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인화면</title>
</head>
<body>
	<form id="loginfrm" name="loginfrm" method="post" action="/j_spring_security_check">
		<table>
			<tr>
				<td>ID</td>
				<td>
					<input type="text" id="loginid" name="loginid" value="${loginid}"/>
				</td>
			</tr>
			<tr>
				<td>PASSWORD</td>
				<td>
					<input type="password" id="loginpw" name="loginpw" value="${loginpw}" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" id="loginbtn" value="로그인"/>
				</td>
			</tr>
			<c:if test="${not empty securityExceptionMsg}">
			<tr>
				<td colspan="2">
					<p><font color="red">로그인 실패, 한번더해봐</font></p>
<%-- 					<p><font color="red">이유 : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</font></p> --%>
<%-- 					<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/> --%>
					<%-- 
						Spring Security 작업중 예외발생시, 예외에 대한 객체를 만든 뒤 세션에 저장.
						저장된 key 가 SPRING_SECURITY_LAST_EXCEPTION
					--%>
					<p><font color="red">${securityExceptionMsg}</font></p>
				</td>
			</tr>
			</c:if>
		</table>
		<input type="hidden" name="loginRedirect" value="${loginRedirect }"/>
	</form>
</body>
<script type="text/javascript">
	$("#loginbtn").click(function(){
		if($("#loginid").val() == ''){
			alert("아이디 입력하세요.");
			$("#longid").focus();
		} else if($("#loginpw").val() == ''){
			alert("비밀번호 입력하세요.");
			$("#longpw").focus();
		} else {
			$("#loginfrm").attr("action", "<c:url value='/j_spring_security_check'/>");
			$("#loginfrm").submit();
		}
	});
</script>
</html>