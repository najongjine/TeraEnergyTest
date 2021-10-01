<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<title>insert</title>

</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<section>
		<form:form action="?${_csrf.parameterName}=${_csrf.token }" method="post" enctype="multipart/form-data" modelAttribute="dummyVO">
			<p>
				<b>title:</b> <form:input path="strDummy" />
			</p>
			<p>
				<b>upload1:</b> <input type="file" multiple="multiple" name="uploaded_files">
			</p>
			<p>
				<b>upload2:</b> <input type="file" multiple="multiple" name="uploaded_files2">
			</p>
			<button>submit</button>
		</form:form>
		
	</section>
</body>

<script type="text/javascript">
/*$(function() {
	let text="${userVO.uf_text }"+""
	alert(text)
	$("#uf_text").val(text)
})*/
</script>
</html>