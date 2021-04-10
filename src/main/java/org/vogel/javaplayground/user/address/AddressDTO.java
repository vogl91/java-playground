package org.vogel.javaplayground.user.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String zipCode;
    private String city;
    private String street;
}
