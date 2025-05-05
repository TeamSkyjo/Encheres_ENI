package fr.tp.eni.encheresskyjo.converter;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserGeneralDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * Converts the UserGeneralDTO into the User object.
 *
 * @author TeamSkyjo
 * @version 1.0
 */
@Component
public class UserGeneralDtoToUserConverter implements Converter<UserGeneralDTO, User> {

    @Override
    public User convert(UserGeneralDTO userGeneralDTO) {

        User user = new User();
        user.setUsername(userGeneralDTO.getUsername());
        user.setLastName(userGeneralDTO.getLastName());
        user.setFirstName(userGeneralDTO.getFirstName());
        user.setEmail(userGeneralDTO.getEmail());
        user.setTelephone(userGeneralDTO.getTelephone());
        user.setStreet(userGeneralDTO.getStreet());
        user.setZip(userGeneralDTO.getZip());
        user.setCity(userGeneralDTO.getCity());

        return user;
    }
}
