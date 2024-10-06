package com.poly.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Exception.ResourceNotFoundException;
import com.poly.Model.Invoice;
import com.poly.Model.User;
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
        invoice.setStatus(invoiceDetails.getStatus());
        invoice.setPaymentMethod(invoiceDetails.getPaymentMethod());
        invoice.setShipping(invoiceDetails.getShipping());
        invoice.setDiscount(invoiceDetails.getDiscount());

        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(int id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id " + id));

        invoiceRepository.delete(invoice);
    }

    // Lấy danh sách tất cả hoá đơn
    public List<Invoice> getInvoice() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoicesByUserId(Integer userId) {
        return invoiceRepository.findByUser_UsersId(userId);
    }

    public Invoice getInvoiceById(Integer invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id " + invoiceId));
    }
}