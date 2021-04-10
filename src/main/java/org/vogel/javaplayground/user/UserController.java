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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{name}")
    public UserDTO getByName(@PathVariable("name") final String name) {
        User user = userDAO.findByName(name).orElseThrow(() -> new EntityNotFoundException(
                String.format("entity 'user' with name '%s' not found ", name)));
        UserDTO userDTO = userMapper.toDTO(user);
        return userDTO;
    }

    @PostMapping(path = "")
    public UserDTO upsert(@RequestBody UserDTO userDTO) {
        User user = userDAO.findByName(userDTO.getName()).orElseGet(User::new);
        user = userMapper.toEntity(userDTO, user);
        user = userDAO.merge(user);
        UserDTO updatedUserDTO = userMapper.toDTO(user);
        return updatedUserDTO;
    }
}
