package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.RegisterDTO;

public interface UserService {

    void createUser(User user);
    User LoadUser(int userId);
    void ChangePassword(String email, String newPassword);
    void deleteProfile(int userId);
}
