<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<title>Detail</title>
</head>
<script type="text/javascript" >
var video = document.getElementById("myvideo");
video.onended = function() {
    document.
}; 
var checkIsAddedToMylist=function(ch_id){
	$.ajax({
        url:'/check_is_added_to_mylist_json/'+ch_id,
        success:function(data){
        	var html=""
        	if(data.bAdded == false){
          		html+=`<a href="#!" id="addSub" data-type="add">+ Add</a>`
          	}else if(data.bAdded == true){
          		html+=`<a href="#!" id="addSub" data-type="remove">- Remove</a>`	
          	}
        	$('#add_sub_to_mylist').html(html);
        	$("#addSub").show()
        	return false;
        }
	,error:function(error){
	}
    })
}
var add_sub_mylist_json=function(type,ch_id){
	$.ajax({
        url:'/add_sub_mylist_json?ch_id='+ch_id+'&type='+type,
        success:function(data){
        	checkIsAddedToMylist('${myCh.music_ch_id }')
        }
    })
}
$(function(){
	checkIsAddedToMylist('${myCh.music_ch_id }');
    
	$(document).on("click","#addSub",function(){
		$("#addSub").hide()
		var type=$(this).data("type")
		add_sub_mylist_json(type,'${myCh.music_ch_id}')
		
	})
	$("#deletevideo").on("click",function(){
		if(confirm("you sure want to delete?")){
			document.location.href="${rootPath }/deletemusicch/"+"${myCh.music_ch_id}"
		}
	})
	
	
  })
</script>


<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	
	<section>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.username" var="username"/>
	</sec:authorize>
		
		<div class="titleDiv">
			<p>${myCh.title}</p>
		</div>
		
		<div >
			<div>
				<video id="myvideo" src="${rootPath }/stream/${myCh.music_ch_id }" width="60%" controls autoplay/>
			</div>
			<div class="InfoBox">
				<sec:authorize access="isAuthenticated()">
					<div class="row float-left" style="margin: 20px;">
						<div style="margin-right: 20px;">
							<a href="${rootPath }/myprevch/${myCh.iorder}">
								<button class="btn btn-success"> &lt; (Prev)</button>
							</a>
						</div>
						<div style="margin-right: 20px;">
							<a href="${rootPath }/mynextch/${myCh.iorder}">
								<button type="button" class="btn btn-secondary"> &gt; (Next)</button>
							</a>
						</div>
					</div>
					<div id="add_sub_to_mylist" style="text-align: right; margin-right:40%">
					</div>
				</sec:authorize>
				<div class="Info">order: ${myCh.iorder}</div>
				<div class="Info">author: ${myCh.author}</div>
				<div class="Info">created_at: ${myCh.created_at}</div>
			</div>
			
		</div>
		
	</section>
	
	
	<div>
		<%@ include file="/WEB-INF/views/include/include-footer.jsp"%>
	</div>
</body>

<script type="text/javascript" >

var checkIsAddedToMylist=function(ch_id){
	$.ajax({
        url:'/check_is_added_to_mylist_json/'+ch_id,
        success:function(data){
        	var html=""
        	if(data.bAdded == false){
          		html+=`<a href="#!" id="addSub" data-type="add">+ Add</a>`
          	}else if(data.bAdded == true){
          		html+=`<a href="#!" id="addSub" data-type="remove">- Remove</a>`	
          	}
        	$('#add_sub_to_mylist').html(html);
        	$("#addSub").show()
        	return false;
        }
	,error:function(error){
	}
    })
}
var add_sub_mylist_json=function(type,ch_id){
	$.ajax({
        url:'/add_sub_mylist_json?ch_id='+ch_id+'&type='+type,
        success:function(data){
        	checkIsAddedToMylist('${myCh.music_ch_id }')
        }
    })
}
$(function(){
	checkIsAddedToMylist('${myCh.music_ch_id }');
    
	$(document).on("click","#addSub",function(){
		$("#addSub").hide()
		var type=$(this).data("type")
		add_sub_mylist_json(type,'${myCh.music_ch_id}')
		
	})
	$("#deletevideo").on("click",function(){
		if(confirm("you sure want to delete?")){
			document.location.href="${rootPath }/deletemusicch/"+"${myCh.music_ch_id}"
		}
	})
	var video = document.getElementById("myvideo");
	video.onended = function() {
		document.location.href="${rootPath }/mynextch/"+"${myCh.iorder}"
	}; 
  })
</script>
</html>