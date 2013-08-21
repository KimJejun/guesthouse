<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
<style type="text/css">
 .body_wrapper { width: 1100px;margin: 0 auto;margin-top: 80px}
</style>
<script type="text/javascript">
$(function(){
	$('#gnb_nav li').removeClass('active').eq(6).addClass('active');
});
</script>
</head>
<body>
<h4>전체 회원 목록</h4>
<c:choose>
	<c:when test="${empty guestList}">
		<div class="alert alert-info">
        	<button type="button" class="close" data-dismiss="alert">×</button>
           	<h4>등록된 회원이 없습니다.</h4>
           	<p>게스트 하우스 홍보를 더 해 주세요.</p>
		</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>이름</th>
					<th>ID</th>
					<th>email</th>
					<th>연락처</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${guestList}" var="guest">
				<tr>
					<td></td>
					<td>${guest.name}</td>
					<td>${guest.id}</td>
					<td>${guest.email}</td>
					<td>${guest.contact}</td>
					<td>
						
					</td>
				</tr>
				</c:forEach>
				
			</tbody>
		</table>
	</c:otherwise>
</c:choose>

</body>
</html>