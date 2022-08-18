package dev.om.library.book;

import dev.om.library.checkout.Checkout;
import dev.om.library.checkout.CheckoutRepository;
import dev.om.library.exception.BadCredentialsException;
import dev.om.library.exception.BadRequestException;
import dev.om.library.session.Session;
import dev.om.library.session.SessionService;
import dev.om.library.user.User;
import dev.om.library.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public void createBook(String authorization, Book book) {
        Session session = sessionService.validateSession(authorization);

        if (session == null) {
            throw new BadCredentialsException("Invalid session");
        }

        if (userRepository.findByUserID(session.getUserID()).getRole() != User.Role.ADMIN) {
            throw new BadCredentialsException("Unauthorized");
        }

        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BadRequestException("ISBN already exists");
        }

        bookRepository.save(book);
    }

    public Checkout checkoutBook(String authorization, String isbn) {
        Session session = sessionService.validateSession(authorization);

        if (session == null) {
            throw new BadCredentialsException("Invalid session");
        }

        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BadRequestException("Invalid ISBN");
        }

        Book book = bookRepository.findByIsbn(isbn);
        User user = userRepository.findByUserID(session.getUserID());

        if (checkoutRepository.countByUserID(user.getUserID()) >= 3) {
            throw new BadRequestException("Maximum of 3 checkouts per user");
        }

        if (checkoutRepository.countByIsbn(book.getIsbn()) >= book.getQuantity()) {
            throw new BadRequestException("All books checked out");
        }

        Checkout checkout = new Checkout();
        checkout.setUserID(user.getUserID());
        checkout.setIsbn(book.getIsbn());
        checkout.setReturned(false);
        checkout.setDateCreated(Timestamp.from(Instant.now()));
        checkout.setDateDue(Timestamp.from(Instant.now().plusSeconds(86400 * 30)));
        checkout.setDateReturned(null);

        checkoutRepository.save(checkout);

        return checkout;
    }

    public void returnBook(String authorization, UUID checkoutID) {
        Session session = sessionService.validateSession(authorization);

        if (session == null) {
            throw new BadCredentialsException("Invalid session");
        }

        if (userRepository.findByUserID(session.getUserID()).getRole() != User.Role.ADMIN) {
            throw new BadCredentialsException("Unauthorized");
        }

        if (!checkoutRepository.existsByCheckoutID(checkoutID)) {
            throw new BadRequestException("Invalid checkout ID");
        }

        Checkout checkout = checkoutRepository.findByCheckoutID(checkoutID);

        if (checkout.getReturned()) {
            throw new BadRequestException("Book already returned");
        }

        checkout.setReturned(true);
        checkout.setDateReturned(Timestamp.from(Instant.now()));

        checkoutRepository.save(checkout);
    }

}
