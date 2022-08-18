package dev.om.library.user;

import dev.om.library.session.Session;
import dev.om.library.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public void addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
    }

    @PostMapping("/login")
    public Session login(@Valid @RequestBody User user) {
        return sessionService.createSession(user);
    }

}

