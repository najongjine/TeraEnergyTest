<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">

</head>
<body>
	<br/>
	<article class="Page navigation example">
		<ul class="pagination justify-content-center">
			<li class="page-item" data-page="1">
				<a class="page-link" href="#">[First]</a>
			</li>
				
			<li class="page-item" data-page="${PAGE.nextPageNo}">
				<a class="page-link" href="#">&lt;</a>
			</li> 

			<c:forEach begin="${PAGE.startPageNo }" end="${PAGE.endPageNo }" var="page">
				<li class="page-item ${page eq PAGE.currentPageNo ? 'active' : '' }" data-page="${page }">
					<a class="page-link" href="#">
						${page }
					</a>
				</li> 
			</c:forEach>
			
			<li class="page-item" data-page="${PAGE.nextPageNo}" class="page-link">
				<a class="page-link" href="#">&gt;</a>
			</li>
			<li class="page-item" data-page="${PAGE.finalPageNo}" class="page-link">
				<a class="page-link" href="#">[Last]</a>
			</li>
		</ul>
	</article>
</body>
</html>