package com.shopping.app.service;

import java.util.List;

import com.shopping.app.entity.Book;

public interface BookService {
	void create(Book book);
	List<Book> findAll();
	Book findById(long id);
	void deleteById(long id);
}
