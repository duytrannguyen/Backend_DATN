package com.poly.ScheduledTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.poly.model.Discount;
import com.poly.repository.DiscountRepositopy;

import java.time.LocalDate;
import java.util.List;

@Component
public class VoucherStatusUpdater {

    @Autowired
    private DiscountRepositopy discountRepository;

    @Scheduled(cron = "*/1 * * * * ?") // Chạy mỗi giây
    public void updateVoucherStatuses() {
        LocalDate currentDate = LocalDate.now();

        // Cập nhật trạng thái voucher thành 'Hết Hạn' nếu ngày kết thúc nhỏ hơn ngày hiện tại
        List<Discount> expiredVouchers = discountRepository.findByEndDateBeforeAndStatusId(currentDate);
        for (Discount voucher : expiredVouchers) {
            voucher.setStatusId(5); // Thay đổi trạng thái thành 'Hết Hạn'
        }
        discountRepository.saveAll(expiredVouchers);
        List<Discount> usedUpVouchers = discountRepository.findByQuantityLessThanEqual(0);
        for (Discount voucher : usedUpVouchers) {
            if (voucher.getStatusId() != 6) { // Chỉ cập nhật nếu trạng thái khác 'Đã Dùng Hết'
                voucher.setStatusId(6); // Xác nhận trạng thái 'Đã Dùng Hết'
                discountRepository.save(voucher);
            }
        }
        // Cập nhật trạng thái voucher thành 'Chờ Phát Hành' nếu ngày bắt đầu lớn hơn ngày hiện tại
        List<Discount> pendingVouchers = discountRepository.findByStartDateAfterAndStatusId(currentDate);
        for (Discount voucher : pendingVouchers) {
            voucher.setStatusId(4); // Thay đổi trạng thái thành 'Chờ Phát Hành'
        }
        discountRepository.saveAll(pendingVouchers);
        
     // Cập nhật trạng thái voucher thành 'On' nếu ngày bắt đầu bằng ngày hiện tại và số lượng lớn hơn 0
        List<Discount> nowVouchers = discountRepository.findByStartDateNowAndQuantityGreaterThanZero(currentDate);
        for (Discount voucher : nowVouchers) {
            voucher.setStatusId(1); // Thay đổi trạng thái thành 'On'
        }
        discountRepository.saveAll(nowVouchers);
    }
    
    
}
