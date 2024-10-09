//package com.poly.configuration;
//
//public class mail {
//
//}
////gửi mail
//		try {
//			MimeMessage mimeMessage = sender.createMimeMessage();
//			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			mimeMessageHelper.setFrom("lyt8073@gmail.com");
//			mimeMessageHelper.setTo(user.getEmail());
//			mimeMessageHelper.setSubject("Xác nhận đơn hàng #" + invoice.getInvoiceId());
//			String addresses = user.getAddresses().stream().map(Address::toString).collect(Collectors.joining(", "));
//			String htmlContent = "<!DOCTYPE html>" + "<html lang='en'>" + "<head>" + "<meta charset='UTF-8'>"
//					+ "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
//					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH' crossorigin='anonymous'>"
//					+ "<title>Order Confirmation</title>" + "<style type='text/css'>"
//					+ "body { font-family: Arial, sans-serif; }" + ".container { margin-top: 50px; }"
//					+ ".card { padding: 20px; }" + ".card-header { background-color: #007bff; color: white; }"
//					+ ".card-body { margin-top: 20px; }" + ".text-center { text-align: center; }"
//					+ ".fw-bold { font-weight: bold; }" + ".text-primary { color: #007bff; }" + "</style>" + "</head>"
//					+ "<body>" + "<div class='container'>" + "<div class='card'>" + "<div class='card-header'>"
//					+ "<h4>Order Confirmation</h4>" + "</div>" + "<div class='card-body'>"
//					+ "<h5 class='text-center fw-bold text-primary'>Cảm Ơn Bạn Đã Đặt Hàng Tại QLBook</h5>"
//					+ "<div class='card'>" + "<p>Chào " + user.getFullName() + ",</p>"
//					+ "<span>QLBook đã nhận được yêu cầu đặt hàng của bạn và đang xử lý.</span>" + "</div><br>"
//					+ "<div class='card'>" + "<b>Đơn Hàng Được Giao đến</b> <br>" + "<div class='row'>"
//					+ "<div class='col-md-4'>" + "Tên: " + user.getFullName() + "<br>" + "Địa Chỉ: " + addresses
//					+ "<br>" + "Điện Thoại: " + user.getPhone() + "<br>" + "Email: " + user.getEmail() + "<br>"
//					+ "</div>" + "</div>" + "</div><br>" + "<div class='card'>" + "<div class='row'>"
//					+ "<div class='col-md-5'>" + "<p>Thành Tiền:</p>" + "<p>Phí Vận Chuyển:</p>" + "<p>Giảm Giá:</p>"
//					+ "<p>Tổng Cộng:</p>" + "</div>" + "<div class='col-md-3'>" + "<p>VNĐ</p>" + "<p>VNĐ</p>"
//					+ "<p>VNĐ</p>" + "<p>VNĐ</p>" + "</div>" + "<div class='col-md-3'>" + "<p>"
//					+ invoice.getTotalAmount() + "</p>" + "<p>" + shipping.getCOD() + "</p>" + "<p>"
//					+ invoice.getDiscount() + "</p>" + "<p>" + (invoice.getTotalAmount() + shipping.getCOD() - 0)
//					+ " VNĐ</p>" + "</div>" + "</div>" + "</div>" + "<p>Cảm ơn bạn đã mua hàng!</p>"
//					+ "<p>Thông tin đơn hàng đã được gửi vào email này.</p>"
//					+ "<p>Trân trọng,<br />Đội ngũ hỗ trợ của chúng tôi</p>"
//					+ "<p>*Vui lòng không trả lời email này!*</p>" + "</div>" + "</div>" + "</div>" + "</body>"
//					+ "</html>";
//
//			mimeMessageHelper.setText(htmlContent, true);
//			sender.send(mimeMessage);
//			System.out.println("Email đã được gửi đi thành công.");
//		} catch (Exception e) {
//			System.out.println("Đã xảy ra lỗi khi gửi email: " + e.getMessage());
//			e.printStackTrace();
//			// Bạn có thể thêm logic xử lý lỗi ở đây nếu cần
//		}