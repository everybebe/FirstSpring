<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" />
<title>자유게시판 상세보기</title>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h3 class="display-3">자유게시판 상세보기 / 수정 / 삭제</h3>
		</div>
	</div>

	<div class="container">
		<form name="newUpdate" action="" class="form-horizontal" method="post">
			<div class="form-group row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					 ${free.boTitle }
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label" >내용</label>
				<div class="col-sm-8" style="word-break: break-all;">
			    	${free.boContent }
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10 ">
					<p>
						<button type="button" class="btn btn-primary" id="delBtn">삭제</button>
						<button type="button" class="btn btn-info"  id="updateBtn">수정</button>
						<button type="button" class="btn btn-danger"  id="listBtn">목록</button>
					</p>
				</div>
			</div>
		</form>
		<form action="/free/update.do" method="get" id="nFrm">
			<input type="hidden" name="boNo" value="${free.boNo }" />
		</form>
		<hr>
	</div>
	
	
	<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/dist/js/adminlte.min.js"></script>
	
</body>



<script type="text/javascript">
$(function(){
	var delBtn = $("#delBtn"); 
	var updateBtn = $("#updateBtn");
	var listBtn = $("#listBtn");
	var nFrm = $("#nFrm");
	
	delBtn.on("click", function(){
		if(confirm("정말 삭제하실건가요?")) {
			nFrm.attr("method","post");
			nFrm.attr("action", "/free/delete.do");
			nFrm.submit();
		} else {
			nFrm.reset();
		}
	})

	updateBtn.on("click", function(){
		nFrm.submit();
	})
	
	listBtn.on("click", function(){
		location.href = "/free/list.do";
	})
})

</script>
	
</html>


