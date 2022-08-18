package dev.om.library.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserID(UUID userID);
    User findByEmail(String email);

    Boolean existsByUserID(UUID userID);
    Boolean existsByEmail(String email);

}