<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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

<!-- <!-- link icon -->
<!-- <!-- FontAwesome 6.2.0 CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
	integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- <!-- (Optional) Use CSS or JS implementation -->
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
<div class="card">
    <div class="card-header">
        <h3>Cập Nhật Hóa Đơn</h3>
    </div>
    <form:form modelAttribute="invoice" action="/admin/payment/update/${invoice.invoiceId}" method="post">
        <hr>
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-6">
                    <!-- Các trường để chỉnh sửa hóa đơn -->
                    <div class="mb-3 row">
                        <label for="invoiceId" class="col-sm-5 col-form-label">Mã Hoá Đơn</label>
                        <div class="col-sm-7">
                            <span class="form-control border-bottom border-1 grey" id="invoiceId">${invoice.invoiceId}</span>
                            <input type="hidden" name="invoiceId" value="${invoice.invoiceId}">
                        </div>
                    </div>
                    <hr>
                    <div class="mb-3 row">
                        <label for="paymentDate" class="col-sm-5 col-form-label">Ngày Thanh Toán</label>
                        <div class="col-sm-7">
                            <form:input type="date" class="form-control border-bottom border-1 grey" id="paymentDate" path="paymentDate" value="${invoice.paymentDate}" />
                            <div class="text-danger col-form-label">
                                <form:errors path="paymentDate" />
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="mb-3 row">
                        <label for="username" class="col-sm-5 col-form-label">Khách Hàng</label>
                        <div class="col-sm-7">
                            <span class="form-control border-bottom border-1 grey" id="username">${invoice.cart.user.username}</span>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="col-md-6">
                    <div class="mb-3 row">
                        <label for="paymentStatus" class="col-sm-5 col-form-label">Trạng Thái Thanh Toán</label>
                        <div class="col-sm-7">
                            <select name="paymentStatus" class="form-control" id="paymentStatus">
                                <c:forEach var="status" items="${statuses}">
                                    <option value="${status.statusId}" ${status.statusId == invoice.status.statusId ? 'selected' : ''}>${status.statusName}</option>
                                </c:forEach>
                            </select>
                            <div class="text-danger col-form-label">
                                <form:errors path="paymentStatus" />
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="mb-3 row">
                        <label for="paymentMethod" class="col-sm-5 col-form-label">Hình Thức Thanh Toán</label>
                        <div class="col-sm-7">
                            <select name="paymentMethod" class="form-control" id="paymentMethod">
                                <c:forEach var="paymentMethod" items="${paymentMethods}">
                                    <option value="${paymentMethod.paymentMethodId}" ${paymentMethod.paymentMethodId == invoice.paymentMethod.paymentMethodId ? 'selected' : ''}>${paymentMethod.paymentMethodName}</option>
                                </c:forEach>
                            </select>
                            <div class="text-danger col-form-label">
                                <form:errors path="paymentMethod" />
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="mb-3 row">
                        <label for="totalAmount" class="col-sm-5 col-form-label">Tổng Tiền</label>
                        <div class="col-sm-7">
                            <span class="form-control border-bottom border-1 grey" id="totalAmount">${invoice.totalAmount}</span>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="col-md-12 text-end">
                    <button type="submit" class="btn btn-primary btn-sm">
                        <i class="fa fa-pencil-square-o"></i> Lưu Thay Đổi
                    </button>
                </div>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>
