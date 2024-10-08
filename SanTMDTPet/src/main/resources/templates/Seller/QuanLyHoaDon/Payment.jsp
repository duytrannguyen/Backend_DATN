<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="/css/admin.css">
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="/views/admin_layout/aside.jsp" />
			<div class="col-md-10">
				<jsp:include page="/views/admin_layout/header.jsp" />
				<main class="mt-5">
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
											class="form-check-label" for="flexRadioDefault2">
											Thêm lựa chọn </label>
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
											id="flexCheckDefault"> <label
											class="form-check-label" for="flexCheckDefault"> Hoàn
											thành </label>
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
												<button type="button"
													class="btn btn-primary dropdown-toggle"
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
											<!-- 								đơn1 -->
											<tr class="transaction-row" data-toggle-tabs="false">
												<td>HD0001</td>
												<td>20/05/2024 04:40PM</td>
												<td>AbcLy</td>
												<td>Đã Thanh Toán</td>
												<td>Hoàn Thành</td>
												<td>Tiền mặt</td>
												<td>200.000 đ</td>
												<td><button type="button" class="btn btn-primary"
														data-bs-toggle="modal" data-bs-target="#exampleModal">
														<i class="fa-regular fa-pen-to-square"></i>
													</button></td>
											</tr>
											<!-- 								đơn2 -->
											<tr class="transaction-row" data-toggle-tabs="false">
												<td>HD0001</td>
												<td>20/05/2024 04:40PM</td>
												<td>AbcLy</td>
												<td>Đã Thanh Toán</td>
												<td>Hoàn Thành</td>
												<td>Tiền mặt</td>
												<td>200.000 đ</td>
												<td><button type="button" class="btn btn-primary"
														data-bs-toggle="modal" data-bs-target="#exampleModal">
														<i class="fa-regular fa-pen-to-square"></i>
													</button></td>
											</tr>
											<!-- 								đơn3 -->
											<tr class="transaction-row" data-toggle-tabs="false">
												<td>HD0001</td>
												<td>20/05/2024 04:40PM</td>
												<td>AbcLy</td>
												<td>Đã Thanh Toán</td>
												<td>Hoàn Thành</td>
												<td>Tiền mặt</td>
												<td>200.000 đ</td>
												<td><button type="button" class="btn btn-primary"
														data-bs-toggle="modal" data-bs-target="#exampleModal">
														<i class="fa-regular fa-pen-to-square"></i>
													</button></td>
											</tr>
											<!-- 								đơn4 -->
											<tr class="transaction-row" data-toggle-tabs="false">
												<td>HD0001</td>
												<td>20/05/2024 04:40PM</td>
												<td>AbcLy</td>
												<td>Đã Thanh Toán</td>
												<td>Hoàn Thành</td>
												<td>Tiền mặt</td>
												<td>200.000 đ</td>
												<td><button type="button" class="btn btn-primary"
														data-bs-toggle="modal" data-bs-target="#exampleModal">
														<i class="fa-regular fa-pen-to-square"></i>
													</button></td>
											</tr>
											<!-- 								<tr class="tab-content" style="display: none;"> -->
											<!-- 									<td colspan="8"> -->
											<!-- 										<ul class="nav nav-tabs"> -->
											<!-- 											<li class="nav-item form-check"><a -->
											<!-- 												class="nav-link active" href="#">Chi tiết đơn hàng</a></li> -->
											<!-- 										</ul> -->
											<!-- 										<div class="tab-content form-check"> -->
											<!-- 											<div class="tab-pane active" id="chi-tiet"> -->
											<!-- 												<div class="card"> -->
											<!-- 													<form action=""> -->
											<!-- 														<div class="col-md-12"> -->
											<!-- 															<div class="row"> -->
											<!-- 																<div class="col-md-6"> -->
											<!-- 																	<div class="mb-3 row"> -->
											<!-- 																		<label for="staticEmail" -->
											<!-- 																			class="col-sm-5 col-form-label">Mã Hóa Đơn</label> -->
											<!-- 																		<div class="col-sm-5"> -->
											<!-- 																			<input type="text" readonly -->
											<!-- 																				class="form-control-plaintext fw-bold" -->
											<!-- 																				id="staticEmail" value="HD0001"> -->
											<!-- 																		</div> -->
											<!-- 																	</div> -->
											<!-- 																	<hr> -->
											<!-- 																	<div class="mb-3 row"> -->
											<!-- 																		<label for="staticEmail" -->
											<!-- 																			class="col-sm-5 col-form-label">Thời Gian</label> -->
											<!-- 																		<div class="col-sm-5"> -->
											<!-- 																			<input type="date" -->
											<!-- 																				class="form-control-plaintext border-bottom border-1 grey" -->
											<!-- 																				id="staticEmail"> -->
											<!-- 																		</div> -->
											<!-- 																	</div> -->
											<!-- 																	<hr> -->
											<!-- 																	<div class="mb-3 row"> -->
											<!-- 																		<label for="staticEmail" -->
											<!-- 																			class="col-sm-5 col-form-label">Khách Hàng</label> -->
											<!-- 																		<div class="col-sm-5"> -->
											<!-- 																			<a href="#"><input type="text" readonly -->
											<!-- 																				class="form-control-plaintext fw-bold" -->
											<!-- 																				id="staticEmail" value="KH001 - AbcLy"></a> -->
											<!-- 																		</div> -->
											<!-- 																	</div> -->
											<!-- 																	<hr> -->
											<!-- 																	<div class="mb-3 row"> -->
											<!-- 																		<label for="staticEmail" -->
											<!-- 																			class="col-sm-5 col-form-label">Số Lượng Sản -->
											<!-- 																			Phẩm</label> -->
											<!-- 																		<div class="col-sm-5"> -->
											<!-- 																			<a href="#"><input type="text" readonly -->
											<!-- 																				class="form-control-plaintext" id="staticEmail" -->
											<!-- 																				value="1"></a> -->
											<!-- 																		</div> -->
											<!-- 																	</div> -->
											<!-- 																	<hr> -->

											<!-- 																</div> -->
											<!-- 																<div class="col-md-6" style="margin-bottom: 20px;"> -->
											<!-- 																	<div class="mb-3 row"> -->
											<!-- 																		<label for="staticEmail" -->
											<!-- 																			class="col-sm-5 col-form-label">Giao Dự Kiến</label> -->
											<!-- 																		<div class="col-sm-5"> -->
											<!-- 																			<input type="date" -->
											<!-- 																				class="form-control-plaintext border-bottom border-1 grey" -->
											<!-- 																				id="staticEmail"> -->
											<!-- 																		</div> -->
											<!-- 																	</div> -->
											<!-- 																	<hr> -->
											<!-- 																	<div class="mb-3 row"> -->
											<!-- 																		<label for="staticEmail" -->
											<!-- 																			class="col-sm-5 col-form-label">Trạng Thái -->
											<!-- 																			Thanh Toán</label> -->
											<!-- 																		<div class="col-sm-5"> -->
											<!-- 																			<select class="form-select form-check" -->
											<!-- 																				aria-label="Default select example"> -->
											<!-- 																				<option value="1">Đã Thanh Toán</option> -->
											<!-- 																				<option value="2">Chưa Thanh Toán</option> -->

											<!-- 																			</select> -->
											<!-- 																		</div> -->
											<!-- 																	</div> -->
											<!-- 																	<hr> -->
											<!-- 																	<div class="mb-3 row"> -->
											<!-- 																		<label for="staticEmail" -->
											<!-- 																			class="col-sm-5 col-form-label">Trạng Thái -->
											<!-- 																			Hóa Đơn</label> -->
											<!-- 																		<div class="col-sm-5"> -->
											<!-- 																			<select class="form-select form-check" -->
											<!-- 																				aria-label="Default select example"> -->

											<!-- 																				<option value="1">Hoàn Thành</option> -->
											<!-- 																				<option value="2" selected>Đang Xử Lý</option> -->
											<!-- 																				<option value="3">Đã Hủy</option> -->

											<!-- 																			</select> -->
											<!-- 																		</div> -->
											<!-- 																	</div> -->
											<!-- 																	<hr> -->
											<!-- 																	<div class="form-floating"> -->
											<!-- 																		<textarea class="form-control" -->
											<!-- 																			placeholder="Leave a comment here" -->
											<!-- 																			id="floatingTextarea2" style="height: 100px"></textarea> -->
											<!-- 																		<label for="floatingTextarea2"><i -->
											<!-- 																			class="fa-solid fa-pen"></i> Ghi Chú</label> -->
											<!-- 																	</div> -->
											<!-- 																</div> -->
											<!-- 																<br> <br> -->
											<!-- 																<div> -->
											<!-- 																	<p class="text-center fw-bold">Chi Tiết Hóa Đơn</p> -->
											<!-- 																</div> -->
											<!-- 																<table class="table table-hover"> -->
											<!-- 																	<thead> -->
											<!-- 																		<tr> -->
											<!-- 																			<th>Mã Sản Phẩm</th> -->
											<!-- 																			<th>Tên Sản phẩm</th> -->
											<!-- 																			<th>Số Lượng</th> -->
											<!-- 																			<th>Đơn Giá</th> -->
											<!-- 																			<th>Thành Tiền</th> -->
											<!-- 																		</tr> -->
											<!-- 																	</thead> -->
											<!-- 																	<tbody> -->
											<!-- 																		<tr> -->
											<!-- 																			<td>SP001</td> -->
											<!-- 																			<td>Công Bằng Trên Đời Là Do Bạn Nỗ Lực Giành -->
											<!-- 																				Lấy</td> -->
											<!-- 																			<td>1</td> -->
											<!-- 																			<td>89.000 đ</td> -->
											<!-- 																			<th>89.000 đ</th> -->
											<!-- 																		</tr> -->
											<!-- 																	</tbody> -->
											<!-- 																</table> -->
											<!-- 																<div class="col-md-12"> -->
											<!-- 																	<div class="row"> -->
											<!-- 																		<div class="col-md-10"> -->
											<!-- 																			<p class="text-sm-end">Tổng số lượng</p> -->
											<!-- 																			<p class="text-sm-end">Tổng tiền hàng</p> -->
											<!-- 																			<p class="text-sm-end">Cần thành toán</p> -->
											<!-- 																		</div> -->
											<!-- 																		<div class="col-md-2"> -->
											<!-- 																			<p class="text-center fw-bold">1</p> -->
											<!-- 																			<p class="text-center fw-bold">89.000 đ</p> -->
											<!-- 																			<p class="text-center fw-bold">89.000 đ</p> -->
											<!-- 																		</div> -->
											<!-- 																	</div> -->
											<!-- 																</div> -->
											<!-- 																<div class="col-md-12 text-end"> -->
											<!-- 																	<button type="button" class="btn btn-primary"> -->
											<!-- 																		<i class="fa-solid fa-floppy-disk"></i> Cập Nhật -->
											<!-- 																	</button> -->
											<!-- 																																		<a href="/home/admin/order" class="btn btn-primary"><i -->
											<!-- 																																			class="fa-regular fa-hourglass-half"></i> Xử Lý Đơn -->
											<!-- 																																			Hàng</a> -->
											<!-- 																	<button type="button" class="btn btn-secondary"> -->
											<!-- 																		<i class="fa-solid fa-print"></i> In Hóa Đơn -->
											<!-- 																	</button> -->
											<!-- 																	<button type="button" class="btn btn-danger"> -->
											<!-- 																		<i class="fa-solid fa-trash"></i> Xóa -->
											<!-- 																	</button> -->
											<!-- 																</div> -->
											<!-- 															</div> -->
											<!-- 														</div> -->
											<!-- 													</form> -->
											<!-- 												</div> -->
											<!-- 											</div> -->

											<!-- 										</div> -->
											<!-- 									</td> -->
											<!-- 								</tr> -->
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</main>
			</div>

		</div>
	</div>

	<!-- MODAL -->
	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Hóa Đơn
						Chi Tiết</h1>
				</div>
				<div class="modal-body">
					<div class="card">
						<form action="">
							<div class="col-md-12">
								<div class="row">
									<div class="col-md-6">
										<div class="mb-3 row">
											<label for="staticEmail" class="col-sm-5 col-form-label">Mã
												Hóa Đơn</label>
											<div class="col-sm-5">
												<input type="text" readonly
													class="form-control-plaintext fw-bold" id="staticEmail"
													value="HD0001">
											</div>
										</div>
										<hr>
										<div class="mb-3 row">
											<label for="staticEmail" class="col-sm-5 col-form-label">Thời
												Gian</label>
											<div class="col-sm-5">
												<input type="date"
													class="form-control-plaintext border-bottom border-1 grey"
													id="staticEmail">
											</div>
										</div>
										<hr>
										<div class="mb-3 row">
											<label for="staticEmail" class="col-sm-5 col-form-label">Khách
												Hàng</label>
											<div class="col-sm-5">
												<a href="/home/admin/client"><input type="text" readonly
													class="form-control-plaintext fw-bold" id="staticEmail"
													value="KH001 - AbcLy"></a>
											</div>
										</div>
										<hr>
										<div class="mb-3 row">
											<label for="staticEmail" class="col-sm-5 col-form-label">Số
												Lượng Sản Phẩm</label>
											<div class="col-sm-5">
												<a href="#"><input type="text" readonly
													class="form-control-plaintext" id="staticEmail" value="1"></a>
											</div>
										</div>
										<hr>

									</div>
									<div class="col-md-6" style="margin-bottom: 20px;">
										<div class="mb-3 row">
											<label for="staticEmail" class="col-sm-5 col-form-label">Giao
												Dự Kiến</label>
											<div class="col-sm-5">
												<input type="date"
													class="form-control-plaintext border-bottom border-1 grey"
													id="staticEmail">
											</div>
										</div>
										<hr>
										<div class="mb-3 row">
											<label for="staticEmail" class="col-sm-5 col-form-label">Trạng
												Thái Thanh Toán</label>
											<div class="col-sm-5">
												<select class="form-select form-check"
													aria-label="Default select example">
													<option value="1">Đã Thanh Toán</option>
													<option value="2">Chưa Thanh Toán</option>

												</select>
											</div>
										</div>
										<hr>
										<div class="mb-3 row">
											<label for="staticEmail" class="col-sm-5 col-form-label">Trạng
												Thái Hóa Đơn</label>
											<div class="col-sm-5">
												<select class="form-select form-check"
													aria-label="Default select example">

													<option value="1">Hoàn Thành</option>
													<option value="2" selected>Đang Xử Lý</option>
													<option value="3">Đã Hủy</option>

												</select>
											</div>
										</div>
										<hr>
										<div class="form-floating">
											<textarea class="form-control"
												placeholder="Leave a comment here" id="floatingTextarea2"
												style="height: 100px"></textarea>
											<label for="floatingTextarea2"><i
												class="fa-solid fa-pen"></i> Ghi Chú</label>
										</div>
									</div>
									<br> <br>
									<div>
										<p class="text-center fw-bold">Chi Tiết Hóa Đơn</p>
									</div>
									<table class="table table-hover">
										<thead>
											<tr class="table table-primary">
												<th>Mã Sản Phẩm</th>
												<th>Tên Sản phẩm</th>
												<th>Số Lượng</th>
												<th>Đơn Giá</th>
												<th>Thành Tiền</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>SP001</td>
												<td>Công Bằng Trên Đời Là Do Bạn Nỗ Lực Giành Lấy</td>
												<td>1</td>
												<td>89.000 đ</td>
												<th>89.000 đ</th>
											</tr>
										</tbody>
									</table>
									<div class="col-md-12">
										<div class="row">
											<div class="col-md-10">
												<p class="text-sm-end">Tổng số lượng</p>
												<p class="text-sm-end">Tổng tiền hàng</p>
												<p class="text-sm-end">Cần thành toán</p>
											</div>
											<div class="col-md-2">
												<p class="text-center fw-bold">1</p>
												<p class="text-center fw-bold">89.000 đ</p>
												<p class="text-center fw-bold">89.000 đ</p>
											</div>
										</div>
									</div>
									<div class="col-md-12 text-end">
										<button type="button" class="btn btn-primary">
											<i class="fa-solid fa-floppy-disk"></i> Cập Nhật
										</button>
										<button type="button" class="btn btn-primary">
											<i
											class="fa-regular fa-hourglass-half"></i> Xử Lý Đơn Hàng
										</button>
										<button type="button" class="btn btn-secondary">
											<i class="fa-solid fa-print"></i> In Hóa Đơn
										</button>
										<button type="button" class="btn btn-danger">
											<i class="fa-solid fa-trash"></i> Xóa
										</button>
										<button type="button" class="btn btn-secondary"
											data-bs-dismiss="modal">Đóng</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
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