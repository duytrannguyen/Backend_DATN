<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6 text-center">
				<h1>Welcome to Shop</h1>
				<h4 class="text-faded">Cùng Khám Phá Cuốn Sách Tiếp Theo</h4>
				<a href="/home/products" class="btn btn-primary rounded-pill">SHOP
					NOW</a>
			</div>
		</div>
	</div>
	<!---------------- Trending ---------------->
	<div class="container mt-5">
		<h6 class="text-center">Trending</h6>
		<h2 class="text-center">Shop by categories</h2>
		<div class="row align-items-center">
			<div class="col-md-3 text-center">
				<a aria-label="Visit product category General Interest Books"
					href="#"> <img src="../images/book_img1.jpg"
					alt="General Interest Books" class="fixed-size-image">
					<h2>Được Quan Tâm</h2>
				</a>
			</div>
			<div class="col-md-3 text-center">
				<a aria-label="Visit product category Fiction Books" href="/home/tieuthuyet"> <img
					src="../images/book_img1.jpg" alt="Fiction Books"
					class="fixed-size-image">
					<h2>Tiểu Thuyết</h2>
				</a>
			</div>
			<div class="col-md-3 text-center">
				<a aria-label="Visit product category Discounted Books" href="#">
					<img src="../images/book_img1.jpg" alt="Discounted Books"
					class="fixed-size-image">
					<h2>Sách Giảm Giá</h2>
				</a>
			</div>
			<div class="col-md-3 text-center">
				<a aria-label="Visit product category Bestsellers" href="#"> <img
					src="../images/book_img1.jpg" alt="Bestsellers"
					class="fixed-size-image">
					<h2>Bán Chạy Nhát</h2>
				</a>
			</div>
		</div>
	</div>


	<!---------------- Featured products------- -->

	<div class="container d-flex flex-column align-items-center mt-5">
		<div class="w-100 text-center mb-3">
			<h6 class="fade-in-up">Những Sản Phẩm Nổi Bật</h6>
		</div>
		<div class="w-100 text-center mb-5">
			<h2 class="fade-in-up">Đang Được Ưa Chuộng</h2>
		</div>
		<div class="row row-cols-1 row-cols-md-5 g-6">
			<c:forEach var="p" items="${products}">
				<div class="col-md-4">
					<!-- Đảm bảo đúng class Bootstrap cho cột -->
					<a href="products/details/${p.productId}"
						class="a-product text-decoration-none">
						<div class="card h-100 border-0 shadow-sm hover-shadow-lg">
							<img src="/Image_SP/${p.imageId.imageName}" class="card-img-top"
								style="height: 230px; width: 100%;">
							<!-- Đảm bảo hình ảnh chiếm hết chiều rộng cột -->
							<div class="card-body">
								<p class="card-title mb-1">${p.productName}</p>
								<div class="d-flex flex-column">
									<span class="giaBan">${p.price}đ</span>
									<!-- Thêm đơn vị tiền tệ -->
									<span class="giaGiam text-muted">120,000đ</span>
									<!-- Đây là giá giảm cố định, bạn có thể thay đổi tùy thuộc vào dữ liệu -->
								</div>
							</div>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>


		<!----------- Shop your way--------------->
		<div class="container mt-5">
			<div class="row text-center">
				<div class="col">
					<h6>Shop your way</h6>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<h2 class="text-center">Why choose us</h2>
				</div>
			</div>
		</div>

		<div class="container">
			<div class="row">
				<div class="col-md">
					<div class="card">
						<div class="card-body">
							<i class="bi bi-car-front-fill"></i>
							<h3 class="text-center">Nhiều Lựa Chọn</h3>
							<p>Chúng tôi cung cấp một bộ sưu tập sách phong phú, bao gồm
								các tiểu thuyết bán chạy nhất và sách được mọi người quan tâm,
								phù hợp với mọi độc giả.</p>
						</div>
					</div>
				</div>
				<div class="col-md">
					<div class="card">
						<div class="card-body">
							<i class="bi bi-credit-card-fill"></i>
							<h3 class="text-center">Giá Triết Khấu</h3>
							<p>Phần sách giảm giá của chúng tôi cung cấp mức giá tuyệt
								vời cho những cuốn sách phổ biến, vì vậy bạn có thể thưởng thức
								những cuốn sách yêu thích của mình mà không phải tốn nhiều tiền.</p>
						</div>
					</div>
				</div>
				<div class="col-md">
					<div class="card">
						<div class="card-body">
							<i class="bi bi-box-seam-fill"></i>
							<h3 class="text-center">Chuyển Phát Nhanh</h3>
							<p>Chúng tôi coi trọng thời gian của bạn, đó là lý do tại sao
								chúng tôi cung cấp dịch vụ vận chuyển nhanh chóng cho tất cả các
								đơn đặt hàng. Nhận sách của bạn được giao ngay trước cửa nhà bạn
								trong thời gian ngắn.</p>
						</div>
					</div>
				</div>
				<div class="col-md">
					<div class="card">
						<div class="card-body">
							<i class="bi bi-mailbox"></i>
							<h3 class="text-center">Đổi Trả Dễ Dàng</h3>
							<p>Chính sách hoàn trả của chúng tôi rất đơn giản, đảm bảo
								rằng nếu bạn không hoàn toàn hài lòng với giao dịch mua hàng của
								mình, việc trả lại hàng rất dễ dàng và không bị căng thẳng.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>