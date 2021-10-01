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
var isAddedResult=null
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
        	checkIsAddedToMylist('${musicChVO.id }')
        }
    })
}
$(function(){
	checkIsAddedToMylist('${musicChVO.id }');
    
	$(document).on("click","#addSub",function(){
		$("#addSub").hide()
		var type=$(this).data("type")
		add_sub_mylist_json(type,'${musicChVO.id}')
		
	})
	$("#deletevideo").on("click",function(){
		if(confirm("you sure want to delete?")){
			document.location.href="${rootPath }/deletemusicch/"+"${musicChVO.id}"
		}
	})
	
	
  })
</script>



<style>

.titleDiv{
	font-weight: bold;
	font-size: x-large;
	margin-top: 5%;
	border-bottom:2px solid darkblue;
}

.imgAndInfoBoxDiv{
	display: flex;
	padding: 10px;
	margin-top: 5%;
}

.InfoBox{

	    font-size: large;
	    display:grid;
	    padding: 10px;
}

.descriptionDiv{
	margin-top: 5%;
	font-size: large;
	font-weight: bold;
}


div.Info:after{
         content: "";
        display: block;
        width: 60px;
        border-bottom: 2px solid darkblue;

}


#daumMapButton{
	margin-top:5%;
}


#showDaumMap{
	font-weight: bold;
    font-size: x-large;
    background-color: darkblue;
    color: yellow;
    border: 5px solid darkblue;
    border-radius: 10px;
}

.mapAlertMSG{

margin-top:5%;
color: darkred;

}
</style>

<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	
	<section>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.username" var="username"/>
	</sec:authorize>
		
		<div class="titleDiv">
			<p>${musicChVO.title}</p>
		</div>
		
		<div >
			<div>
				<video src="${rootPath }/stream/${musicChVO.id }" width="60%" controls/>
			</div>
			<div class="InfoBox">
				<sec:authorize access="isAuthenticated()">
					<div id="add_sub_to_mylist" style="text-align: right; margin-right:40%">
					</div>
				</sec:authorize>
				<div class="Info">author: ${musicChVO.author}</div>
				<div class="Info">created_at: ${musicChVO.created_at}</div>
			</div>
			
		</div>
		
	</section>
	
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.username" var="username"/>
		<c:if test="${username eq musicChVO.author }">
			<div class="row float-right" style="margin-right: 20px;">
				<div style="margin-right: 20px;">
					<a href="${rootPath }/updatemusicform/${musicChVO.id }"><button class="btn btn-info">Update</button></a>
				</div>
				<div style="margin-right: 20px;">
					<button type="button" id="deletevideo" class="btn btn-danger">Delete</button>
				</div>
			</div>
		</c:if>
	</sec:authorize>
	
	<div>
		<%@ include file="/WEB-INF/views/include/include-footer.jsp"%>
	</div>
</body>


</html>