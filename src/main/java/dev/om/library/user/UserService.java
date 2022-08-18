package dev.om.library.user;

import dev.om.library.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(User user) {
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
}
