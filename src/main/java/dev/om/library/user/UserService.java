package dev.om.library.user;

import dev.om.library.checkout.Checkout;
import dev.om.library.checkout.CheckoutService;
import dev.om.library.exception.BadCredentialsException;
import dev.om.library.exception.BadRequestException;
import dev.om.library.session.Session;
import dev.om.library.session.SessionRepository;
import dev.om.library.session.SessionService;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User already exists");
        }

        if (user.getPassword().length() < 8) {
            throw new BadRequestException("Password too weak");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);

        userRepository.save(user);
    }

    public List<Checkout> getCheckouts(String authorization) {
        Session session = sessionService.validateSession(authorization);

        if (session == null) {
            throw new BadCredentialsException("Invalid session");
        }

        return checkoutService.getCheckoutsByUserID(session.getUserID());
    }

}
