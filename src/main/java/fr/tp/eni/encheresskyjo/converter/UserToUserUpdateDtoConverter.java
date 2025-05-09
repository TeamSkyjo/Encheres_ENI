package fr.tp.eni.encheresskyjo.converter;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserUpdateDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserUpdateDtoConverter implements Converter<User, UserUpdateDTO> {

    @Override
    public UserUpdateDTO convert(User user) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUserId(user.getUserId());
        userUpdateDTO.setUsername(user.getUsername());
        userUpdateDTO.setLastName(user.getLastName());
        userUpdateDTO.setFirstName(user.getFirstName());
        userUpdateDTO.setEmail(user.getEmail());
        userUpdateDTO.setTelephone(user.getTelephone());
        userUpdateDTO.setStreet(user.getStreet());
        userUpdateDTO.setZip(user.getZip());
        userUpdateDTO.setCity(user.getCity());

        return userUpdateDTO;
    }
}
