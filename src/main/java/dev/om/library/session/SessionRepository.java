package dev.om.library.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    Session findBySessionID(UUID sessionID);
    Session findByUserID(UUID userID);

    Boolean existsBySessionID(UUID sessionID);
    Boolean existsByUserID(UUID userID);

    void deleteBySessionID(UUID sessionID);
    @Modifying
    @Query("DELETE FROM Session s WHERE s.userID=:userID")
    void deleteByUserID(@Param("userID") UUID userID);

}