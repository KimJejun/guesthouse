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
	$('#btn_add_room').click(function(){
		$('#dialog-form').modal('show');
	});
	
	$('#roomForm').submit(function(){
		var params = $(this).serialize();
		var url = $(this).attr('action');
		console.log(url, params);
		$.getJSON(url, params, function(data) {
			var status = data.status;
			var errList = data.errorMessageList;
			console.log(status, errList);
			
			if (status === 'SUCCESS') {
				location.replace('/manage/roomList');
			} else if (status === 'EXIST_ROOM') {
				$('#exist_alert').show();
				return false;
			}
			
			$.each(errList, function(){
				var errField= this.field;
				console.log(errField);
				var $input = $("input[name='" + errField + "']");
				$input.parent().parent().addClass('error');
				$input.next().text(this.defaultMessage);
			});
		});
		
		return false;
	});
});
</script>
</head>
<body>
<h4>전체 객실 목록</h4>
<p>
  	<button class="btn btn-primary pull-right" type="button" id="btn_add_room">객실 추가</button>
</p>
<c:choose>
	<c:when test="${empty roomList}">
		<div class="alert alert-info">
        	<button type="button" class="close" data-dismiss="alert">×</button>
           	<h4>등록된 방이 없습니다.</h4>
           	<p>등록해주세요.</p>
		</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>이름</th>
					<th>수용인원</th>
					<th>색상</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${roomList}" var="room">
				<tr>
					<td></td>
					<td>${room.name}</td>
					<td>${room.capacity}</td>
					<td>${room.color}</td>
				</tr>
				</c:forEach>
				
			</tbody>
		</table>
	</c:otherwise>
</c:choose>

<div id="dialog-form" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		
		<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">신규 객실 추가하기</h3>
	  	</div>
    	<form:form id="roomForm" cssClass="form-horizontal" action="/manage/room/regist" commandName="room">
		  	<div class="modal-body">
		  	
		  		<div class="alert alert-error hide" id="exist_alert">
					이미 등록된 객실입니다. 객실 이름 및 기타 정보를 확인하세요.
				</div>
				
		  		<div class="control-group">
				    <label class="control-label" for="inputName">객실 이름</label>
				    <div class="controls">
			      		<input type="text" name="name" id="inputName" placeholder="객실 이름">
			      		<span class="help-inline"></span>
			    	</div>
	  			</div>
		  		<div class="control-group">
					<label class="control-label" for="inputCapaticy">수용인원</label>
				  	<div class="controls">
				    	<select class="span1" name="capacity">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>	
						</select>
				  	</div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="inputColor">객실 색상</label>
				    <div class="controls">
			      		<input type="text" name="color" id="inputColr" placeholder="객실 색상">
			      		<span class="help-inline"></span>
			    	</div>
	  			</div>
		  	</div>
	 	<div class="modal-footer">
	    	<button class="btn btn-success" type="submit">저장</button>
	    	<button class="btn" data-dismiss="modal" aria-hidden="true">닫기</button>
	  	</div>
	  	</form:form>
</div>
</body>

</html>
