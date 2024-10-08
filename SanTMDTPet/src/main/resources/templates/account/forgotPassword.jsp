<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style type="text/css">
.container{
max-width: 500px;}


</style>
</head>
<body>
<jsp:include page="../client_layout/header.jsp"></jsp:include>
	<div class="page-heading normal-space">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1>Quên mật khẩu</h1>
                </div>
            </div>
        </div>
    </div>
	<br>
	<div class="container">
	<div class="card">
	<div class="card-header">
	<h1>Forgot Password</h1>
	</div>
	<div class="card-body">
	<div class="row">
	<div class="col-md-6">
	<img alt="" src="/images/sach1.jpg" style="max-width: 100px;">
	</div>
	<div class="col-md-6">
    <form action="/forgot-password" method="post">
        <label for="email">Email:</label>
        <br>
        <input style="max-width: 1000px;" type="email" id="email" name="email" required><br><br>
        
        <button type="submit" value="Send OTP" class="btn btn-outline-primary">gửi Otp</button>

    </form>
    </div>
    </div>
    </div>
    </div>
</div>
<br>
<jsp:include page="../client_layout/footer.jsp"></jsp:include>
</body>
</html>