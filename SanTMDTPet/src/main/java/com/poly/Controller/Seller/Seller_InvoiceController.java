package com.poly.Controller.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.Model.OrderStatus;
import com.poly.Service.InvoiceService;
import com.poly.dto.InvoiceDTO;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*") // Cho phép tất cả các nguồn

@RestController
@RequestMapping("/api/pet/invoices")
public class Seller_InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Lấy danh sách tất cả hóa đơn
    @GetMapping("/listAll")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // Lấy một hóa đơn theo ID
    // @GetMapping("/invoice/{id}")
    // public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable int id) {
    // Optional<InvoiceDTO> invoice = invoiceService.getInvoiceById(id);
    // if (invoice.isPresent()) {
    // return new ResponseEntity<>(invoice.get(), HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }
    // lấy danh sách các đơn hàng theo seller ( DÙNG ĐỂ HIỂN THỊ DANH SÁCH CÁC ĐƠN
    // HÀNG KHÁCH ORDER TRONG QUẢN LÝ ĐƠN HÀNG)
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesBySellerIdAndStatusName(
            @PathVariable int sellerId,
            @RequestParam List<String> StatusName) {
        List<InvoiceDTO> filteredInvoices = invoiceService.getInvoicesBySellerIdAndStatusName(sellerId, StatusName);
        if (!filteredInvoices.isEmpty()) {
            return new ResponseEntity<>(filteredInvoices, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Cập nhật trạng thái đơn hàng ( DÙNG CHO CẬP NHẬT CÁC TRẠNG THÁI BÊN SELLER)
    @PutMapping("/update/{id}") // Đảm bảo đúng endpoint
    public ResponseEntity<OrderStatus> updateOrderStatus(
            @PathVariable Integer id,
            @RequestBody OrderStatus orderStatus) {
        try {
            OrderStatus updatedOrderStatus = invoiceService.updateOrderStatus(id, orderStatus);
            return new ResponseEntity<>(updatedOrderStatus, HttpStatus.OK); // Trả về trạng thái 200 OK và đối tượng đã
                                                                            // cập nhật
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Trả về trạng thái 404 NOT FOUND nếu có lỗi
        }
    }

    // Xóa hóa đơn theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable int id) {
        Optional<InvoiceDTO> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent()) {
            invoiceService.deleteInvoice(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
