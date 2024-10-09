<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
.container {
	max-width: 1000px
}

.headerlogin {
	font-size: 30px;
}
</style>
</head>
<body>
	<br>

	<div class="container d-flex justify-content-center">
		<div class="col-md-6">
			<div class="row">
				<div class="card">
					<h1>Verify OTP</h1>
					<form action="/verify-otp" method="post">
						<input type="hidden" name="email" value="${email}"> <label
							for="otp">OTP:</label> <input type="number" id="otp" name="otp"
							required><br>
						<br> <input type="submit" value="Verify OTP">
					</form>
					<p>${message}</p>
				</div>
			</div>
		</div>
	</div>

</body>
</html>