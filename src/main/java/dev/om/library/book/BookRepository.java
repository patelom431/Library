package dev.om.library.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    Book findByIsbn(String isbn);

    Boolean existsByIsbn(String isbn);

}
