<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp" %>
</head>
<style>
section.email_body{
width:80%;
margin:120px auto;
display: flex;
flex-flow: column;
justify-content: center;
align-items: center;
}
span#secret{
display: none;
}
</style>
<script type="text/javascript">
$(function() {
	$(document).on("click","#btn_email_ok",function(){
		let secret_key=$("span#secret").text()
		let secret_value=$("input#email_ok").val()
		if(secret_value==""){
			alert("인증코드를 입력한후 인증버튼을 클릭하세요")
			$("input#email_ok").focus()
			return false
		}
		$.ajax({
			url:"${rootPath}/member/findId_email_token_check",
			method:"POST",
			data:{
				"${_csrf.parameterName}" : "${_csrf.token}",
				email:$("#email").val(),
				secret_key:secret_key,
				secret_value:secret_value
			},
			success:function(result){
			/* 6월4일 버그 수정 if 랑 else 추가
			이거 없으면 인증코드 잘못 입력해도 FAIL 리턴되긴 하는데 비번 변경 페이지로 넘어가짐 */
				if(result=="OK"){
				alert(result)
				document.location.replace("${rootPath}/member/re_join")
				} else {
				alert(result+"\n"+"인증코드를 잘못 입력하였습니다")
				}
			},
			error:function(){
				alert("서버통신오류")
			}
		})
	})	
})
</script>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp" %>
<%@ include file="/WEB-INF/views/include/include-mypage.jsp"%>
<section class="email_body">
<h2>Email 인증</h2>
<div>ID찾기/비밀번호를 변경하려면 E-mail 인증을 완료해야 합니다</div>
<p>
<form:form action="${rootPath}/member/findID" method="post" modelAttribute="memberVO" class="form-group">
	<div class="form-group">
	<form:input path="email" type="email" placeholder="email"/>
	</div>
	<c:choose>
		<c:when test="${JOIN=='EMAIL_OK'}">
		<button class="btn btn-outline-warning">인증 email 다시 보내기</button>
		<br/>
		<br/>
		<p>E-mail을 열어서 인증코드를 확인한 후 아래 입력란에 입력 후 인증 버튼을 클릭하세요</p>
		<div class="form-group">
		<span id="secret">${My_Email_Secret}</span>
		<input id="email_ok" class="form-control" placeholder="인증코드 입력">
		<button type="button" id="btn_email_ok" class="btn btn-outline-primary">인증하기</button>
		</div>
		</c:when>
		<c:otherwise>
		<button class="btn btn-outline-success">인증 email 보내기</button>
		</c:otherwise>
	</c:choose>
</form:form>
</p>
</section>
</body>
</html>