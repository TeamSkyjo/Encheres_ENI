package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;
import fr.tp.eni.encheresskyjo.dto.UserGeneralDTO;
import fr.tp.eni.encheresskyjo.dto.UserUpdateDTO;

public interface UserService {

    void createUser(UserCreateDTO dto);
    UserGeneralDTO loadUser(String username);
    void updateUser(UserUpdateDTO dto);
    void changePassword(String email, String newPassword);
    void deleteUser(int userId);
}
