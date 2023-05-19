<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" />

<title>자유게시판 목록</title>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">자유 게시판 목록</h1>
		</div>
	</div>
	<div class="container">
				<div>
				<div class="text-right">
					<span class="">전체 ${pagingVO.totalRecord } 건 이상</span>
				</div>
			</div>
			<div style="padding-top: 50px">
						<div class="col-md-12">
			<div class="card">
			<div class="card-header">
			<div class="card-tools">
				<form class="input-group input-group-sm" method="post" id="searchForm" style="width: 440px;">
					<input type="hidden" name="page" id="page" />
					<select class="form-control" name="searchType">
						<option value="title" <c:if test="${searchType == 'title' }"><c:out value="selected"/></c:if>>제목</option>
						<option value="writer" <c:if test="${searchType == 'writer' }"><c:out value="selected"/></c:if>>작성자</option>
						<option value="titleWriter" <c:if test="${searchType == 'titleWriter' }"><c:out value="selected"/></c:if>>제목+작성자</option>
					</select>
					<input type="text" name="searchWord" class="form-control float-right" value="${searchWord }" placeholder="Search">
					<div class="input-group-append">
						<button type="submit" class="btn btn-default">
							<i class="fas fa-search"></i>검색
						</button>
					</div>
				</form>
			</div>	
			</div>	
			</div>
			</div>	
			
				<table class="table">
					<thead class="table-dark">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성일</th>
							<th>작성자</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody>
							<c:set value="${pagingVO.dataList }" var="freeList" />
						<c:choose>
							<c:when test="${empty freeList }">
								<tr>
									<td colspan="5">조회하신 게시글이 존재하지 않습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${freeList }" var="free">
									<tr id="${free.boNo }">
										<td>${free.boNo }</td>
										<td>
										<a href="/free/detail.do?boNo=${free.boNo }">
											${free.boTitle } 
											</a>
										</td>
										<td>${free.boWriter }</td>
										<td>${free.boDate }</td>
										<td>${free.boHit }</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>

					</tbody>
				</table>
			</div>
			<div align="left">
				<button type="button" class="btn btn-primary" id="newBtn">&laquo;글쓰기</button>
			</div>
				<div class="card-footer clearfix" id="pagingArea">
			${pagingVO.pagingHTML }
			</div>
		<hr>
	</div>
		<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>
	
</body>

<script type="text/javascript">
$(function(){
	var newBtn = $("#newBtn");
	var searchForm = $("#searchForm");
	var pagingArea = $("#pagingArea");
	
	pagingArea.on("click", "a", function(event){
		event.preventDefault();
		var pageNo = $(this).data("page");
		searchForm.find("#page").val(pageNo);
		searchForm.submit();
	})
	
	newBtn.on("click", function(){
		location.href = "/free/form.do";
	})
	
	
	$("tr").on("click", function(){
		var tnum = $(this).attr('id')		
		location.href = "/free/detail.do?boNo=" + tnum;
	})
}) 
</script>
</html>





