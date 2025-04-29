package fr.tp.eni.encheresskyjo.dal;
/**
 * @Author TeamSkyjo
 * Version : 1.0
 * Interface to define all methods linked to users in database
 */

import fr.tp.eni.encheresskyjo.bo.User;

public interface UserDAO {
    void create(User user);
    User readById(int userId);
    User readByUsername(String username);
    User readByEmail(String email);
    void updateAll(User user);
    void updatePassword(String email, String newPassword);
    void updateCredit (int userId, int newCredit);
    void delete(int userId);
}
