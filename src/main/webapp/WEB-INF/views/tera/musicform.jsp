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
		<form:form action="?${_csrf.parameterName}=${_csrf.token }" method="post" enctype="multipart/form-data" modelAttribute="musicChVO">
			<div>
				<form:hidden path="author" /><form:hidden path="id" />
			</div>
			<div class="form-group">
				<b>title:</b> <form:input id="title" path="title" class="form-control"/>
			</div>
			<div>
				<c:if test="${not empty musicChVO.uploaded_pic }">
					<br/>
					<img src="${rootPath }/pics/${musicChVO.uploaded_pic }" width="200" height="200"/>
				</c:if>
			</div>
			<div class="form-group">
				<c:if test="${not empty musicChVO.uploaded_pic }">
					<label for="posterFormFile" class="form-label text-warning">
					The poster is already exists for this atricle. If you want to change the poster, select a poster file again and submit.
					</label>
				</c:if>
				<div><b>poster:</b> <input id="posterFileInput" type="file" name="poster" accept="image/*" class="form-control" id="posterFormFile"></div>
			</div>
			<br/>
			<div>
				<c:if test="${not empty musicChVO.uploaded_filename }">
					<br/>
					<video src="${rootPath }/stream/${musicChVO.id }" controls width="400" height="200"/>
				</c:if>
			</div>
			<div class="form-group">
				<c:if test="${not empty musicChVO.uploaded_filename }">
					<label for="videoFormFile" class="form-label text-warning">
					The video is already exists for this atricle. If you want to change the video, select a video file again and submit.
					</label>
				</c:if>
				<div><b>video:</b> <input type="file" id="videoFileInput" name="videos" accept="video/*,audio/*" class="form-control" id="videoFormFile"></div>
			</div>
			<div id="uploadGuideText" style="color: green ">
				<h3><b>Video is uploading... it will take 1~10 minutes... Please wait patiently</b></h3>
			</div>
			
			<div class="row float-left" style="margin: 20px;">
				<div style="margin-right: 20px;">
					<button id="btnSubmit" class="btn btn-success" type="button">submit</button>
				</div>
				<div style="margin-right: 20px;">
					<c:choose>
						<c:when test="${not empty musicChVO.id and musicChVO.id  gt 0 }">
							<a href="${rootPath }/getamusicch/${musicChVO.id}">
								<button id="btnCancle" type="button" class="btn btn-secondary">cancle</button>
							</a>
						</c:when>
						<c:otherwise>
							<a href="${rootPath }/musicchList">
								<button id="btnCancle" type="button" class="btn btn-secondary">cancle</button>
							</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
		
	</section>
</body>

<%@ include file="/WEB-INF/views/include/include-footer.jsp"%>

<script type="text/javascript">
var videoFolderSize=+'${videoFolderSize}'
var maxFolderSize=+'${maxFolderSize}'
$(function() {
	$("#uploadGuideText").hide()
	$(document).on("click","#btnSubmit",function(){
		var videofileName = $("#videoFileInput").val();
		var posterfilename= $("#posterFileInput").val();
		var mode='${mode}'
		
		if(!$("#title").val().trim()){
			alert("please enter title")
			return false
		}
		if(mode=='insert' && !videofileName){
			alert("please choose video file")
			return false
		}
		if(mode=='insert' && !posterfilename){
			alert("please choose poster file")
			return false
		}
		$("#btnSubmit").hide()
		$("#btnCancle").hide()
		$("#uploadGuideText").show()
		
		$("form").submit()
	})
	
	$('#videoFileInput').on('change', function() {
		var uploadFileSize=this.files[0].size
		uploadFileSize=+uploadFileSize
        if (videoFolderSize + uploadFileSize >= maxFolderSize) {
            alert("Sever's capacity is in over limit. Try to upload small size video. If you still have problem, checkout the Dashboard at main page");
            $("#videoFileInput").val(null);
        } 
    });
})
</script>
</html>