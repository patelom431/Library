package dev.om.library.session;

import dev.om.library.exception.BadCredentialsException;
import dev.om.library.user.User;
import dev.om.library.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public Session getSession(UUID sessionID) {
        return sessionRepository.findBySessionID(sessionID);
    }

    @Transactional
    public Session createSession(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        User userTemp = userRepository.findByEmail(user.getEmail());
        String passwordHash = userTemp.getPassword();

        if (!BCrypt.checkpw(user.getPassword(), passwordHash)) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (sessionRepository.existsByUserID(userTemp.getUserID())) {
            sessionRepository.deleteByUserID(userTemp.getUserID());
        }

        Session session = new Session();
        session.setUserID(userTemp.getUserID());
        session.setDateCreated(Timestamp.from(Instant.now()));
        session.setDateExpires(Timestamp.from(Instant.now().plusSeconds(86400)));

        sessionRepository.save(session);

        return session;
    }

    public Session validateSession(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer")) {
            try {
                UUID sessionID = UUID.fromString(authorization.substring(7));

                if (sessionRepository.existsBySessionID(sessionID)) {
                    return(sessionRepository.findBySessionID(sessionID));
                } else {
                    return null;
                }
            } catch (IllegalArgumentException e) {
                return null;
            }
        } else {
            return null;
        }
    }

}
