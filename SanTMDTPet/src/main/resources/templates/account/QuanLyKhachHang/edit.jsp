<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Client</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="/css/style.css">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>

<!-- FontAwesome 6.2.0 CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
	integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- FontAwesome 6.2.0 JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/js/all.min.js"
	integrity="sha512-naukR7I+Nk6gp7p5TMA4ycgfxaZBJ7MO5iC3Fp6ySQyKFHOGfpkSZkYVWV5R7u7cfAicxanwYQ5D1e17EfJcMA=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<style type="text/css">
.card {
	margin-top: 15px;
	padding: 20px;
}

.form-control {
	font-size: small;
}

.col-form-label {
	font-size: small;
}

a {
	color: black;
	text-decoration: none;
}
</style>
</head>
<body>
	<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="/admin/client/list">List
					Client</a></li>
			<li class="breadcrumb-item active text-primary" aria-current="page">Edit</li>
		</ol>
	</nav>
	<div class="card">
		<div class="card-header">
			<h3>Cập Nhật Khách hàng</h3>
		</div>
		<form action="/admin/client/update/${itemProd.username}" method="post"
			enctype="multipart/form-data">
			<hr>
			<div class="col-md-12">
				<div class="row">
					<div class="col-md-6">
						<div class="mb-3 row">
							<label for="staticUsername" class="col-sm-5 col-form-label">Tên
								Đăng Nhập</label>
							<div class="col-sm-7">
								<input type="text" readonly
									class="form-control-plaintext fw-bold" id="username"
									name="username" value="${itemProd.username}">
							</div>
						</div>
						<hr>
						<div class="mb-3 row">
							<label for="fullName" class="col-sm-5 col-form-label">Tên
								Khách Hàng</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="fullName"
									name="fullName" value="${itemProd.fullName}">
							</div>
						</div>
						<hr>
						<div class="mb-3 row">
							<label for="phone" class="col-sm-5 col-form-label">Số
								Điện Thoại</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="phone" name="phone"
									value="${itemProd.phone}">
							</div>
						</div>
						<hr>
						<div class="mb-3 row">
							<label for="birthDate" class="col-sm-5 col-form-label">Ngày
								Sinh</label>
							<div class="col-sm-7">
								<input type="date" class="form-control" id="birthDate"
									name="birthDate" value="${itemProd.birthDate}">
							</div>
						</div>
						<hr>
						<div class="mb-3 row">
							<label class="col-sm-5 col-form-label">Giới Tính</label>
							<div class="col-sm-7">
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="gender"
										id="genderMale" value="true"
										${itemProd.gender ? 'checked' : ''}> <label
										class="form-check-label" for="genderMale">Nam</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="gender"
										id="genderFemale" value="false"
										${!itemProd.gender ? 'checked' : ''}> <label
										class="form-check-label" for="genderFemale">Nữ</label>
								</div>
							</div>
						</div>
					</div>
					<!-- Contact Information and Address Section -->
					<div class="col-md-6">
						<div class="mb-3 row">
							<label for="email" class="col-sm-5 col-form-label">Email</label>
							<div class="col-sm-7">
								<input type="email" class="form-control" id="email" name="email"
									value="${itemProd.email}">
							</div>
						</div>
						<hr>
						<div class="mb-3 row">
							<label for="address" class="col-sm-5 col-form-label">Địa
								Chỉ</label>
							<div class="col-sm-7">
								<textarea class="form-control" id="address" name="address"
									rows="3">${itemProd.address}</textarea>
							</div>
						</div>
						<hr>
						<div class="mb-3 row">
							<label for="profileImage" class="col-sm-5 col-form-label">Hình
								Ảnh</label>
							<div class="col-sm-7">
								<input type="file" class="form-control" id="profileImage"
									name="profileImage"> <img
									src="/Image_Users/${itemProd.profileImage}" width="100px"
									height="100px">
							</div>
						</div>
						<hr>
						<div class="mb-3 row">
							<label for="roleId" class="col-sm-5 col-form-label">Vai
								Trò</label>
							<div class="col-sm-7">
								<select class="form-control" id="roleId" name="roleId">
									<c:forEach var="role" items="${roles}">
										<option value="${role.roleId}"
											${itemProd.roleId.roleId == role.roleId ? 'selected' : ''}>${role.roleName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
				<!-- Control Buttons Section -->
				<div class="row">
					<div class="col-md-12 text-end">

						<button class="btn btn-outline-success"
							formaction="/admin/client/update/${itemProd.usersId}">Update</button>
						<%-- <button class="btn btn-outline-danger"
							formaction="/admin/client/delete/${itemProd.usersId}">Delete</button> --%>
						<a href="/admin/client/list" class="btn btn-secondary btn-sm">
							<i class="fa-solid fa-arrow-left"></i> Quay Lại
						</a>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div aria-live="polite" aria-atomic="true"
		style="position: relative; min-height: 200px;">
		<div class="toast-container position-absolute top-0 end-0 p-3">
			<c:if test="${not empty error}">
				<div class="toast show" role="alert" aria-live="assertive"
					aria-atomic="true">
					<div class="toast-header bg-danger text-white">
						<strong class="me-auto">Lỗi</strong>
						<button type="button" class="btn-close" data-bs-dismiss="toast"
							aria-label="Close"></button>
					</div>
					<div class="toast-body">${error}</div>
				</div>
			</c:if>
			<c:if test="${not empty successMessage}">
				<div class="toast show" role="alert" aria-live="assertive"
					aria-atomic="true">
					<div class="toast-header bg-success text-white">
						<strong class="me-auto">Thành công</strong>
						<button type="button" class="btn-close" data-bs-dismiss="toast"
							aria-label="Close"></button>
					</div>
					<div class="toast-body">${successMessage}</div>
				</div>
			</c:if>
		</div>
	</div>

</body>

<!-- Include Bootstrap JS and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"
	integrity="sha384-WvbYB4vPw3sJ6Ujlpn4BAGFVQ9b8JGBXV3yZ4c4HGZAXX7xHT4gCDb2JJCfjE0zo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
	integrity="sha384-b4Aiq1TkE0fdsVQ8pfFj1y8ck+Ll5vHs47NF0geDEZKa0B+Q/U7X9vCvF3O7bV6D"
	crossorigin="anonymous"></script>

<!-- Script to show toast automatically -->
<script>
	$(document).ready(function() {
		$('.toast').toast('show');
	});
</script>
<script>
	// Script tự động hiển thị toast
	document.addEventListener('DOMContentLoaded', function() {
		var toastElList = [].slice.call(document.querySelectorAll('.toast'));
		var toastList = toastElList.map(function(toastEl) {
			return new bootstrap.Toast(toastEl);
		});
		toastList.forEach(function(toast) {
			toast.show();
		});
	});
</script>
</html>