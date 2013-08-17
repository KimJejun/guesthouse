<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내 예약 목록</title>
<style type="text/css">
 .body_wrapper { width: 1100px;margin: 0 auto;margin-top: 80px}
</style>
</head>
<body>
<c:choose>
	<c:when test="${empty reservations}">
		<div class="alert alert-info">
        	<button type="button" class="close" data-dismiss="alert">×</button>
           	<h4>예약한 기록이 없습니다!</h4>
           	<p>예약을 진행하시기 바랍니다.</p>
           	<p>
              <a class="btn btn-info" href="/reserve/index">예약하러가기</a>
            </p>
		</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>방이름</th>
					<th>예약일</th>
					<th>예약 인원</th>
					<th>예약 상태</th>
					<th>입금확인요청</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reservations}" var="reservation">
				<tr>
					<td></td>
					<td>${reservation.room.name}</td>
					<td>${reservation.reservedAt}</td>
					<td>${reservation.guestCount}</td>
					<td>${reservation.status.status}</td>
					<td>
						<form action="/guest/confirm" method="post" id="form">
							<input type="hidden" name="nodeId" value="${reservation.nodeId}"> 
						<c:choose>
							<c:when test="${reservation.status == 'RESERVATION'}">
								<input type="hidden" name="status" value="CONFIRM"> 
								<button class="btn btn-small btn-success" type="submit">입금완료요청</button>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="status" value="CANCLE"> 
								<button class="btn btn-small btn-danger" type="submit">예약취소</button>
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
</body>
</html>