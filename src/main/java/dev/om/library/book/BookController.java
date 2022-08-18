package dev.om.library.book;

import dev.om.library.checkout.Checkout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/book/{isbn}")
    public Book getBook(@PathVariable String isbn) {
        return bookService.getBook(isbn);
    }

    @PostMapping
    public void createBook(@RequestHeader("Authorization") String authorization, @Valid @RequestBody Book book) {
        bookService.createBook(authorization, book);
    }

    @PostMapping("/book/{isbn}/checkout")
    public Checkout checkoutBook(@RequestHeader("Authorization") String authorization, @PathVariable String isbn) {
        return bookService.checkoutBook(authorization, isbn);
    }

    @PutMapping("/book/{checkoutID}/renew")
    public Checkout renewBook(@RequestHeader("Authorization") String authorization, @PathVariable UUID checkoutID) {
        return bookService.renewBook(authorization, checkoutID);
    }

    @PutMapping("/book/{checkoutID}/return")
    public void returnBook(@RequestHeader("Authorization") String authorization, @PathVariable UUID checkoutID) {
        bookService.returnBook(authorization, checkoutID);
    }

}
