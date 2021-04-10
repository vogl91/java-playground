package org.vogel.javaplayground.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RestController
@RequestMapping(path = "/user")
@Transactional
public class UserController {
    @Autowired
    private UserDAO userDAO;


    @GetMapping("/{name}")
    public User getByName(@PathVariable("name") final String name) {
        return userDAO.findByName(name).orElseThrow(() -> new EntityNotFoundException(
                String.format("entity 'user' with name '%s' not found ", name)));
    }

    @PostMapping(path = "")
    public User upsert(@RequestBody User user) {
        if (user.getId() != null) {
            userDAO.findExistingById(user.getId());
        }
        user = userDAO.merge(user);
        return user;
    }
}
