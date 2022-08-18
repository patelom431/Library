package dev.om.library.session;

import dev.om.library.book.Book;
import dev.om.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public Session createSession(@Valid @RequestBody User user) {
        return sessionService.createSession(user);
    }

    @GetMapping("/{sessionID}")
    public Session getSession(@PathVariable UUID sessionID) {
        return sessionService.getSession(sessionID);
    }

}
