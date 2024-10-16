package com.poly.Controller.Seller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.Model.Discount;

import com.poly.Reponsitory.DiscountRepository;
import com.poly.Reponsitory.DiscountStatusRepository;
import com.poly.Service.DiscountService;
import org.springframework.http.HttpStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("seller/voucher")
public class Seller_VoucherController {
    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    DiscountStatusRepository discountStatusRepository;

    @Autowired
    ServletContext context;
    @Autowired
    private DiscountService discountService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // Lấy tất cả voucher hoặc tìm kiếm theo key
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listVouchers(
            @RequestParam(name = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo) {

        int pageSize = 10; // Kích thước trang

        if (pageNo < 0) {
            return new ResponseEntity<>(Map.of("message", "Page number must be zero or greater."),
                    HttpStatus.BAD_REQUEST);
        }
        Page<Discount> page;
        if (searchKey != null && !searchKey.isEmpty()) {
            page = discountService.findVouchersByKey(searchKey, pageNo, pageSize);
        } else {
            page = discountService.findVouchersByStatusId(1, pageNo, pageSize); // Giả sử bạn muốn lấy các voucher có
                                                                                // trạng thái là 1
        }
        List<Discount> vouchers = page.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("vouchers", vouchers);
        response.put("pageNo", pageNo);
        response.put("totalPages", page.getTotalPages());
        response.put("searchKey", searchKey);
        if (vouchers.isEmpty()) {
            response.put("message", "No vouchers found.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/add")
    public String addProduct(@ModelAttribute("vc") Discount vc, HttpServletRequest req, Model model) {
        // Tạo mã xác thực ngẫu nhiên
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            verificationCode.append(chars.charAt(index));
        }
        String result = verificationCode.toString();
        // Gán mã xác thực cho thuộc tính mô hình
        vc.setDiscountCode(result);
        req.setAttribute("view", "/Seller/QuanLyVoucher/Voucher/addVoucher.html");
        return "indexSeller";
    }

    //
    @PostMapping("/create")
    public ResponseEntity<?> createVoucher(@Valid @RequestBody Discount vc, BindingResult errors) {
        // Kiểm tra lỗi validation của đối tượng Discount
        if (errors.hasErrors()) {
            // Trả về danh sách lỗi với chỉ message
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(Collections.singletonMap("messages", errorMessages));
        }
        try {
            // Gọi service để xử lý việc tạo voucher
            discountService.createVoucher(vc);
            return ResponseEntity.ok(Collections.singletonMap("message", "Voucher created successfully!"));
        } catch (IllegalArgumentException e) {
            // Xử lý khi có lỗi phát sinh và trả về message
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    //
    @GetMapping("/edit/{discountId}")
    public ResponseEntity<?> editVoucher(@PathVariable("discountId") Integer discountId) {
        // Tìm đối tượng voucher theo discountId
        Optional<Discount> optionalVoucher = discountRepository.findById(discountId);
        if (optionalVoucher.isPresent()) {
            // Nếu tìm thấy, trả về voucher
            return ResponseEntity.ok(optionalVoucher.get());
        } else {
            // Nếu không tìm thấy, trả về thông báo lỗi
            Map<String, String> response = new HashMap<>();
            response.put("message", "Không tìm thấy voucher với ID: " + discountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //
    @PutMapping("/update/{discountId}")
    public ResponseEntity<?> updateVoucher(@PathVariable("discountId") Integer discountId,
            @Valid @RequestBody Discount vc, BindingResult errors) {
        // Kiểm tra lỗi
        if (errors.hasErrors()) {
            // Tạo danh sách lỗi
            List<String> errorMessages = errors.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        // Gọi service để cập nhật voucher
        try {
            discountService.updateVoucher(discountId, vc, errors);
            return ResponseEntity.ok(Collections.singletonMap("message", "Voucher update successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Có lỗi xảy ra khi cập nhật voucher: " + e.getMessage());
        }
    }

    //
    // luu tru
    @GetMapping("/storage/{discountId}")
    public ResponseEntity<Map<String, String>> toggleStatus(@PathVariable("discountId") Integer discountId) {
        Map<String, String> response = new HashMap<>();
        try {
            // Gọi phương thức service để cập nhật trạng thái
            String message = discountService.toggleStatus(discountId);
            response.put("message", message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //
    // @GetMapping("/storage")
    // public String listVoucherStorage(HttpServletRequest req, HttpServletResponse
    // resp, Model model,
    // @RequestParam(name = "key", required = false, defaultValue = "") String key,
    // @RequestParam(name = "pageNo", required = false, defaultValue = "0") int
    // pageNo,
    // @RequestParam(name = "discountTypeId", required = false) List<Integer>
    // discountTypeIds,
    // @RequestParam(name = "status", required = false) List<Integer> statusList) {
    //
    //// Xử lý các giá trị mặc định nếu tham số không có
    // if (statusList == null) {
    // statusList = Collections.emptyList();
    // }
    // if (discountTypeIds == null) {
    // discountTypeIds = Collections.emptyList();
    // }
    //
    //// Lấy tất cả các loại giảm giá
    // List<DiscountType> discountTypes = discountTypeRepository.findAll();
    // model.addAttribute("discountTypes", discountTypes);
    //// Lấy danh sách các trạng thái
    // List<DiscountsStatus> discountStatus = discountStatusRepository.findAll();
    // model.addAttribute("discountStatus", discountStatus);
    //// Thiết lập phân trang
    // int pageSize = 10; // Kích thước trang
    // Sort sort = Sort.by(Direction.DESC, "discountId");
    // Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    // LocalDate currentDate = LocalDate.now();
    // Page<Discount> page;
    //
    //// Thực hiện truy vấn dựa trên loại giảm giá và trạng thái
    // if (!discountTypeIds.isEmpty()) {
    // if (!statusList.isEmpty()) {
    // page = discountRepositopy.findByDiscountTypeIdsAndStatuses(discountTypeIds,
    // statusList, pageable);
    // } else {
    // page = discountRepositopy.findByDiscountTypeIdIn(discountTypeIds, pageable);
    // }
    // } else {
    // if (!statusList.isEmpty()) {
    //// Tìm kiếm dựa trên các trạng thái
    // page = discountRepositopy.findByStatusIdIn(statusList, pageable);
    // } else {
    // page = discountRepositopy.findByStatusId(2, pageable); // Trạng thái mặc định
    // }
    // }
    //
    //// Lấy danh sách voucher và thêm vào model
    // List<Discount> vouchers = page.getContent();
    // if (vouchers.isEmpty()) {
    // model.addAttribute("noVouchersFound", true);
    // } else {
    // model.addAttribute("vouchers", vouchers);
    // }
    //
    //// Thêm các thông tin phân trang và các tham số tìm kiếm vào model
    // model.addAttribute("pageNo", pageNo);
    // model.addAttribute("totalPages", page.getTotalPages());
    // model.addAttribute("key", key);
    // model.addAttribute("selectedDiscountTypeIds", discountTypeIds);
    // model.addAttribute("statusList", statusList);
    //
    //// Chỉ định view để hiển thị
    // req.setAttribute("view", "/Seller/QuanLyVoucher/LuuTruVoucher/Storage.html");
    // return "indexSeller";
    // }
}
