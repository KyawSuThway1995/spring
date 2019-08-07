package com.shopping.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.app.entity.Book;
import com.shopping.app.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository repo;

	@Override
	public void create(Book book) {
		repo.save(book);
	}

	@Override
	public List<Book> findAll() {
		return repo.findAll();
	}

	@Override
	public Book findById(long id) {
		return repo.getOne(id);
	}

	@Override
	public void deleteById(long id) {
		repo.deleteById(id);
	}

}
