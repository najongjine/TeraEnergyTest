<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath }"/>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp" %>
</head>
<style>
input{
margin-top:2vh;
margin-bottom: 2vh;
}
button{
margin-left: 1vw;
margin-right:1vw;
margin-top:2vh;
margin-bottom: 2vh;
}
.message{
color: green;
font-weight: bold;
font-size: 12;
}
</style>
<script type="text/javascript">
var bUsedUsername=false
$(document).ready(function() {
	if('${serverMsg}'){
		alert('${serverMsg}')
	}
	$(document).on("click","#btn-join",function(){
		let username=$("#username")
		let password=$("#password")
		let re_password=$("#re_password")
		
		if(!username.val().trim()){
			alert("Please enter your username")
			username.focus()
			return false
		}
		if(!password.val().trim()){
			alert("Please enter your password")
			password.focus()
			return false
		}
		if(password.val().trim().length<4){
			alert("a password must be longer than 3 chars")
			password.focus()
			return false
		}
		if(re_password.val()==""){
			alert("Please enter your re_password")
			re_password.focus()
			return false
		}
		if(password.val()!=re_password.val()){
			alert("password and repassword doesn't match")
			password.focus()
			return false
		}
		if(bUsedUsername){
			alert("The username is already in use")
			return false
		}
		
		$("form").submit()
	})
	$(document).on("blur","#username",function(){//입력박스에서 포커스가 벗어났을때
		let username=$(this).val()
		if(username==""){
			$("#m_username").text("Please enter your username")
			$("#m_username").focus()
			return false
		}
		$.ajax({
			url:"${rootPath}/member/checkusername_alrdyuse_json",
			method:"POST",
			data:{'username':username},
			beforeSend:function(ax){
				ax.setRequestHeader("${_csrf.headerName}","${_csrf.token}")
			},
			success:function(data){
				if(data && data.success && data.bExist){
					$("#m_username").text("The username is already in use")
					$("#m_username").css("color","red")
					$("#m_username").focus()
					bUsedUsername=true
					return false
				} else if(data && data.success && !data.bExist){
					$("#m_username").text("Ok to use")
					$("#m_username").css("color","green")
					bUsedUsername=false
					return false
				}
			},
			error:function(){
				$("#m_username").text("server error")
				$("#m_username").css({ 'color': 'red', 'font-size': 12 });
				return false
			}
		})
	})
})
</script>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp" %>

<form:form modelAttribute="memberVO" action="${rootPath }/member/register" method="post" class="form-group container">
<h2 class="jumbotron">Register</h2>
<form:input path="username" placeholder="User ID" class="form-control"/>
<p class="message h3" id="m_username"></p>

<form:input type="password" path="password" placeholder="password" class="form-control"/>
<input type="password" id="re_password" name="re_password" placeholder="re_password" class="form-control"/>
<button class="btn btn-outline-primary" id="btn-join" type="button">Register</button>

</form:form>

<%@ include file="/WEB-INF/views/include/include-footer.jsp"%>
</body>
</html>