package com.wat.buggyreader.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wat.buggyreader.domain.Borrows;

import com.wat.buggyreader.repository.BorrowsRepository;
import com.wat.buggyreader.web.rest.util.HeaderUtil;
import com.wat.buggyreader.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Borrows.
 */
@RestController
@RequestMapping("/api")
public class BorrowsResource {

    private final Logger log = LoggerFactory.getLogger(BorrowsResource.class);
        
    @Inject
    private BorrowsRepository borrowsRepository;

    /**
     * POST  /borrows : Create a new borrows.
     *
     * @param borrows the borrows to create
     * @return the ResponseEntity with status 201 (Created) and with body the new borrows, or with status 400 (Bad Request) if the borrows has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/borrows",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Borrows> createBorrows(@RequestBody Borrows borrows) throws URISyntaxException {
        log.debug("REST request to save Borrows : {}", borrows);
        if (borrows.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("borrows", "idexists", "A new borrows cannot already have an ID")).body(null);
        }
        Borrows result = borrowsRepository.save(borrows);
        return ResponseEntity.created(new URI("/api/borrows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("borrows", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /borrows : Updates an existing borrows.
     *
     * @param borrows the borrows to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated borrows,
     * or with status 400 (Bad Request) if the borrows is not valid,
     * or with status 500 (Internal Server Error) if the borrows couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/borrows",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Borrows> updateBorrows(@RequestBody Borrows borrows) throws URISyntaxException {
        log.debug("REST request to update Borrows : {}", borrows);
        if (borrows.getId() == null) {
            return createBorrows(borrows);
        }
        Borrows result = borrowsRepository.save(borrows);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("borrows", borrows.getId().toString()))
            .body(result);
    }

    /**
     * GET  /borrows : get all the borrows.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of borrows in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/borrows",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Borrows>> getAllBorrows(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Borrows");
        Page<Borrows> page = borrowsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/borrows");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /borrows/:id : get the "id" borrows.
     *
     * @param id the id of the borrows to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the borrows, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/borrows/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Borrows> getBorrows(@PathVariable String id) {
        log.debug("REST request to get Borrows : {}", id);
        Borrows borrows = borrowsRepository.findOne(id);
        return Optional.ofNullable(borrows)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /borrows/:id : delete the "id" borrows.
     *
     * @param id the id of the borrows to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/borrows/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBorrows(@PathVariable String id) {
        log.debug("REST request to delete Borrows : {}", id);
        borrowsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("borrows", id.toString())).build();
    }

}
