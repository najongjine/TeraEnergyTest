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
		document.location.href='${rootPath}/mylist?pageIndex='+$(this).data("page");
		return false;
	})
})
</script>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<br/><br/>
	<div class="container">
	  <div class="row">
	    <div class="col-12 col-sm-8 col-lg-5">
	      <h6 class="text-muted">My Video List</h6> 
	      	<c:forEach items="${myList }" var="vo" varStatus="i">
	      		<div class="card" style="width: 18rem;">
	      		  <a href="${rootPath }/mych/${vo.id}">
				  	<img class="card-img-top" src="${rootPath }/pics/${vo.uploaded_pic }" alt="Card image cap">
				  </a>
				  <div class="card-body">
				    <h5 class="card-title">${vo.title }</h5>
				    <p class="card-text">order : ${vo.iorder }</p>
				  </div>
				  <div>
				  	<hr/>
				  </div>
				</div>
	    	</c:forEach>
	    </div>
	  </div>
	</div>

	<hr/>
	<br/>
	<br/>
	<br/>
	<%@ include file="/WEB-INF/views/include/cmtPagi.jsp"%>
</body>

<%@ include file="/WEB-INF/views/include/include-footer.jsp"%>

</html>