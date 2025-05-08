package fr.tp.eni.encheresskyjo.converter;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts the UserUpdateDTO into the User object.
 *
 * <p>
 *     Password validation and update logic (e.g. comparing old and new passwords)
 *     is handled separately in the service layer.
 * </p>
 *
 * @author TeamSkyjo
 * @version 1.0
 */
@Component
public class UserUpdateDtoToUserConverter implements Converter<UserUpdateDTO, User> {

    private final UserGeneralDtoToUserConverter userGeneralDtoToUserConverter;

    @Autowired
    public UserUpdateDtoToUserConverter(UserGeneralDtoToUserConverter userGeneralDtoToUserConverter) {
        this.userGeneralDtoToUserConverter = userGeneralDtoToUserConverter;
    }

    @Override
    public User convert(UserUpdateDTO userUpdateDTO) {
        User user = userGeneralDtoToUserConverter.convert(userUpdateDTO);

        return user;
    }
}
