package com.poly.Mapper;

import com.poly.dto.response.AddressReponse;
import com.poly.Model.Address;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressDTOMapper implements Function<Address, AddressReponse> {
    @Override
    public AddressReponse apply(Address address) {
        return AddressReponse.builder()
                .addressId(address.getId())
//                .streetName(address.getStreet())
                .build();
    }
}
