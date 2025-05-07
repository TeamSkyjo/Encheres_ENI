package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;
import fr.tp.eni.encheresskyjo.dto.UserGeneralDTO;
import fr.tp.eni.encheresskyjo.dto.UserUpdateDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class TestUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testCreateUser_valid() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("Jojo89");
        userCreateDTO.setLastName("Lapin");
        userCreateDTO.setFirstName("Jojo");
        userCreateDTO.setEmail("jojo89@gmail.com");
        userCreateDTO.setTelephone("");
        userCreateDTO.setStreet("4 rue du terrier");
        userCreateDTO.setZip("89024");
        userCreateDTO.setCity("LapinVille");
        userCreateDTO.setPassword("Pa$$w0rdlong");
        userCreateDTO.setPasswordConfirm("Pa$$w0rdlong");

        try {
            userService.createUser(userCreateDTO);
        } catch (BusinessException e) {
            System.out.println("BusinessException keys: " + e.getKeys());
        }


        User createdUser = userDAO.readByUsername("Jojo89");

        assertNotNull(createdUser, "User should be in the database");

        System.out.println("Created user: " + createdUser);

    }

    @Test
    public void testCreateUser_usernameFail() {
        // username already in database: techguy
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("techguy");
        userCreateDTO.setLastName("Lapin");
        userCreateDTO.setFirstName("Jojo");
        userCreateDTO.setEmail("jojo89@gmail.com");
        userCreateDTO.setTelephone("");
        userCreateDTO.setStreet("4 rue du terrier");
        userCreateDTO.setZip("89024");
        userCreateDTO.setCity("LapinVille");
        userCreateDTO.setPassword("Pa$$w0rdlong");
        userCreateDTO.setPasswordConfirm("Pa$$w0rdlong");

        try {
            userService.createUser(userCreateDTO);
        } catch (BusinessException e) {
            System.out.println("BusinessException keys: " + e.getKeys());
        }
    }

    @Test
    public void testCreateUser_emailFail() {
        // email already in database: julien.lemoine@email.com
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("Jojo89");
        userCreateDTO.setLastName("Lapin");
        userCreateDTO.setFirstName("Jojo");
        userCreateDTO.setEmail("julien.lemoine@email.com");
        userCreateDTO.setTelephone("");
        userCreateDTO.setStreet("4 rue du terrier");
        userCreateDTO.setZip("89024");
        userCreateDTO.setCity("LapinVille");
        userCreateDTO.setPassword("Pa$$w0rdlong");
        userCreateDTO.setPasswordConfirm("Pa$$w0rdlong");

        try {
            userService.createUser(userCreateDTO);
        } catch (BusinessException e) {
            System.out.println("BusinessException keys: " + e.getKeys());
        }
    }

    @Test
    public void testLoadUser_valid() {
        userService.createUser(new UserCreateDTO(
                "movieFan",
                "Almodovar",
                "Pedro",
                "p.almodovar@email.com",
                null,
                "5 somewhere",
                "67345",
                "MadridInFrance",
                "MotDePasse123!",
                "MotDePasse123!"
        ));
        UserGeneralDTO dto = userService.loadUser("movieFan");
        System.out.println("user loaded: " + dto);

    }

    @Test
    public void testLoadUser_fail() {
        // user doesn't exist
        try {
            UserGeneralDTO dto = userService.loadUser("pseudo");
            System.out.println("user loaded: " + dto);
        } catch(BusinessException e) {
            System.out.println("BusinessException keys: " + e.getKeys());
        }

    }

//    @Test
//    public void testDeleteUser_valid() {
//        // TODO : contraintes de clés étrangères
//        int userId = 1;
//
//        userService.deleteUser(userId);
//
//        try {
//            User deletedUser = userDAO.readById(userId);
//
//            if (deletedUser == null) {
//                System.out.println("User with id " + userId + " has been successfully deleted.");
//            } else {
//                System.out.println("User with id " + userId + " still exists.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error deleting user: " + e.getMessage());
//        }
//
//    }


    @Test
    public void testUpdateUser_valid() {
        User user = userDAO.readById(1);
        System.out.println("\nAvant update : " + user);

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUserId(1);
        userUpdateDTO.setUsername("Jojo");
        userUpdateDTO.setLastName("Almodovar");
        userUpdateDTO.setCity("Madrid");

        try {
            userService.updateUser(userUpdateDTO);

            User updatedUser = userDAO.readById(1);
            System.out.println("\nAfter update : " + updatedUser);

        } catch (BusinessException businessException){
            System.out.println("Error : " + businessException.getKeys());
        }

    }

    @Test
    public void testCreateAndUpdateUser_pswd() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("Jojo89");
        userCreateDTO.setLastName("Lapin");
        userCreateDTO.setFirstName("Jojo");
        userCreateDTO.setEmail("jojo89@gmail.com");
        userCreateDTO.setTelephone("");
        userCreateDTO.setStreet("4 rue du terrier");
        userCreateDTO.setZip("89024");
        userCreateDTO.setCity("LapinVille");
        userCreateDTO.setPassword("Pa$$w0rdlong");
        userCreateDTO.setPasswordConfirm("Pa$$w0rdlong");

        try {
            userService.createUser(userCreateDTO);
        } catch (BusinessException e) {
            System.out.println("BusinessException keys: " + e.getKeys());
        }


        User createdUser = userDAO.readByUsername("Jojo89");
        System.out.println("\nCreated user: " + createdUser);
        System.out.println("\ncreated user psw: " + createdUser.getPassword());

        System.out.println("\nUpdated user---------------");
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUserId(createdUser.getUserId());
        userUpdateDTO.setCurrentPassword("Pa$$w0rdlong");
        userUpdateDTO.setNewPassword("Motdepasse123!");
        userUpdateDTO.setNewPasswordConfirm("Motdepasse123!");

        try {
            userService.updateUser(userUpdateDTO);

            User updatedUser = userDAO.readById(createdUser.getUserId());
            System.out.println("\nAfter update : " + updatedUser);

        } catch (BusinessException businessException){
            System.out.println("Error : " + businessException.getKeys());
        }


    }

    @Test
    public void testUpdateUser_password_valid() {
        User user = userDAO.readById(1);
        System.out.println("\nAvant update : " + user.getPassword());

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUserId(1);
        userUpdateDTO.setTelephone("0143567898");
        userUpdateDTO.setCurrentPassword("pass123");
        userUpdateDTO.setNewPassword("Motdepasse123!");
        userUpdateDTO.setNewPasswordConfirm("Motdepasse123!");

        try {
            userService.updateUser(userUpdateDTO);

            User updatedUser = userDAO.readById(1);
            System.out.println("\nAfter update : " + updatedUser.getPassword());

        } catch (BusinessException businessException){
            System.out.println("Error : " + businessException.getKeys());
        }
    }
}
