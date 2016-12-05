package com.wat.buggyreader.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Borrows.
 */

@Document(collection = "borrows")
public class Borrows implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("book_id")
    private String book_id;

    @Field("user_id")
    private String user_id;

    @Field("date_from")
    private LocalDate date_from;

    @Field("date_to")
    private LocalDate date_to;

    @Field("is_activated")
    private Boolean is_activated;

    @Field("is_waiting")
    private Boolean is_waiting;

    @Field("price")
    private Integer price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_id() {
        return book_id;
    }

    public Borrows book_id(String book_id) {
        this.book_id = book_id;
        return this;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public Borrows user_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDate getDate_from() {
        return date_from;
    }

    public Borrows date_from(LocalDate date_from) {
        this.date_from = date_from;
        return this;
    }

    public void setDate_from(LocalDate date_from) {
        this.date_from = date_from;
    }

    public LocalDate getDate_to() {
        return date_to;
    }

    public Borrows date_to(LocalDate date_to) {
        this.date_to = date_to;
        return this;
    }

    public void setDate_to(LocalDate date_to) {
        this.date_to = date_to;
    }

    public Boolean isIs_activated() {
        return is_activated;
    }

    public Borrows is_activated(Boolean is_activated) {
        this.is_activated = is_activated;
        return this;
    }

    public void setIs_activated(Boolean is_activated) {
        this.is_activated = is_activated;
    }

    public Boolean isIs_waiting() {
        return is_waiting;
    }

    public Borrows is_waiting(Boolean is_waiting) {
        this.is_waiting = is_waiting;
        return this;
    }

    public void setIs_waiting(Boolean is_waiting) {
        this.is_waiting = is_waiting;
    }

    public Integer getPrice() {
        return price;
    }

    public Borrows price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Borrows borrows = (Borrows) o;
        if(borrows.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, borrows.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Borrows{" +
            "id=" + id +
            ", book_id='" + book_id + "'" +
            ", user_id='" + user_id + "'" +
            ", date_from='" + date_from + "'" +
            ", date_to='" + date_to + "'" +
            ", is_activated='" + is_activated + "'" +
            ", is_waiting='" + is_waiting + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
