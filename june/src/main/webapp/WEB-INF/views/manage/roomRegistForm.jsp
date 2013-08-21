<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
 
</style>
<script type="text/javascript">
$(function(){
	
});
</script>
</head>
<body>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   	<h3 id="myModalLabel">신규 객실 추가하기</h3>
</div>
<form:form cssClass="form-horizontal" commandName="room">
<form:errors path="name" ></form:errors>
<div class="modal-body">
 	<div class="control-group">
	    <label class="control-label" for="inputName">객실 이름</label>
	    <div class="controls">
      		<input type="text" name="name" id="inputName" placeholder="객실 이름">
    	</div>
	</div>
 	<div class="control-group">
		<label class="control-label" for="inputCapaticy">수용인원</label>
	  	<div class="controls">
	    	<input type="text" id="inputCapaticy" placeholder="1~10">
	  	</div>
	</div>
</div>
<div class="modal-footer">
  	<button class="btn btn-success" type="submit">저장</button>
  	<button class="btn" data-dismiss="modal" aria-hidden="true">닫기</button>
</div>
</form:form>
</body>
</body>
</html>