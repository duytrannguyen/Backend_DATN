package com.poly.Controller.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.Model.Invoice;
import com.poly.Service.InvoiceService;

@RestController
@RequestMapping("/api/pet/invoices")
public class Client_PayController {
	@Autowired
    private InvoiceService invoiceService;

	//Xem tất cả hoá đơn
	@GetMapping("/listAll")
	public ResponseEntity<List<Invoice>> getAllInvoices() {
	    List<Invoice> invoices = invoiceService.getInvoice();
	    return new ResponseEntity<>(invoices, HttpStatus.OK);
	}
	
    // Thêm mới hoá đơn
    @PostMapping("/add")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice newInvoice = invoiceService.addInvoice(invoice);
        return new ResponseEntity<>(newInvoice, HttpStatus.CREATED);
    }

    // Sửa hoá đơn
    @PutMapping("/update/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable int id, @RequestBody Invoice invoiceDetails) {
        Invoice updatedInvoice = invoiceService.updateInvoice(id, invoiceDetails);
        return new ResponseEntity<>(updatedInvoice, HttpStatus.OK);
    }

    // Xóa hoá đơn
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteInvoice(@PathVariable int id) {
        invoiceService.deleteInvoice(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
 // API lấy hóa đơn theo user_id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Invoice>> getInvoicesByUserId(@PathVariable int userId) {
        List<Invoice> invoices = invoiceService.getInvoicesByUserId(userId);
        if (invoices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Không có hóa đơn
        }
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

}
