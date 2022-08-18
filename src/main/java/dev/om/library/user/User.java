package dev.om.library.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "userID", nullable = false, unique = true)
    private UUID userID;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
    private Role role = Role.USER;

    public enum Role {
        USER,
        ADMIN
    }

    public User() {

    }

    public User(UUID userID, String email, String password, Role role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
