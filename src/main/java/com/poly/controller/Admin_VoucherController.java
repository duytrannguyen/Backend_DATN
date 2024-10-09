package com.poly.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.poly.model.Discount;
import com.poly.model.DiscountType;
import com.poly.repository.DiscountRepositopy;
import com.poly.repository.DiscountTypeTRepository;
import com.poly.service.CartService;
import com.poly.service.DiscountService;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/voucher")
public class Admin_VoucherController {
//	@Autowired
//	VoucherRepository voucherRepository;
	@Autowired
	DiscountRepositopy discountRepositopy;

	@Autowired
	DiscountTypeTRepository discountTypeRepository;
	@Autowired
	ServletContext context;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@GetMapping("/list")
	public String listVoucher(HttpServletRequest req, HttpServletResponse resp, Model model,
	        @RequestParam(name = "key", required = false, defaultValue = "") String key,
	        @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
	        @RequestParam(name = "discountTypeId", required = false) Integer discountTypeId) {
	    // Lấy danh sách các loại giảm giá
	    List<DiscountType> discountTypes = discountTypeRepository.findAll();
	    model.addAttribute("discountTypes", discountTypes);
	    
	    // Thiết lập phân trang
	    int pageSize = 10; // Kích thước trang
	    Sort sort = Sort.by(Direction.DESC, "discountId");
	    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
	    
	    Page<Discount> page;
	    
	    // Kiểm tra và lấy danh sách voucher với trạng thái ON (1) và các điều kiện lọc khác
	    if (StringUtils.isNotBlank(key)) {
	        page = discountRepositopy.findByDiscountCodeAndStatusId(key, 1, pageable);
	    } else if (discountTypeId != null) {
	        page = discountRepositopy.findByDiscountTypeIdAndStatusId(discountTypeId, 1, pageable);
	    } else {
	        page = discountRepositopy.findByStatusId(1, pageable);
	    }
	    
	    List<Discount> vouchers = page.getContent();
	    
	    if (vouchers.isEmpty()) {
	        model.addAttribute("noVouchersFound", true);
	    } else {
	        model.addAttribute("vouchers", vouchers);
	    }
	    
	    // Thêm thông tin phân trang vào model
	    model.addAttribute("pageNo", pageNo);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("key", key);
	    model.addAttribute("selectedDiscountTypeId", discountTypeId); // Thêm thông tin để Thymeleaf biết loại giảm giá nào được chọn
	    
	    // Đặt view cho giao diện
	    req.setAttribute("view", "/admin/QuanLyVoucher/Voucher/Voucher.html");
	    return "indexAdmin";
	}



	@GetMapping("/add")
	public String addProduct(@ModelAttribute("vc") Discount vc,HttpServletRequest req, Model model) {
	    // Lấy danh sách loại giảm giá từ cơ sở dữ liệu
	    List<DiscountType> discountTypes = discountTypeRepository.findAll();
	    model.addAttribute("discountType", discountTypes); 
	    
	    // Tạo mã xác thực ngẫu nhiên
	    String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    Random random = new Random();
	    StringBuilder verificationCode = new StringBuilder();
	    for (int i = 0; i < 10; i++) {
	        int index = random.nextInt(chars.length());
	        verificationCode.append(chars.charAt(index));
	    }
	    String result = verificationCode.toString();
	   // model.addAttribute("verificationCode", result);
	 // Gán mã xác thực cho thuộc tính mô hình
	    vc.setDiscountCode(result);
	    req.setAttribute("view", "/admin/QuanLyVoucher/Voucher/addVoucher.html");
	    return "indexAdmin";
	}

	@PostMapping("/create")
	public String createVoucher(@Valid @ModelAttribute("vc") Discount vc, BindingResult errors, Model model,
	        HttpServletRequest req, @RequestParam("discountType") Integer discountTypeId, RedirectAttributes redirectAttributes) {
	    // Lấy danh sách loại giảm giá từ cơ sở dữ liệu
	    List<DiscountType> discountTypes = discountTypeRepository.findAll();
	    model.addAttribute("discountType", discountTypes);

	    // Kiểm tra lỗi của đối tượng Voucher
	    if (errors.hasErrors()) {
	        // Nếu có lỗi, hiển thị lại trang thêm voucher với thông tin lỗi
	        req.setAttribute("view", "/admin/QuanLyVoucher/Voucher/addVoucher.html");
	        return "indexAdmin";
	    }

	    // Kiểm tra ngày bắt đầu
	    Date currentDate = new Date(); // Ngày hiện tại
	    if (vc.getStartDate().after(currentDate)) {
	        vc.setStatusId(4); // Đặt trạng thái voucher là OFF (2) nếu ngày bắt đầu sau ngày hiện tại
	        redirectAttributes.addFlashAttribute("message", "Voucher đã được thêm vào lưu trữ chờ phát hành!");
	    } else {
	        vc.setStatusId(1); // Đặt trạng thái voucher là ON (1) nếu ngày bắt đầu là ngày hiện tại hoặc trước ngày hiện tại
	    }
		// Kiểm tra ngày bắt đầu không được trước ngày hiện tại
		if (vc.getStartDate().before(currentDate)) {
			errors.rejectValue("startDate", "error.startDate", "Ngày bắt đầu phải sau ngày hiện tại.");
			req.setAttribute("view", "/admin/QuanLyVoucher/Voucher/addVoucher.html");
			return "indexAdmin"; // Trả về trang form để hiển thị lỗi
		}
		// Validate startDate và endDate
		if (!vc.isValid()) {
			errors.rejectValue("endDate", "error.endDate", "Ngày kết thúc phải sau ngày bắt đầu");
			req.setAttribute("view", "/admin/QuanLyVoucher/Voucher/addVoucher.html");
			return "indexAdmin"; // Trả về trang form để hiển thị lỗi
		}
	    // Xử lý logic khi không có lỗi
	    DiscountType discountType = discountTypeRepository.findById(discountTypeId).orElse(null);
	    if (discountType != null) {
	        vc.setDiscountType(discountType);
	        discountRepositopy.saveAndFlush(vc);
	    }
	    if (vc.getStatusId() == 1) {
	        redirectAttributes.addFlashAttribute("message", "Voucher đã được thêm thành công!");
	    }
	    return "redirect:/admin/voucher/list";
	}

	@GetMapping("/edit/{discountId}")
	public String editVoucher(HttpServletRequest req, Model model, @PathVariable("discountId") Integer discountId) {
	    Discount voucher = discountRepositopy.findById(discountId)
	            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher"));
	    List<DiscountType> discountTypes = discountTypeRepository.findAll();
	    model.addAttribute("discountTypes", discountTypes);
	    model.addAttribute("vc", voucher); // Gán đối tượng voucher vào "vc"
	    req.setAttribute("view", "/admin/QuanLyVoucher/Voucher/editVoucher.html");
	    return "indexAdmin";
	}


	@PostMapping("/update/{discountId}")
	public String updateVoucher(@Valid @ModelAttribute("vc") Discount vc, BindingResult errors, 
	                             @PathVariable("discountId") Integer discountId, Model model, 
	                             RedirectAttributes redirectAttributes, HttpServletRequest req) {
	    // Kiểm tra lỗi của đối tượng Voucher
	    if (errors.hasErrors()) {
	        // Nếu có lỗi, hiển thị lại trang thêm voucher với thông tin lỗi
	        req.setAttribute("view", "/admin/QuanLyVoucher/Voucher/editVoucher.html");
	        return "indexAdmin";
	    }
		// Validate startDate và endDate
		if (!vc.isValid()) {
			errors.rejectValue("endDate", "error.endDate", "Ngày kết thúc phải sau ngày bắt đầu");
			req.setAttribute("view", "/admin/QuanLyVoucher/Voucher/editVoucher.html");
			return "indexAdmin"; // Trả về trang form để hiển thị lỗi
		}
	    // Gán DiscountType cho voucher
	    DiscountType discountType = discountTypeRepository.findById(vc.getDiscountType().getDiscountTypeId())
	            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại voucher"));
	    vc.setDiscountType(discountType);
	    // Cập nhật voucher
	    vc.setStatusId(1);
	    discountRepositopy.saveAndFlush(vc);
	    // Thông báo thành công
	    redirectAttributes.addFlashAttribute("message", "Voucher đã được cập nhật thành công!");
	    return "redirect:/admin/voucher/list";
	}


	//luu tru
	@GetMapping("/storage/{discountId}")
	public String toggleStatus(@PathVariable("discountId") Integer discountId, RedirectAttributes redirectAttributes) {
	    // Tìm voucher theo discountId
	    Discount discount = discountRepositopy.findById(discountId)
	            .orElseThrow(() -> new EntityNotFoundException("Voucher not found"));

	    // Kiểm tra trạng thái hiện tại và cập nhật trạng thái mới
	    int currentStatus = discount.getStatusId();
	    if (currentStatus == 1 || currentStatus == 2) {
	        // Nếu trạng thái hiện tại là "on" (1), chuyển thành "off" (2) và lưu vào kho lưu trữ
	        int newStatus = (currentStatus == 1) ? 2 : currentStatus; // Nếu là "on", chuyển sang "off"; nếu là "off", không thay đổi
	        discount.setStatusId(newStatus);
	        discountRepositopy.save(discount);
	        redirectAttributes.addFlashAttribute("message", "Voucher đã được chuyển vào kho lưu trữ!");
	    } else {
	        // Thêm thông báo lỗi nếu trạng thái không hợp lệ
	        redirectAttributes.addFlashAttribute("message", "Cập nhật không thành công do trạng thái không hợp lệ!");
	    }
	    return "redirect:/admin/voucher/list"; // Chuyển hướng đến danh sách voucher
	}
	
	@GetMapping("/storage")
    public String listVoucherStorage(HttpServletRequest req, HttpServletResponse resp, Model model,
            @RequestParam(name = "key", required = false, defaultValue = "") String key,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "discountTypeId", required = false) List<Integer> discountTypeIds,
            @RequestParam(name = "status", required = false) List<Integer> statusList) {

        // Xử lý các giá trị mặc định nếu tham số không có
        if (statusList == null) {
            statusList = Collections.emptyList();
        }
        if (discountTypeIds == null) {
            discountTypeIds = Collections.emptyList();
        }

        // Lấy tất cả các loại giảm giá
        List<DiscountType> discountTypes = discountTypeRepository.findAll();
        model.addAttribute("discountTypes", discountTypes);

        // Thiết lập phân trang
        int pageSize = 10; // Kích thước trang
        Sort sort = Sort.by(Direction.DESC, "discountId");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        LocalDate currentDate = LocalDate.now();
        Page<Discount> page;

        // Thực hiện truy vấn dựa trên loại giảm giá và trạng thái
        if (!discountTypeIds.isEmpty()) {
            if (!statusList.isEmpty()) {
                page = discountRepositopy.findByDiscountTypeIdsAndStatuses(discountTypeIds, statusList, pageable);
            } else {
                page = discountRepositopy.findByDiscountTypeIdIn(discountTypeIds, pageable);
            }
        } else {
            if (!statusList.isEmpty()) {
                // Tìm kiếm dựa trên các trạng thái
                page = discountRepositopy.findByStatusIdIn(statusList, pageable);
            } else {
                page = discountRepositopy.findByStatusId(2, pageable); // Trạng thái mặc định
            }
        }

        // Lấy danh sách voucher và thêm vào model
        List<Discount> vouchers = page.getContent();
        if (vouchers.isEmpty()) {
            model.addAttribute("noVouchersFound", true);
        } else {
            model.addAttribute("vouchers", vouchers);
        }

        // Thêm các thông tin phân trang và các tham số tìm kiếm vào model
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("key", key);
        model.addAttribute("selectedDiscountTypeIds", discountTypeIds);
        model.addAttribute("statusList", statusList);

        // Chỉ định view để hiển thị
        req.setAttribute("view", "/admin/QuanLyVoucher/LuuTruVoucher/Storage.html");
        return "indexAdmin"; 
    }








}
