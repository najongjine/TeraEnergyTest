<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
<section style="margin:5%;">
	<h1>Login</h1>
	<article style="margin:5%;">
		<form:form action="${rootPath }/login" modelAttribute="memberVO" method="post">
		<p>
		<b>user name:</b>
		<form:input path="username"/>
		<form:errors path="username" />
		</p>
		<p>
		<b>password:</b>
		<form:input id="pass1" path="password" type="password"/>
		<form:errors path="password" />
		</p>
		<button id="btn-login" class="btn btn-success">submit</button>
		<a id="regiLink" href="${rootPath }/member/register">Sign Up</a>
		</form:form>
	</article>
</section>

<%@ include file="/WEB-INF/views/include/include-footer.jsp"%>
</body>
</html>