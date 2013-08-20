<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false" %>
<html>
<head>
<link href='/resources/js/plugin/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='/resources/js/plugin/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<style>
    .body_wrapper {
    	margin-top : 80px;
    }

	#calendar { width: 900px;margin: 0 auto; }
		
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contain { width: 350px; margin: 20px 0; }
    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
    #dialog-form {font-size: 0.8em}


	#wrap { width: 1100px;margin: 0 auto; }
		

	#calendar {	width: 750px;}
	.room1 {background-color: red}
	.room2 {background-color: blue}
	.room3 {background-color: green}
</style>
<script src='/resources/js/plugin/fullcalendar/fullcalendar.min.js' type="text/javascript"></script>
<script src='/resources/js/plugin/noty/jquery.noty.js' type="text/javascript"></script>
<script src='/resources/js/plugin/noty/center.js' type="text/javascript"></script>
<script src='/resources/js/plugin/noty/default.js' type="text/javascript"></script>
<script>

$(document).ready(function() {
	
	$('#gnb_nav li').removeClass('active').eq(1).addClass('active');
	
	var selectedDate, today = new Date();
	
	var calendar = $('#calendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month'
		},
		titleFormat: {
			month: 'yyyy MMMM',
			week: "MMM d[ yyyy]{ '&#8212;'[ MMM] d yyyy}",
			day: 'dddd, MMM d, yyyy'
		},
		selectable: true,
		selectHelper: true,
		select: function(start, end, allDay) {
			var month = (start.getMonth() + 1);
			if (month < 10) {
				month = "0" + month;
			}
			var date = start.getDate();
			if (date < 10) {
				date = "0" + date;
			}
			var date = start.getFullYear() + "-" + month + "-" + date;
			
			
			if (canReservation(today, start)) {
				openReservationDialog(date);
			} else {
				var n = noty({text: '이전날은 예약 불가능합니다.', layout: 'center', timeout: 500, modal: true, force: false
					, type: 'information'});
			}

		},
		editable: true,
		eventResizeStop: function(event, jsEvent, ui, view) {
			//console.log(event, jsEvent, ui, view);
		},
		viewRender : function(view, element) {
			var d = $('#calendar').fullCalendar('getDate');
			var month = (d.getMonth() + 1);
			if (month < 10) {
				month = "0" + month;
			}
			var date = d.getDate();
			if (date < 10) {
				date = "0" + date;
			}
			var date = d.getFullYear() + "-" + month;
			
			$('#calendar').fullCalendar( 'removeEventSource',  '/reserve/list?startDate=' + date );
			$('#calendar').fullCalendar( 'addEventSource', '/reserve/list?startDate=' + date );
		}
	});
	
	function openReservationDialog(date) {
		selectedDate = date;
		var user = "${user.name}";
		if (user == null || user.length == 0) {
			var n = noty({text: '로그인후 이용하실 수 있습니다.', layout: 'center', timeout: false, modal: true, force: false
				, type: 'information'
				, buttons: [
				            {addClass: 'btn btn-mini btn-success', text: '로그인', onClick: function($noty) {
				            	 location.replace('/guest/loginForm');
				              }
				            },
				            {addClass: 'btn btn-mini', text: '취소', onClick: function($noty) {
				                $noty.close();
				              }
				            }
				          ]});
			return;
		} 
		$('#reservedAt').val(date);
		$('#myModalLabel').text(date + ' 예약');
		$('#dialog-form').modal('show');
	}
	
	$('#dialog-form').modal({
		show: false
	}).on('show', function(){
		$('#roomType').remove();
		$('#guestCount').remove();
		$.getJSON('/reserve/roomInfo.json?date=' + selectedDate, function(data) { 
    		var roomItems = [];
			$.each(data, function(key, value) {
				roomItems.push('<option value="' + value.nodeId + '" capacity="' + value.capacity + '">' + value.name + '</option>');
			});
    		  
			$('<select/>', {
    			'class': 'my-new-list',
    			'id': 'roomType',
    			'name': 'room.nodeId',
				html: roomItems.join('')
			}).appendTo('#lableRoomType');
			
			
			$('#roomType').on('change', function() {
				$('#guestCount').remove();
				var capacityItems = [];
				var capacity = $('#roomType option:selected').attr('capacity');
				for (var i = 1; i <= capacity; i++) {
					capacityItems.push('<option value="' + i + '">' + i + '</option>');
				}
				$('<select/>', {
	    			'id': 'guestCount',
	    			'name': 'guestCount',
					html: capacityItems.join('')
				}).appendTo('#labelGuestCount');
			});
			$('#roomType').change();
		});
	});
	
	
	$('#reservation').submit(function() {
		
		return true;
	});

	function canReservation(today, selectedDay) {
		today = new Date(today.getFullYear(), today.getMonth(), today.getDate(), 0, 0, 0, 0);
		selectedDay.setFullYear(selectedDay.getFullYear(), selectedDay.getMonth(), selectedDay.getDate());
		
		console.log(today, selectedDay);
		
		if (today <= selectedDay) return true;
		else return false;
	}
	
});

</script>
</head>
<body>
<div id='wrap'>
	<sec:authorize access="isAnonymous()">
	<div class="alert alert-block alert-info fade in">
  		<button type="button" class="close" data-dismiss="alert">×</button>
  		<h4 class="alert-heading">로그인 후 예약을 진행하실수 있습니다.</h4>
  		<p>아래 버튼을 클릭하여 로그인 후 이용해주세요.</p>
  		<p>
    		<a href="/guest/loginForm" class="btn btn-warning">log in</a>
  		</p>
	</div>
	</sec:authorize>
	
	<c:choose>
		<c:when test="${success}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>예약 완료!</strong> 예약 완료 되었습니다. <span class="label label-important">내 메뉴 > 예약 목록</span> 입금을 진행해주세요.
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>예약 실패!</strong> 이미 같은날 예약을 진행하신경우 예약 중복 예약 하실수 없습니다. 관리자에게 문의해주세요.
			</div>
		</c:otherwise>
	</c:choose>
	
	<div id='calendar'></div>

	<div id="dialog-form" title="예약 하기" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">Modal header</h3>
	  	</div>
    	<form:form commandName="reservation" action="reserve">
	  	<div class="modal-body">
	  		<fieldset>
	  			<form:hidden path="reservedAt"/>
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
	    	<button class="btn btn-success" type="submit">예약</button>
	    	<button class="btn" data-dismiss="modal" aria-hidden="true">닫기</button>
	  	</div>
	  	</form:form>
	</div>
	
	
</div> 
</body>
</html>



