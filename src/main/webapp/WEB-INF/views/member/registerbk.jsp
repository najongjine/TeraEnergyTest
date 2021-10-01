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
<script type="text/javascript">
$(function() {
	$(document).on("click","#btn-regi",function(){
		let pass1=$("#pass1").text()
		let pass2=$("#pass2").text()
		if(pass1!=pass2){
			alert("비밀번호 재입력이 서로 틀립니다")
			return false
		}
		$("form").submit()
	})
})
</script>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
<section>
<form:form modelAttribute="memberVO" method="post">
<p>
<b>user name:</b>
<form:input path="u_name"/>
<form:errors path="u_name" />
</p>
<p>
<b>password:</b>
<form:input id="pass1" path="u_password" type="password"/>
<form:errors path="u_password" />
</p>
<p>
<b>re_pasword:</b>
<form:input id="pass2" path="u_repassword" type="password"/>
<form:errors path="u_repassword" />
</p>
<button id="btn-regi" type="button">submit</button>
</form:form>
</section>
</body>
</html>