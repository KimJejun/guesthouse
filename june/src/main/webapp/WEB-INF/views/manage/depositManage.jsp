<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내 예약 목록</title>
<style type="text/css">
 .body_wrapper { width: 1100px;margin: 0 auto;margin-top: 80px}
</style>
<script type="text/javascript">
$(function(){
	$('#gnb_nav li').removeClass('active').eq(5).addClass('active');

	$('#dialog-form').modal({
		show: false
	}).on('show', function(){
	});
	
	/* $('#btn_update_complete, #btn_update_finish').on('click', function(){
	});
	
	
	$('#btn_update_finish').on('click', function(){
	}); */
});
</script>
</head>
<body>
<h4>입금 확인</h4>
<c:choose>
	<c:when test="${empty reservationList}">
		<div class="alert alert-info">
        	<button type="button" class="close" data-dismiss="alert">×</button>
           	<h4>입금확인할 내역이 없습니다.</h4>
           	<p>게스트 하우스 홍보를 더 해 주세요.</p>
		</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>예약자(입금인)</th>
					<th>방이름</th>
					<th>예약일</th>
					<th>예약 인원</th>
					<th>예약 상태</th>
					<th>입금확인완료</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reservationList}" var="reservation">
				<tr>
					<td></td>
					<td>(${reservation.depositName})</td>
					<td>${reservation.room.name}</td>
					<td>${reservation.reservedAt}</td>
					<td>${reservation.guestCount}</td>
					<td>${reservation.status.status}, ${reservation.status}</td>
					<td>
						<form action="/manage/confirm" method="post" id="form">
							<input type="hidden" name="nodeId" value="${reservation.nodeId}"> 
						<c:choose>
							<c:when test="${reservation.status == 'COMPLETE'}">
								<input type="hidden" name="status" value="FINISH"> 
								<button class="btn btn-small btn-success" id="btn_update_finish" type="submit">예약완료</button>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="status" value="COMPLETE"> 
								<button class="btn btn-small btn-info" id="btn_update_complete" type="submit">입금확인</button>
							</c:otherwise>
						</c:choose>
						</form>
					</td>
				</tr>
				</c:forEach>
				
			</tbody>
		</table>
	</c:otherwise>
</c:choose>

<div id="dialog-form" title="예약 하기" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">예약 입금 처리 하기</h3>
	  	</div>
	  	<div class="modal-body">
	  		<fieldset>
	  			<div style="" align="left">
				    <label for="name" id="lableRoomType">방종류</label>
				    <label for="email" id="labelGuestCount">인원</label>
	  			</div>
	  			<div align="left">
				    <label for="password">입금인</label>
				    <input type="text" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
	  			</div>
	  		</fieldset>
	  	</div>
	 	<div class="modal-footer">
	    	<button class="btn" data-dismiss="modal" aria-hidden="true">닫기</button>
	    	<button class="btn btn-primary" type="submit">예약</button>
	  	</div>
	</div>
</body>
</html>