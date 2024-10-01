package com.poly.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Exception.ResourceNotFoundException;
import com.poly.Model.Invoice;
import com.poly.Reponsitory.InvoiceRepository;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice addInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(int id, Invoice invoiceDetails) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id " + id));
        
        invoice.setTotalAmount(invoiceDetails.getTotalAmount());
        invoice.setPaymentDate(invoiceDetails.getPaymentDate());
        invoice.setUser(invoiceDetails.getUser());
        invoice.setStatusId(invoiceDetails.getStatusId());
        invoice.setPaymentMethodId(invoiceDetails.getPaymentMethodId());
        invoice.setShippingId(invoiceDetails.getShippingId());
        invoice.setDiscountId(invoiceDetails.getDiscountId());
        
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(int id) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id " + id));
        
        invoiceRepository.delete(invoice);
    }
//Lấy danh sách tất cả hoá đơn
    public List<Invoice> getInvoice() {
        return invoiceRepository.findAll();
    }
 // Lấy danh sách hóa đơn theo user_id
    public List<Invoice> getInvoicesByUserId(Integer user) {
        return invoiceRepository.findByUser(user);
    }

}

