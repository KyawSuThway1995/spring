package com.shopping.app.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String author;
	private LocalDate publishedDate;
	private int price;

	public Book(String name, String author, LocalDate publishedDate, int price) {
		super();
		this.name = name;
		this.author = author;
		this.publishedDate = publishedDate;
		this.price = price;
	}

}
