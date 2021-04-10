package org.vogel.javaplayground.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vogel.javaplayground.user.address.AddressMapper;

@Component
public class UserMapper {
    @Autowired
    private AddressMapper addressMapper;

    public UserDTO toDTO(final User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        if (user.getAddress() != null) {
            userDTO.setAddress(addressMapper.toDTO(user.getAddress()));
        }
        return userDTO;
    }

    public User toEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getAddress() != null) {
            user.setAddress(addressMapper.toEntity(userDTO.getAddress(), user.getAddress()));
        } else {
            user.setAddress(null);
        }

        return user;
    }
}
