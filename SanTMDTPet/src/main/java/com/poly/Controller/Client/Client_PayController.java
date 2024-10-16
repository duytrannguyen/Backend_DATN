package com.poly.Controller.Client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.Model.Invoice;
import com.poly.Service.InvoiceService;
import com.poly.dto.InvoiceDTO;

@RestController
@RequestMapping("/api/pet/invoices")
public class Client_PayController {

    @Autowired
    private InvoiceService invoiceService;

    // Thêm mới hóa đơn ( DÙNG CHO KHI THANH TOÁN)
    @PostMapping("/pay")
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        // Lấy userId từ invoiceDTO
        Integer userId = invoiceDTO.getUsersId();
        // Gọi dịch vụ để tạo hóa đơn từ giỏ hàng
        InvoiceDTO savedInvoice = invoiceService.createInvoiceFromCart(userId, invoiceDTO);
        // Trả về hóa đơn đã lưu
        return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
    }

    // lọc hoá đơn theo trạng thái hoá đơn của từng user -- ( DÙNG CHO HIỂN THỊ CÁC
    // TRẠNG THÁI CỦA USER)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByUserIdAndStatuses(
            @PathVariable int userId,
            @RequestParam List<String> StatusName) {

        List<InvoiceDTO> filteredInvoices = invoiceService.getInvoicesByUserIdAndStatusName(userId, StatusName);

        if (!filteredInvoices.isEmpty()) {
            return new ResponseEntity<>(filteredInvoices, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
