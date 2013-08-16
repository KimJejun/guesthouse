<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 가입</title>
<style type="text/css">
.body_wrapper { margin-top : 80px; }
</style>
<script type="text/javascript">
$(function(){
	$('#id').focus();
});
</script>
</head>
<body>


<div class="container">

	<form:form commandName="guest" action="regist" cssClass="form-signin">
		<form:errors/>
        <h2 class="form-signin-heading">회원 가입.</h2>
        <input type="text" class="input-block-level" placeholder="ID" name="id" id="id">
        <input type="password" class="input-block-level" placeholder="비밀번호" name="password">
        <input type="text" class="input-block-level" placeholder="이름" name="name">
        <input type="text" class="input-block-level" placeholder="이메일 주소" name="email">
        <input type="text" class="input-block-level" placeholder="연락처" name="contact">
        <button class="btn btn-large btn-primary" type="submit">가입</button>
	</form:form>

</div>

</body>
</html>

