package com.shopping.app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Invoice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Account account;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "invoice")
	private List<OrderDetails> orderDetails;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate invoiceDate;
	private int subTotal;
	private int tax;
	private int total;
	
	@PrePersist
	public void init() {
		orderDetails.forEach(od -> od.setInvoice(this));
	}
}
