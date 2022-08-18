package dev.om.library.book;

import dev.om.library.checkout.Checkout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getBook(@PathVariable String isbn) {
        return bookService.getBook(isbn);
    }

    @PostMapping
    public void addBook(@RequestHeader("Authorization") String authorization, @Valid @RequestBody Book book) {
        bookService.addBook(authorization, book);
    }

    @PostMapping("/{isbn}/checkout")
    public Checkout checkoutBook(@RequestHeader("Authorization") String authorization, @PathVariable String isbn) {
        return bookService.checkoutBook(authorization, isbn);
    }

    @PostMapping("/{isbn}/return")
    public void returnBook(@RequestHeader("Authorization") String authorization, @PathVariable UUID checkoutID) {
        bookService.returnBook(authorization, checkoutID);
    }

}
