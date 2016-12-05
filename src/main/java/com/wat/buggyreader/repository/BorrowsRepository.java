package com.wat.buggyreader.repository;

import com.wat.buggyreader.domain.Borrows;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Borrows entity.
 */
@SuppressWarnings("unused")
public interface BorrowsRepository extends MongoRepository<Borrows,String> {

}
