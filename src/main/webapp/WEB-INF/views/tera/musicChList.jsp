<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
</head>
<script type="text/javascript">
$(function() {
	$(document).on("click",".page-item",function(){
		var pageIndex=$(this).data("page")
		document.location.href='${rootPath}/musicchList?pageIndex='+pageIndex;
		return false;
	})
})
</script>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<br/><br/>
	<section class="apiData row">
		<c:forEach items="${musicChList }" var="vo" varStatus="i">
			<div class="container col-sm-3">
				<h2>${vo.title}</h2>
				<div class="card" style="width: 200px">
					<img class="card-img-top" src="${rootPath }/pics/${vo.uploaded_pic }" alt="Card image"
						style="width: 100%">
					<div class="card-body">
						<h4 class="card-title">${vo.author }</h4>
						<a href="${rootPath }/getamusicch/${vo.id }" class="btn btn-primary">Detail</a>
					</div>
				</div>
				<br>
			</div>
		</c:forEach>
	</section>
	<hr/>
	<br/>
	<section >
		<sec:authorize access="isAuthenticated()">
		<div class="float-right">
			<a href="${rootPath }/insertmusicform"><button class="btn btn-success btn-lg">insert</button></a>
		</div>
		</sec:authorize>
	</section>
	<br/>
	<br/>
	<%@ include file="/WEB-INF/views/include/cmtPagi.jsp"%>
</body>

<%@ include file="/WEB-INF/views/include/include-footer.jsp"%>

</html>