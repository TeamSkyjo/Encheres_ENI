package fr.tp.eni.encheresskyjo.converter;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts the UserCreateDTO into the User object.
 *
 * @author TeamSkyjo
 * @version 1.0
 */
@Component
public class UserCreateDtoToUserConverter implements Converter<UserCreateDTO, User> {


    @Override
    public User convert(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setLastName(userCreateDTO.getLastName());
        user.setFirstName(userCreateDTO.getFirstName());
        user.setEmail(userCreateDTO.getEmail());
        user.setTelephone(userCreateDTO.getTelephone());
        user.setStreet(userCreateDTO.getStreet());
        user.setZip(userCreateDTO.getZip());
        user.setCity(userCreateDTO.getCity());
        user.setPassword(userCreateDTO.getPassword());

        return user;
    }
}
