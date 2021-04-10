package org.vogel.javaplayground.user.address;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDTO toDTO(final Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setZipCode(address.getZipCode());
        return addressDTO;
    }

    public Address toEntity(final AddressDTO addressDTO, @Nullable Address address) {
        if (address == null) {
            address = new Address();
        }
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setZipCode(addressDTO.getZipCode());
        return address;
    }
}
