package dev.om.library.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    /**
    @PostMapping
    public Session createSession(@Valid @RequestBody User user) {
        return sessionService.createSession(user);
    }
    **/

}
