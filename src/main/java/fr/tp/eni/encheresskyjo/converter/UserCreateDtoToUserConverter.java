package fr.tp.eni.encheresskyjo.converter;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;
import fr.tp.eni.encheresskyjo.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts the UserCreateDTO into the User object.
 *
 * @author TeamSkyjo
 * @version 1.1
 */
@Component
public class UserCreateDtoToUserConverter implements Converter<UserCreateDTO, User> {

    private final UserGeneralDtoToUserConverter userGeneralDtoToUserConverter;

    @Autowired
    public UserCreateDtoToUserConverter(UserGeneralDtoToUserConverter userGeneralDtoToUserConverter) {
        this.userGeneralDtoToUserConverter = userGeneralDtoToUserConverter;
    }

    @Override
    public User convert(UserCreateDTO userCreateDTO) {
        User user = userGeneralDtoToUserConverter.convert(userCreateDTO);
        user.setPassword(userCreateDTO.getPassword());
        return user;
    }
}
