package com.poly.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Page;
import com.poly.Model.Discount;
import com.poly.Reponsitory.DiscountRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    // Lấy tất cả voucher với phân trang
    public Page<Discount> findAllVouchers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "discountId"));
        return discountRepository.findAll(pageable);
    }

    // Tìm kiếm voucher theo mã hoặc tên
    public Page<Discount> findVouchersByKey(String key, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "discountId"));
        return discountRepository.findBydiscountCode(key, pageable);
    }

    // Tìm voucher theo statusId
    public Page<Discount> findVouchersByStatusId(int statusId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "discountId"));
        return discountRepository.findByStatusId(statusId, pageable);
    }

    // Tìm voucher theo danh sách statusId
    public Page<Discount> findVouchersByStatusIds(List<Integer> statusList, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "discountId"));
        return discountRepository.findByStatusIdIn(statusList, pageable);
    }

    // Lấy danh sách voucher hết hạn
    public List<Discount> findExpiredVouchers(LocalDate currentDate) {
        return discountRepository.findByEndDateBeforeAndStatusId(currentDate);
    }

    // Lấy danh sách voucher chờ phát hành
    public List<Discount> findPendingVouchers(LocalDate currentDate) {
        return discountRepository.findByStartDateAfterAndStatusId(currentDate);
    }

    // Lấy danh sách voucher phát hành hôm nay và còn số lượng
    public List<Discount> findVouchersStartingTodayWithQuantity(LocalDate currentDate) {
        return discountRepository.findByStartDateNowAndQuantityGreaterThanZero(currentDate);
    }

    // Lấy voucher với số lượng còn lại nhỏ hơn hoặc bằng 0
    public List<Discount> findVouchersByLowQuantity(int quantity) {
        return discountRepository.findByQuantityLessThanEqual(quantity);
    }

    // Tìm mã khuyến mãi theo mã khuyến mãi
    public Optional<Discount> findVoucherByDiscountCode(String discountCode) {
        return discountRepository.findByDiscountCode(discountCode);
    }

    // THÊM
    public void createVoucher(Discount vc) {
        Date currentDate = new Date();
        // Kiểm tra ngày bắt đầu không được trước ngày hiện tại
        if (vc.getStartDate().before(currentDate)) {
            throw new IllegalArgumentException("Ngày bắt đầu phải sau ngày hiện tại.");
        }
        // Kiểm tra ngày kết thúc phải sau ngày bắt đầu
        if (vc.getEndDate() != null && !vc.getEndDate().after(vc.getStartDate())) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu.");
        }
        // Xử lý trạng thái của voucher
        if (vc.getStartDate().after(currentDate)) {
            vc.setStatusId(2); // Đặt trạng thái OFF (2) nếu ngày bắt đầu sau ngày hiện tại
        } else {
            vc.setStatusId(1); // Đặt trạng thái ON (1) nếu ngày bắt đầu là ngày hiện tại hoặc trước
        }
        discountRepository.saveAndFlush(vc);
    }

    // SỬA
    public Discount updateVoucher(Integer discountId, Discount vc, BindingResult errors) {
        // Kiểm tra sự tồn tại của voucher
        Discount existingVoucher = discountRepository.findById(discountId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher với ID: " + discountId));
        // Lấy ngày hiện tại
        Date currentDate = new Date();
        // Kiểm tra nếu voucher không có startDate hoặc endDate
        if (vc.getStartDate() == null || vc.getEndDate() == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và ngày kết thúc không được để trống.");
        }
        // Kiểm tra ngày bắt đầu không được trước ngày hiện tại
        if (vc.getStartDate().before(currentDate)) {
            throw new IllegalArgumentException("Ngày bắt đầu phải sau ngày hiện tại.");
        }
        // Kiểm tra ngày kết thúc phải sau ngày bắt đầu
        if (!vc.getEndDate().after(vc.getStartDate())) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu.");
        }
        // Cập nhật thông tin voucher từ đối tượng đã gửi
        existingVoucher.setDiscountCode(vc.getDiscountCode());
        existingVoucher.setStartDate(vc.getStartDate());
        existingVoucher.setEndDate(vc.getEndDate());
        // Đặt giá trị cho statusId, đảm bảo không phải NULL
        if (vc.getStatusId() != null) {
            existingVoucher.setStatusId(vc.getStatusId()); // Cập nhật statusId từ đối tượng vc
        } else {
            // Nếu không có giá trị statusId, có thể đặt giá trị mặc định hoặc ném ngoại lệ
            throw new IllegalArgumentException("statusId không được để trống.");
        }

        existingVoucher.setQuantity(vc.getQuantity());

        // Lưu cập nhật vào cơ sở dữ liệu
        return discountRepository.save(existingVoucher); // Sử dụng save thay vì saveAndFlush
    }

    // XÓA
    public String toggleStatus(Integer discountId) {
        // Tìm voucher theo discountId
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy voucher!"));
        // Kiểm tra trạng thái hiện tại và cập nhật trạng thái mới
        int currentStatus = discount.getStatusId();
        if (currentStatus == 1 || currentStatus == 5) {
            // Nếu trạng thái hiện tại là "on" (1), chuyển thành "off" (5)
            int newStatus = (currentStatus == 1) ? 5 : currentStatus; // Nếu là "on", chuyển sang "off"
            discount.setStatusId(newStatus);
            discountRepository.save(discount);
            return "Voucher đã được chuyển vào kho lưu trữ!";
        } else {
            // Thêm thông báo lỗi nếu trạng thái không hợp lệ
            throw new IllegalArgumentException("Cập nhật không thành công do trạng thái không hợp lệ!");
        }
    }

}
