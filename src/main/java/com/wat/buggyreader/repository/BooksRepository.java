package com.wat.buggyreader.repository;

import com.wat.buggyreader.domain.Books;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Books entity.
 */
@SuppressWarnings("unused")
public interface BooksRepository extends MongoRepository<Books,String> {

}
