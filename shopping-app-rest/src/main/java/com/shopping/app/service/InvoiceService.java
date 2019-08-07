package com.shopping.app.service;

import java.time.LocalDate;
import java.util.List;

import com.shopping.app.entity.Invoice;

public interface InvoiceService {

	void create(Invoice invoice);
	List<Invoice> findAll();
	Invoice findById(long id);
	void deleteById(long id);
	List<Invoice> search(String username, LocalDate startDate, LocalDate endDate);
}
