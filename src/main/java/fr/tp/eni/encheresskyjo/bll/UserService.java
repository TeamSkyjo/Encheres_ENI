package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.RegisterDTO;

public interface UserService {

    void createUser(User user);
    User loadUser(int userId);
    void updateUser(User user);
    void changePassword(String email, String newPassword);
    void deleteUser(int userId);
}
