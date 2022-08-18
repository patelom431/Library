package dev.om.library.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public Book() {

    }

    public Book(String isbn, String title, String author, String genre, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
    }

}
