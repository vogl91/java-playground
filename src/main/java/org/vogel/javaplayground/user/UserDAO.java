package org.vogel.javaplayground.user;

import org.springframework.stereotype.Component;
import org.vogel.javaplayground.common.AbstractDAO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDAO extends AbstractDAO<User> {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public Optional<User> findByName(final String name) {
        List<User> users = executeNamedQuery("user.findByName", Map.of("name", name));
        return users.stream().findAny();
    }
}
