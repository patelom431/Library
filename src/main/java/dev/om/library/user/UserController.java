package dev.om.library.user;

import dev.om.library.checkout.Checkout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/checkouts")
    public List<Checkout> getCheckouts(@RequestHeader("Authorization") String authorization) {
        return userService.getCheckouts(authorization);
    }

}

