package com.poly.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.model.Invoice;
import com.poly.repository.InvoiceRepository;
import com.poly.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Override
	public Double getTotalAmount() {
		return invoiceRepository.findTotalAmount();
	}

	@Override
	public Long getTotalOrders() {
		return invoiceRepository.countTotalOrders();
	}

	@Override
	public Invoice findByInvoice(Integer invoiceId) {
		return invoiceRepository.findById(invoiceId).orElse(null);
	}

	@Override
	public void save(Invoice invoice) {
		invoiceRepository.save(invoice);
	}

	@Override
	public List<Invoice> getAllInvoices() {
		return invoiceRepository.findAllInvoicesWithDetails();
	}

	@Override
	public Page<Invoice> findInvoicesByStatusAndKey(String status, String key, Pageable pageable) {
		return invoiceRepository.findByStatusAndKey(status, key, pageable);
	}
}
