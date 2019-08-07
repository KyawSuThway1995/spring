package com.shopping.app.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.app.entity.Invoice;
import com.shopping.app.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository repo;

	@Override
	public void create(Invoice invoice) {
		repo.save(invoice);
	}

	@Override
	@Transactional
	public List<Invoice> search(String name, LocalDate startDate, LocalDate endDate) {
		String sql = "select inv from Invoice inv where 1 = 1 ";
		StringBuilder sb = new StringBuilder(sql);
		Map<String, Object> map = new HashMap<>();

		if (null != name && !name.isEmpty()) {
			sb.append("and upper(inv.account.name) like :name ");
			map.put("name", name.concat("%").toUpperCase());
		}

		if (null != startDate) {
			sb.append("and inv.invoiceDate >= :startDate ");
			map.put("startDate", startDate);
		}

		if (null != endDate) {
			sb.append("and inv.invoiceDate <= :endDate ");
			map.put("endDate", endDate);
		}

		return repo.find(sb.toString(), map);
	}

	@Override
	public List<Invoice> findAll() {
		return repo.findAll();
	}

	@Override
	public Invoice findById(long id) {
		return repo.getOne(id);
	}

	@Override
	public void deleteById(long id) {
		repo.deleteById(id);
	}

}
