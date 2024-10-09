package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.model.Invoice;
import com.poly.model.OrderStatus;
import com.poly.model.PaymentMethod;
import com.poly.repository.InvoiceRepository;
import com.poly.service.InvoiceService;
import com.poly.service.PaymentMethodService;
import com.poly.service.StatusService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/payment")
public class Admin_PaymentController {

	@Autowired
	private ServletContext context;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private StatusService statusService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@GetMapping("/list")
	public String listInvoices(HttpServletRequest req, HttpServletResponse resp, Model model,
	        @RequestParam(name = "key", required = false, defaultValue = "") String key,
	        @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
	        @RequestParam(name = "status", required = false, defaultValue = "ALL") String status) {

	    int pageSize = 10;
	    Sort sort = Sort.by(Sort.Direction.DESC, "invoiceId");
	    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

	    Page<Invoice> page = invoiceService.findInvoicesByStatusAndKey(status, key, pageable);

	    List<OrderStatus> statuses = statusService.findAll();
	    List<PaymentMethod> paymentMethods = paymentMethodService.findAll();

	    model.addAttribute("invoices", page.getContent());
	    model.addAttribute("pageNo", pageNo);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("key", key);
	    model.addAttribute("status", status);
	    model.addAttribute("statuses", statuses);
	    model.addAttribute("paymentMethods", paymentMethods);

	    req.setAttribute("view", "/admin/QuanLyHoaDon/Payment/Payment.html");
	    return "indexAdmin";
	}
	@GetMapping("/edit/{invoiceId}")
	public String edit(HttpServletRequest req, Model model, @PathVariable("invoiceId") Integer invoiceId) {
	    // Tìm hóa đơn theo ID
	    Invoice invoice = invoiceService.findByInvoice(invoiceId);
	    if (invoice == null) {
	        throw new IllegalArgumentException("Không tìm thấy hóa đơn");
	    }

	    // Lấy danh sách trạng thái đơn hàng và phương thức thanh toán
	    List<OrderStatus> statuses = statusService.findAll();
	    List<PaymentMethod> paymentMethods = paymentMethodService.findAll();

	    // Thêm hóa đơn, trạng thái và phương thức thanh toán vào model
	    model.addAttribute("invoice", invoice);
	    model.addAttribute("statuses", statuses);
	    model.addAttribute("paymentMethods", paymentMethods);

	    // Đặt view cho trang chỉnh sửa hóa đơn
	    req.setAttribute("view", "/admin/QuanLyHoaDon/Payment/editPayment.html");
	    return "indexAdmin";
	}

	@PostMapping("/update/{invoiceId}")
	public String updateInvoice(@Valid @ModelAttribute("invoice") Invoice invoice, BindingResult errors,
	                            @PathVariable("invoiceId") Integer invoiceId, Model model, HttpServletRequest req,
	                            RedirectAttributes redirectAttributes) {

	    if (errors.hasErrors()) {
	        // Nếu có lỗi, nạp lại danh sách trạng thái và phương thức thanh toán và quay lại trang chỉnh sửa
	        List<OrderStatus> statuses = statusService.findAll();
	        List<PaymentMethod> paymentMethods = paymentMethodService.findAll();

	        model.addAttribute("statuses", statuses);
	        model.addAttribute("paymentMethods", paymentMethods);
	        req.setAttribute("view", "/admin/QuanLyHoaDon/Payment/editPayment.html");
	        return "indexAdmin";
	    }

	    // Tìm và cập nhật trạng thái đơn hàng
	    OrderStatus orderStatus = statusService.findById(invoice.getStatus().getStatusId())
	            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy trạng thái thanh toán"));
	    invoice.setStatus(orderStatus);

	    // Tìm và cập nhật phương thức thanh toán
	    PaymentMethod paymentMethod = paymentMethodService.findById(invoice.getPaymentMethod().getPaymentMethodId())
	            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phương thức thanh toán"));
	    invoice.setPaymentMethod(paymentMethod);

	    // Lưu hóa đơn với trạng thái và phương thức thanh toán mới
	    invoiceService.save(invoice);

	    // Chuyển hướng quay lại trang chỉnh sửa hóa đơn sau khi cập nhật thành công
	    redirectAttributes.addFlashAttribute("message", "Cập nhật hóa đơn thành công!");
	    return "redirect:/admin/payment/list";
	    
	}


}
