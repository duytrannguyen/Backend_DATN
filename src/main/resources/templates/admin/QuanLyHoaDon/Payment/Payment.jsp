<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hoá Đơn</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="/css/style.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>

<!-- link icon -->
<!-- FontAwesome 6.2.0 CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
	integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- (Optional) Use CSS or JS implementation -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/js/all.min.js"
	integrity="sha512-naukR7I+Nk6gp7p5TMA4ycgfxaZBJ7MO5iC3Fp6ySQyKFHOGfpkSZkYVWV5R7u7cfAicxanwYQ5D1e17EfJcMA=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<style type="text/css">
.card {
	margin-top: 15px;
	padding: 20px;
}

.form-check {
	font-size: small;
}

a {
	color: black;
	text-decoration: none;
}
</style>

</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<main class="">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-3">
							<h4>Hoá đơn</h4>
							<div class="card shadow p-3 mb-5 bg-body-tertiary rounded">
								<strong form-check>Thời Gian</strong>
								<hr>
								<div class="form-check">
									<input class="form-check-input" type="radio"
										name="flexRadioDefault11" id="flexRadioDefault1" checked>
									<label class="form-check-label" for="flexRadioDefault1">
										Toàn thời gian </label>
								</div>

								<div class="form-check ">
									<input class="form-check-input" type="radio"
										name="flexRadioDefault11" id="flexRadioDefault2"> <label
										class="form-check-label" for="flexRadioDefault2"> Thêm
										lựa chọn </label>
									<div class="date-range d-none form-check">
										Từ <input type="date" class="form-control form-check"
											id="startDate"> Đến <input type="date"
											class="form-control form-check" id="endDate">
									</div>
								</div>
							</div>
							<div class="card shadow p-3 mb-5 bg-body-tertiary rounded">
								<strong form-check>Trạng thái hoá đơn</strong>
								<hr>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value=""
										id="flexCheckDefault"> <label class="form-check-label"
										for="flexCheckDefault"> Hoàn thành </label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value=""
										id="flexCheckChecked" checked> <label
										class="form-check-label" for="flexCheckChecked"> Đang
										xử lý </label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value=""
										id="flexCheckChecked1"> <label
										class="form-check-label" for="flexCheckChecked1"> Đã
										huỷ </label>
								</div>
							</div>

							<div class="card shadow p-3 mb-5 bg-body-tertiary rounded">
								<strong form-check>Trạng thái thanh toán</strong>
								<hr>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value=""
										id="flexCheckDefault22"> <label
										class="form-check-label" for="flexCheckDefault22"> Đã
										thanh toán </label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value=""
										id="flexCheckChecked22" checked> <label
										class="form-check-label" for="flexCheckChecked22">Chưa
										thanh toán </label>
								</div>

							</div>

							<div class="card shadow p-3 mb-5 bg-body-tertiary rounded">
								<strong form-check>Phương Thức Thanh Toán</strong>
								<hr>

								<input class="form-control form-check" list="datalistOptions"
									id="exampleDataList" placeholder="Chọn phương thức">
								<datalist id="datalistOptions">
									<option value="Tiền Mặt"></option>
									<option value="ATM/ InternetBanking"></option>
								</datalist>

							</div>
						</div>
						<div class="col-md-9">
							<div class="col-md-12">
								<div class="row">
									<div class="col-md-6">
										<form class="d-flex" role="search">
											<input class="form-control me-2" type="search"
												placeholder="Tìm kiếm" aria-label="Search">
											<button class="btn btn-outline-primary" type="submit">
												<i class="fa-solid fa-magnifying-glass"></i>
											</button>
										</form>
									</div>
									<div class="col-md-6 d-flex justify-content-end">
										<!-- 								<button class="btn btn-primary me-2" type="submit"> -->
										<!-- 									<i class="fa-solid fa-plus"></i> Thêm mới -->
										<!-- 								</button> -->
										<div class="btn-group">
											<button type="button" class="btn btn-primary dropdown-toggle"
												data-bs-toggle="dropdown" aria-expanded="false">File
											</button>
											<ul class="dropdown-menu">
												<li><a class="dropdown-item" href="#"><i
														class="fa-solid fa-file-import"></i> Import</a></li>
												<li><a class="dropdown-item" href="#"><i
														class="fa-solid fa-up-right-from-square"></i> Xuất danh
														sách</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<br>
							<div class="card shadow p-3 mb-5 bg-body-tertiary rounded">
								<table class="table table-hover " id="myTable">
									<thead>
										<tr>
											<th>Mã hoá đơn</th>
											<th>Ngày tạo hoá đơn</th>
											<th>Khách hàng</th>
											<th>TT thanh toán</th>
											<th>TT hoá đơn</th>
											<th>Hình thức thanh toán</th>
											<th>Tổng tiền</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<!--đơn1 -->
										<c:forEach var="invoice" items="${invoices}">
											<tr class="transaction-row" data-toggle-tabs="false">
												<td>${invoice.invoiceId}</td>
												<td><fmt:formatDate value="${invoice.paymentDate}"
														pattern="yyyy-MM-dd" /></td>
												<td><c:if
														test="${not empty invoice.cart.user.username}">
               										 ${invoice.cart.user.username}
          											  </c:if></td>
												<td>${invoice.paymentStatus}</td>
												<td>${invoice.status.statusName}</td>
												<td>${invoice.paymentMethod.paymentMethodName}</td>
												<td><fmt:formatNumber value="${invoice.totalAmount}"
														type="currency" />đ</td>
												<td><a href="/admin/payment/edit/${invoice.invoiceId}"
													class="btn btn-primary"> <i
														class="fa-regular fa-pen-to-square"></i>
												</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>

	</div>

	
	<script type="text/javascript">
		const fullTimeRadio = document.getElementById('flexRadioDefault1');
		const additionalRadio = document.getElementById('flexRadioDefault2');
		const dateRange = document.querySelector('.date-range');

		fullTimeRadio.addEventListener('click', () => {
		  dateRange.classList.add('d-none');
		});

		additionalRadio.addEventListener('click', () => {
		  dateRange.classList.remove('d-none');
		});
		fullTimeRadio.addEventListener('click', () => {
			  dateRange.classList.add('d-none');
			  additionalRadio.checked = false;
			});
	</script>
</body>
</html>