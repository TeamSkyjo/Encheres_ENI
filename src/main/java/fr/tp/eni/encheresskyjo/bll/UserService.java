package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;

public interface UserService {

    void createUser(UserCreateDTO dto);
    User loadUser(String username);
    void updateUser(UserCreateDTO dto);
    void changePassword(String email, String newPassword);
    void deleteUser(int userId);
}
