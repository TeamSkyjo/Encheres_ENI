package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userDAO.readByUsername("movieFan");
        System.out.println("user in DB:\n" + user);

        System.out.println("user loaded: ");
        System.out.println(userService.loadUser("movieFan"));
    }

    @Test
    public void testLoadUser_fail() {
        User user = userDAO.readByUsername("movieFan");
        System.out.println("user in DB:\n" + user);

        System.out.println("user loaded: ");
        System.out.println(userService.loadUser("movieFan"));
    }



    // can't retrieve password from DB, always null
    // -> RowMapper -> DTO !!!
    @Test
    public void testUpdateUser_valid() {
//        User user = userService.loadUser(5);
//        System.out.println(user);
//        System.out.println(user.getPassword());

        //user.setUsername("machin");
        //user.setTelephone("0678987654");
        //user.setCity("Paris");
        //user.getPassword();
//        user.setPassword("MotDePasseValide=123");
//        user.setPasswordConfirm("MotDePasseValide=123");

//        try {
//            userService.updateUser(user);
//            System.out.println("User : update successful");
//        } catch (BusinessException businessException){
//            System.out.println(businessException.getKeys());
//        }

    }
}
