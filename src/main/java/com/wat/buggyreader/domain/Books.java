package com.wat.buggyreader.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Books.
 */

@Document(collection = "books")
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("ISBN")
    private String isbn;

    @Field("Book-Title")
    private String title;

    @Field("Book-Author")
    private String author;

    @Field("Year-Of-Publication")
    private Integer publication_year;

    @Field("Publisher")
    private String publisher;

    @Field("Image-URL-S")
    private String image_s;

    @Field("Image-URL-M")
    private String image_m;

    @Field("Image-URL-L")
    private String image_l;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public Books isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public Books title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public Books author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublication_year() {
        return publication_year;
    }

    public Books publication_year(Integer publication_year) {
        this.publication_year = publication_year;
        return this;
    }

    public void setPublication_year(Integer publication_year) {
        this.publication_year = publication_year;
    }

    public String getPublisher() {
        return publisher;
    }

    public Books publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage_s() {
        return image_s;
    }

    public Books image_s(String image_s) {
        this.image_s = image_s;
        return this;
    }

    public void setImage_s(String image_s) {
        this.image_s = image_s;
    }

    public String getImage_m() {
        return image_m;
    }

    public Books image_m(String image_m) {
        this.image_m = image_m;
        return this;
    }

    public void setImage_m(String image_m) {
        this.image_m = image_m;
    }

    public String getImage_l() {
        return image_l;
    }

    public Books image_l(String image_l) {
        this.image_l = image_l;
        return this;
    }

    public void setImage_l(String image_l) {
        this.image_l = image_l;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Books books = (Books) o;
        if(books.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, books.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Books{" +
            "id=" + id +
            ", isbn='" + isbn + "'" +
            ", title='" + title + "'" +
            ", author='" + author + "'" +
            ", publication_year='" + publication_year + "'" +
            ", publisher='" + publisher + "'" +
            ", image_s='" + image_s + "'" +
            ", image_m='" + image_m + "'" +
            ", image_l='" + image_l + "'" +
            '}';
    }
}
