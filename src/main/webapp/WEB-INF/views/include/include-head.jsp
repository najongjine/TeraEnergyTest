<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Tera MiniTube</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">
<!-- include summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote.min.js"></script>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	width: 90%;
	margin: 0 auto;
}

.container-fluid {
	margin-top: 0px;
	border: 1px solid #eee;
}
</style>
<script>
	$(document).ready(function() {
		
		$('.summernote').summernote({
			disableDragAndDrop : false,
			callbacks:{
				onImageUpload:function(files,editor,isEditted){
					for(let i=files.length-1;i>=0;i--){
						//파일을 한개씩 업로드할 함수
						upFile(files[i],this) // this==editor(summernote 자체)
					}
					
				}
			}
		});//end summer
		//ajax를 사용해서 파일을 업로드 수행하고
		// 실제 저장된 파일 이름을 받아서 summernote에 기록된 내용중 img src="" 을 변경
		function upFile(file,editor) {
			var formData=new FormData()
			//upFile 변수에 file정보를 담아서 보내기 위한 준비
			/*
			editor.insertImage:= summernote 의 내장 함수를 callback 형태로
			호출해서 현재 summernote box에 표시하고자
			이미지의 src부분을 url값으로 대치
			img src="data:base64..." -> img src="UUIDfile.jpg" 형태로 변경
			*/
			formData.append('upFile',file) // 'upFile'은 컨트롤러에서 받을 파라메터 이름
			$.ajax({
				url:"${rootPath}/image_up",
				type:"POST",
				data:formData,
				contentType:false,
				processData:false,
				enctype:"multipart/form-data",
				beforeSend:function(ax){
					ax.setRequestHeader("${_csrf.headerName}","${_csrf.token}")
				},
				success:function(result){
					result="${rootPath}/files/"+result
					$(editor).summernote('editor.insertImage',result)
				},
				error:function(){
					alert("서버통신오류")
				}
			})
		}//end ajax file upload
		
	});
</script>