package com.poly.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.poly.model.Invoice;
import java.util.List;

public interface InvoiceService {
    Double getTotalAmount();
    Long getTotalOrders();
    Invoice findByInvoice(Integer invoiceId);
    void save(Invoice invoice);
    List<Invoice> getAllInvoices();
    Page<Invoice> findInvoicesByStatusAndKey(String status, String key, Pageable pageable);
}
