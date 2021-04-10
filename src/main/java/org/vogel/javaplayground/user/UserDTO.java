package org.vogel.javaplayground.user;

import lombok.Getter;
import lombok.Setter;
import org.vogel.javaplayground.user.address.AddressDTO;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String lastName;
    private AddressDTO address;
}
