package dev.om.library.checkout;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "checkouts")
public class Checkout {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "checkoutID", nullable = false, unique = true)
    private UUID checkoutID;

    @Column(name = "userID", nullable = false)
    private UUID userID;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "returned", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean returned;

    @Column(name = "dateCreated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreated;

    @Column(name = "dateDue", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '30' DAY")
    private Timestamp dateDue;

    @Column(name = "dateReturned", columnDefinition = "TIMESTAMP DEFAULT NULL")
    private Timestamp dateReturned;

    public Checkout() {

    }

    public Checkout(UUID checkoutID, UUID userID, String isbn, Boolean returned, Timestamp dateCreated, Timestamp dateDue, Timestamp dateReturned) {
        this.checkoutID = checkoutID;
        this.userID = userID;
        this.isbn = isbn;
        this.returned = returned;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.dateReturned = dateReturned;
    }

}
