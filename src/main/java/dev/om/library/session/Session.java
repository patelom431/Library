package dev.om.library.session;

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
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "sessionID", nullable = false, unique = true)
    private UUID sessionID;

    @Column(name = "userID", nullable = false, unique = true)
    private UUID userID;

    @Column(name = "dateCreated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreated;

    @Column(name = "dateExpires", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '1' DAY")
    private Timestamp dateExpires;

    public Session() {

    }

    public Session(UUID sessionID, UUID userID, Timestamp dateCreated, Timestamp dateExpires) {
        this.sessionID = sessionID;
        this.userID = userID;
        this.dateCreated = dateCreated;
        this.dateExpires = dateExpires;
    }

}
