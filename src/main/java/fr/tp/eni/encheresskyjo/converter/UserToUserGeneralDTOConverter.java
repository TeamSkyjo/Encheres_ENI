package fr.tp.eni.encheresskyjo.converter;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserGeneralDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserGeneralDTOConverter implements Converter<User, UserGeneralDTO> {

    @Override
    public UserGeneralDTO convert(User user) {
        UserGeneralDTO userGeneralDTO = new UserGeneralDTO();
        userGeneralDTO.setUsername(user.getUsername());
        userGeneralDTO.setLastName(user.getLastName());
        userGeneralDTO.setFirstName(user.getFirstName());
        userGeneralDTO.setEmail(user.getEmail());
        userGeneralDTO.setTelephone(user.getTelephone());
        userGeneralDTO.setStreet(user.getStreet());
        userGeneralDTO.setZip(user.getZip());
        userGeneralDTO.setCity(user.getCity());

        return userGeneralDTO;
    }
}
