package com.example.demo.service;

import com.example.demo.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findOne(Long id);
}
