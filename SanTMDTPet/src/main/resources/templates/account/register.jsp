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
	<header>
		<jsp:include page="/views/client_layout/header.jsp"/>
	</header>
	<br>
	<div class="container d-flex justify-content-center">
		<div class="col-md-6">
			<div class="row">
				<div class="card">
					<div class="card-header">
						<a class="headerlogin"><strong><i
								class="bi bi-person-fill-lock"></i> Đăng ký</strong> </a>
					</div>
					<div class="card-body">
						<form action="register" method="get">
							<label>Tên đăng nhập:</label> <input name="userName"
								class="form-control" placeholder="" type="text"> <br>
							<label>Họ và tên:</label> <input name="fullName"
								class="form-control" placeholder="" type="text"> <br>
							<label>Ngày sinh:</label> <input name="birthDay"
								class="form-control" placeholder="" type="date"> <br>
							<label>Gmail:</label> <input name="eMail" class="form-control"
								placeholder="" type="text"> <br> <label>Số
								điện thoại:</label> <input name="userName" class="form-control"
								placeholder="" type="number"> <br> <label>Mật
								khẩu:</label> <input name="passWord" class="form-control"
								placeholder="Mật khẩu phải nhiều hơn 8 kí tự" type="password">
							<br> <label>Giới Tính:</label> <br>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio"
									name="inlineRadioOptions" id="inlineRadio1" value="option1">
								<label class="form-check-label" for="inlineRadio1">Nữ</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio"
									name="inlineRadioOptions" id="inlineRadio2" value="option2">
								<label class="form-check-label" for="inlineRadio2">Nam</label>
							</div>
							<br> <a class="dang-nhap" href="" style="font-size: 16px">Bạn
								đã có tài khoản?</a> <br> <br>
							<button class="btn btn-outline-success">Login</button>
							<button class="btn btn-outline-danger">Quay Lại</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer>
	<jsp:include page="/views/client_layout/footer.jsp"/>
	</footer>
</body>
</html>