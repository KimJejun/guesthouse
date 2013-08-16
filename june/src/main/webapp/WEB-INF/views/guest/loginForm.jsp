<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
<style type="text/css">
.body_wrapper { margin-top : 80px; }
</style>
<script type="text/javascript">
$(function(){
	$('#username').focus();
});
</script>
</head>
<body>

<div class="container">

	<form action="j_spring_security_check" class="form-signin" method="post">
        <h2 class="form-signin-heading">로그인.</h2>
        <input id="username" type="text" class="input-block-level" placeholder="ID" name="j_username">
        <input type="password" class="input-block-level" placeholder="비밀번호" name="j_password">
        <button class="btn btn-large btn-primary" type="submit">로그인</button>
        <a href="/guest/regist" class="btn btn-large btn-primary">회원가입</a>
	</form>

</div>

</body>
</html>

