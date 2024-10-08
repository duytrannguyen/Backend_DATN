<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký </title>
<link rel="shortcut icon" href="/assets/images/logobook.ico"
	type="image/x-icon">
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
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js">
	
</script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js">
	
</script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js">
	
</script>


<style type="text/css">
body {
	background-image: url('/images/bgLogin.jpg');
	height: 100%;
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
}

.btn-login {
	font-size: 0.9rem;
	letter-spacing: 0.05rem;
	padding: 0.75rem 1rem;
}
</style>
</head>
<body>
<jsp:include page="../client_layout/header.jsp"></jsp:include>
	<div class="page-heading normal-space">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<h1 style="color: white;">Đăng Ký</h1>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
				<div class="card border-0 shadow rounded-3 my-5">
					<div class="bg-primary d-grid"
						style="place-items: center; height: 50px">
						<h5 class="card-title text-light fw-bolder fs-5 ">Register</h5>
					</div>
					<div class="card-body p-4 p-sm-5">
						<form action="/home/dangky" method="post" class="row">
							<div class="form-floating mb-3 col-12">
								<input type="text" class="form-control" id="username"
									name="username" placeholder="Tài khoản" required> <label
									for="username">Tài khoản</label>
							</div>
							<div class="form-floating mb-3 col-6">
								<input type="text" class="form-control" id="fullName"
									name="fullName" placeholder="Họ tên" required> <label
									for="fullName">Họ tên</label>
							</div>
							<div class="form-floating mb-3 col-6">
								<input type="email" class="form-control" id="email" name="email"
									placeholder="Email" required> <label for="email">Email</label>
							</div>
							<div class="form-floating mb-3 col-12">
								<textarea type="text" class="form-control" id="address"
									name="address" placeholder="Địa chỉ"></textarea>
								<label for="address">Địa chỉ</label>
							</div>
							<div class="form-floating mb-3 col-12">
								<input type="date" class="form-control" id="birthDate"
									name="birthDate" placeholder="Ngày sinh" required> <label
									for="birthDate">Ngày sinh</label>
							</div>
							<div class="form-floating mb-3 col-6">
								<input type="text" class="form-control" id="phone" name="phone"
									placeholder="Số điện thoại" required> <label
									for="phone">Số điện thoại</label>
							</div>
							<div class="form-floating mb-3 col-6">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="Mật khẩu" required> <label
									for="password">Mật khẩu</label>
							</div>
							<div class="form-floating mb-3 col-6">
								<div>
									<label>Giới tính</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" required
										id="inlineRadio1" name="gender" value="false"> <label
										class="form-check-label" for="inlineRadio1">Nữ</label>

								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" required
										id="inlineRadio2" name="gender" value="true"> <label
										class="form-check-label" for="inlineRadio2">Nam</label>

								</div>
							</div>
							<div class="d-grid">
								<button class="btn btn-danger btn-login text-uppercase fw-bold"
									type="submit">Sign Up</button>
									<div class="text-center fw-bolder">${mess }</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

</body>
</html>