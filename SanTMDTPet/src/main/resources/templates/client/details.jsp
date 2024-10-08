<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="author" content="templatemo">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

<title>Liberty Template - NFT Item Detail Page</title>

<!-- Bootstrap core CSS -->
<link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">


<!-- Additional CSS Files -->
<link rel="stylesheet" href="/assets/css/fontawesome.css">
<link rel="stylesheet" href="/assets/css/templatemo-liberty-market.css">
<link rel="stylesheet" href="/assets/css/owl.css">
<link rel="stylesheet" href="/assets/css/animate.css">
<link rel="stylesheet"
	href="https://unpkg.com/swiper@7/swiper-bundle.min.css" />
<!--

TemplateMo 577 Liberty Market

https://templatemo.com/tm-577-liberty-market

-->
<style type="text/css">
.a-product {
	transition: all 0.3s ease-in-out;
}

.a-product:hover {
	transform: translateY(-5px);
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
}

.hover-shadow-lg:hover {
	box-shadow: 0 1rem 3rem rgba(0, 0, 0, 0.175) !important;
}

.btnn {
	margin-top: 40px;
	margin-left: 20px;
	margin-bottom: 10px;
	width: 220px;
	height: 40px;
	width: 220px;
}
</style>
</head>

<body>

	<!-- ***** Preloader Start ***** -->
	<div id="js-preloader" class="js-preloader">
		<div class="preloader-inner">
			<span class="dot"></span>
			<div class="dots">
				<span></span> <span></span> <span></span>
			</div>
		</div>
	</div>
	<!-- ***** Preloader End ***** -->

	<!-- ***** Header Area Start ***** -->
	<header class="header-area header-sticky">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="main-nav navbar navbar-expand-lg navbar-light bg-light">
						<!-- ***** Logo Start ***** -->
						<a href="/home" class="logo navbar-brand"> <img
							src="/assets/images/logobook.jpg" alt="">
						</a>
						<!-- ***** Logo End ***** -->
						<!-- ***** Menu Start ***** -->
						<button class="navbar-toggler" type="button"
							data-bs-toggle="collapse" data-bs-target="#navbarNav"
							aria-controls="navbarNav" aria-expanded="false"
							aria-label="Toggle navigation">
							<span class="navbar-toggler-icon"></span>
						</button>
						<div class="collapse navbar-collapse" id="navbarNav">
							<ul class="nav navbar-nav">
								<li class="nav-item"><a href="/home" class="nav-link">Home</a>
								</li>
								<li class="nav-item"><a href="#" class="nav-link">Giới
										Thiệu</a></li>
								<li class="nav-item"><a href="#" class="nav-link">Liên
										Hệ</a></li>
								<li class="nav-item"><a href="#" class="nav-link">Sản
										Phẩm</a></li>
								<li class="nav-item dropdown"><a
									class="nav-link dropdown-toggle" id="accountDropdown"
									role="button" data-bs-toggle="dropdown" aria-expanded="false">
										Tài Khoản </a>
									<ul class="dropdown-menu" aria-labelledby="accountDropdown">
										<li><a class="dropdown-item" href="">Đăng Ký</a></li>
										<li><a class="dropdown-item" href="">Đăng Nhập</a></li>
										<li><a class="dropdown-item" href="">Quên Mật Khẩu</a></li>
										<hr>
										<li><a class="dropdown-item" href="">Thông Tin Tài
												Khoản</a></li>
										<li><a class="dropdown-item" href="">Đổi Mật Khẩu</a></li>
										<li><a class="dropdown-item" href="">Giỏ Hàng</a></li>
										<li><a class="dropdown-item" href="">Đơn Hàng Đã Mua</a></li>
										<li><a class="dropdown-item" href="">Lịch Sử Đơn Hàng</a></li>
									</ul></li>
							</ul>
						</div>
						<!-- ***** Menu End ***** -->
					</nav>
				</div>
			</div>
		</div>
	</header>
	<!-- ***** Header Area End ***** -->

	<div class="page-heading normal-space">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<h6>QLBook Store</h6>
					<h2>Xem Chi Tiết Sản Phẩm</h2>
					<span> <a href="/home">Home > </a> <a href="#">Chi Tiết
							Sản Phẩm</a></span>
				</div>
			</div>
		</div>
	</div>

	<div class="item-details-page">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="section-heading">
						<div class="line-dec"></div>
						<h2>
							Chi Tiết <em>Sản Phẩm</em>
						</h2>
					</div>
				</div>
				<!--         <div class="card"> -->
				<div class="col-md-12">
					<div class="row">

						<!-- 				chi tiết sản phẩm -->
						<div class="col-md-5">
							<img src="/images/sach3.jpg" class="card-img-top" alt="...">

							<a href="details/cart"><button type="button"
									class="btn btn-outline-primary btnn"">
									<i class="fa-solid fa-cart-plus"></i> Thêm Vào Giỏ Hàng
								</button></a> <a href="details/cart/pay"><button type="button"
									class="btn btn-primary btnn">Mua Ngay</button></a>
						</div>
						<div class="col-md-7">
							<h4>Công Bằng Trên Đời Là Do Bạn Nỗ Lực Giành Lấy</h4>
							<br> <span class="nccap">Nhà cung cấp:
								<h6 class="nccap">Công Ty Cổ Phần Time Books</h6>
							</span><br> <span class="nccap">Nhà Xuất bản:
								<h6 class="nccap">Dân Trí</h6>
							</span> <br>
							<hr>
							<br>
							<p>
								Giá: <strong class="text-danger small">89.000 VNĐ</strong> <br>
								Giá cũ: <em class="text-decoration-line-through">(100.000
									VNĐ)</em>
							</p>
							<span class="nccap" style="margin-right: 35px;">Thời gian
								giao hàng </span> <span class="nccap">Giao hàng đến:
								<h6 class="nccap" style="margin-right: 10px;">Phường Lê
									Bình, Quận Cái Răng, Cần Thơ</h6>
							</span> <span><a href="#"
								class="link-primary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover nccap"
								data-bs-toggle="modal" data-bs-target="#staticBackdrop">Thay
									đổi</a></span> <br> <span class="nccap" style="margin-left: 23%;">Dự
								kiến giao hàng
								<h6 class="nccap">Thứ sáu, 24/05</h6>
							</span> <br>
							<hr>
							<span for="customRange2" class="form-label">
								<h6>Số Lượng:</h6>
								<span id="rangeValue">5</span> <input type="range"
								class="form-range" min="0" max="10" id="customRange2">
							</span>
						</div>
					</div>
				</div>
				<!-- 		</div> -->

				<!-- 					mô tải sản phẩm -->
				<!-- 		<div class="card" style="margin-top: 10px;"> -->
				<h5>THÔNG TIN SẢN PHẨM</h5>
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-3">
							<p>Mã Sản Phẩm:</p>
							<p>Nhà Cung Cấp:</p>
							<p>Nhà Xuất Bản:</p>
							<p>Tác Giả:</p>
							<p>Năm Xuất Bản:</p>
							<p>Ngôn Ngữ:</p>
							<p>Trọng Lượng:</p>
							<p>Kích Thước:</p>
							<p>Số Trang:</p>
						</div>
						<!-- 				thông tin -->
						<div class="col-md-9">
							<p>9786044011912</p>
							<p>Công Ty Cổ Phần Time Books</p>
							<p>Dân Trí</p>
							<p>Mao Mao Trùng Tiểu Thư</p>
							<p>2024</p>
							<p>Tiếng Việt</p>
							<p>300</p>
							<p>20 x 13 x 1.4 cm</p>
							<p>280</p>
						</div>
						<!-- 				</div> -->
						<br> <span>Giá sản phẩm trên Fahasa.com đã bao gồm
							thuế theo luật hiện hành. Bên cạnh đó, tuỳ vào loại sản phẩm,
							hình thức và địa chỉ giao hàng mà có thể phát sinh thêm chi phí
							khác như Phụ phí đóng gói, phí vận chuyển, phụ phí hàng cồng
							kềnh,...</span>
						<hr>
						<span class="d-none" id="extraListMota">
							<p>
								<strong>Công Bằng Trên Đời Là Do Bạn Nỗ Lực Giành Lấy</strong>
							</p>
							<h6>GIỚI THIỆU TÁC GIẢ</h6> <span>Mao Mao Trùng Tiểu Thư:
								Tên thật là Trang Lệ Cúc, là một tác giả trẻ tiêu biểu với hàng
								vạn người hâm mộ cùng nhiều bài viết nổi tiếng được đăng báo chỉ
								sau một năm viết lách. Bằng kinh nghiệm phong phú và một trái
								tim ấm áp, những câu chuyện của cô đã khơi dậy động lực và
								truyền cảm hứng cho nhiều bạn trẻ.</span>

							<h6>GIỚI THIỆU TÁC PHẨM</h6>
							<p>"CÔNG BẰNG TRÊN ĐỜI LÀ DO BẠN NỖ LỰC GIÀNH LẤY"</p> <span>Đôi
								khi chúng ta đều tự hỏi, tại sao cứ phải liều mạng, thảnh thơi
								sống qua ngày cũng là sống mà nhỉ? Song, chỉ cần nghĩ thế giới
								này to lớn vô cùng, phong cảnh muôn màu muôn sắc, bạn sẽ không
								cam lòng.</span><br> <span>Vậy rốt cuộc ý nghĩa của nỗ lực
								là gì?</span><br> <span>Nỗ lực là mỗi ngày đều trôi qua một
								cách rực rỡ và đặc biệt, chứ không phải lặp lại một ngày mấy
								chục nghìn lần.</span>
						</span>
						<div class="d-flex justify-content-center">
							<button id="showMoreMota" type="button"
								class="btn d-flex justify-content-center">Xem Thêm
								Thông Tin</button>
							<button id="collapseBtn" type="button"
								class="btn d-flex justify-content-center d-none">Thu
								Gọn</button>
						</div>
					</div>
				</div>
				<br>
				<hr>
				<!-- 		<div class="card"> -->
				<h5>SẢN PHẨM GỢI Ý</h5>
				<hr>
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-3">
							<a href="product/details" class="a-product text-decoration-none">
								<div class="h-100 border-0 shadow-sm hover-shadow-lg"
									style="display: block;">
									<img src="/images/sach4.jpg" class="card-img-top" alt="..."
										style="height: 190px; width: 180px; padding: 5px 20px 5px 20px; margin-left: 50px;">
									<div class="">
										<span
											style="font-size: medium; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; max-width: 100%;">Chưa
											Kịp Lớn Đã Phải Trưởng Thành - Quyển 2 - Phiên Bản Song Ngữ
											Việt-Trung</span>
										<div class="d-flex flex-column">
											<p>
												Giá: <strong class="text-danger small">89.000 VNĐ</strong> <br>
												Giá cũ: <em class="text-decoration-line-through">(100.000
													VNĐ)</em>
											</p>
										</div>
									</div>
								</div>
							</a>
						</div>
						<!-- sp1 -->
						<div class="col-md-3">
							<a href="product/details" class="a-product text-decoration-none">
								<div class="h-100 border-0 shadow-sm hover-shadow-lg"
									style="display: block;">
									<img src="/images/sach4.jpg" class="card-img-top" alt="..."
										style="height: 190px; width: 180px; padding: 5px 20px 5px 20px; margin-left: 50px;">
									<div class="">
										<span
											style="font-size: medium; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; max-width: 100%;">Chưa
											Kịp Lớn Đã Phải Trưởng Thành - Quyển 2 - Phiên Bản Song Ngữ
											Việt-Trung</span>
										<div class="d-flex flex-column">
											<p>
												Giá: <strong class="text-danger small">89.000 VNĐ</strong> <br>
												Giá cũ: <em class="text-decoration-line-through">(100.000
													VNĐ)</em>
											</p>
										</div>
									</div>
								</div>
							</a>
						</div>
						<!-- sp2 -->
						<div class="col-md-3">
							<a href="product/details" class="a-product text-decoration-none">
								<div class="h-100 border-0 shadow-sm hover-shadow-lg"
									style="display: block;">
									<img src="/images/sach4.jpg" class="card-img-top" alt="..."
										style="height: 190px; width: 180px; padding: 5px 20px 5px 20px; margin-left: 50px;">
									<div class="">
										<span
											style="font-size: medium; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; max-width: 100%;">Chưa
											Kịp Lớn Đã Phải Trưởng Thành - Quyển 2 - Phiên Bản Song Ngữ
											Việt-Trung</span>
										<div class="d-flex flex-column">
											<p>
												Giá: <strong class="text-danger small">89.000 VNĐ</strong> <br>
												Giá cũ: <em class="text-decoration-line-through">(100.000
													VNĐ)</em>
											</p>
										</div>
									</div>
								</div>
							</a>
						</div>
						<!-- sp4 -->
						<div class="col-md-3">
							<a href="product/details" class="a-product text-decoration-none">
								<div class="h-100 border-0 shadow-sm hover-shadow-lg"
									style="display: block;">
									<img src="/images/sach4.jpg" class="card-img-top" alt="..."
										style="height: 190px; width: 180px; padding: 5px 20px 5px 20px; margin-left: 50px;">
									<div class="">
										<span
											style="font-size: medium; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; max-width: 100%;">Chưa
											Kịp Lớn Đã Phải Trưởng Thành - Quyển 2 - Phiên Bản Song Ngữ
											Việt-Trung</span>
										<div class="d-flex flex-column">
											<p>
												Giá: <strong class="text-danger small">89.000 VNĐ</strong> <br>
												Giá cũ: <em class="text-decoration-line-through">(100.000
													VNĐ)</em>
											</p>
										</div>
									</div>
								</div>
							</a>
						</div>

					</div>
				</div>
				<br>
			</div>
		</div>
	</div>
	</div>



	<footer>
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<p>
						Copyright 2024 <a href="#">QLBook</a> Store Co., Ltd. All rights
						reserved. &nbsp;&nbsp; Designed by <a title="HTML CSS Templates"
							rel="sponsored" href="#" target="_blank">Nhom2</a>
					</p>
				</div>
			</div>
		</div>
	</footer>
	<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="staticBackdropLabel">Chọn Địa
						Chỉ Giao Hàng</h1>
					<!-- 					<button type="button" class="btn-close" data-bs-dismiss="modal" -->
					<!-- 						aria-label="Close"></button> -->
				</div>
				<div class="modal-body">

					<div class="form-check">
						<div class="form-check form-switch">
							<input class="form-check-input" type="radio" role="switch"
								id="flexSwitchCheckDefault" name="radio" value="0"> <label
								class="form-check-label nccap" for="flexSwitchCheckDefault">Giao
								Hàng Đến: Phường Lê Bình, Quận Cái Răng, Cần Thơ </label>
						</div>
						<div class="form-check form-switch">
							<input class="form-check-input" type="radio" role="switch"
								id="flexSwitchCheckChecked" name="radio" value="1" checked>
							<label class="form-check-label nccap"
								for="flexSwitchCheckChecked">Giao Hàng Đến Địa Chỉ Khác</label><br>
							<span class="nccap">Tỉnh/Thành Phố:</span> <select
								class="form-select nccap" aria-label="Default select example">
								<option selected>Hà Nội</option>
								<option value="1">TP.Hồ Chí Minh</option>
								<option value="2">Cần Thơ</option>
								<option value="3">Bạc Liêu</option>
							</select> <span class="nccap">Quận/Huyện:</span> <select
								class="form-select nccap" aria-label="Default select example">
								<option value="4">Ninh Kiều</option>
								<option value="1">Cái Răng</option>
								<option value="2">Bình Thuỷ</option>
								<option value="3">Cờ Đỏ</option>
							</select> <span class="nccap">Phường/Xã:</span> <select
								class="form-select nccap" aria-label="Default select example">
								<option value="4">Lê Bình</option>
								<option value="1">Hưng Thạnh</option>
								<option value="2">Hưng Phú</option>
								<option value="3">Thường Thạnh</option>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Hủy</button>
					<button type="button" class="btn btn-primary">Xác Nhận</button>
				</div>
			</div>
		</div>


	</div>
	<!-- Scripts -->
	<script>
	const showMoreNCC = document.getElementById('showMoreMota');
	const showLessNCC = document.getElementById('collapseBtn');
	const extraListNCC = document.getElementById('extraListMota');
	
	showMoreNCC.addEventListener('click', () => {
		extraListNCC.classList.remove('d-none');
		  showMoreNCC.classList.add('d-none');
		  showLessNCC.classList.remove('d-none');
		});

	showLessNCC.addEventListener('click', () => {
		extraListNCC.classList.add('d-none');
		showMoreNCC.classList.remove('d-none');
		showLessNCC.classList.add('d-none');
		});
</script>
	<!-- Bootstrap core JavaScript -->
	<script src="/vendor/jquery/jquery.min.js"></script>
	<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>

	<script src="/assets/js/isotope.min.js"></script>
	<script src="/assets/js/owl-carousel.js"></script>

	<script src="/assets/js/tabs.js"></script>
	<script src="/assets/js/popup.js"></script>
	<script src="/assets/js/custom.js"></script>
</body>
</html>