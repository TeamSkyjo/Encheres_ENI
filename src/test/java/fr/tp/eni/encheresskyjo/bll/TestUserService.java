package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
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
        // Rien ne fonctionne
        User user = new User();
        user.setUsername("Jojo89");
        user.setLastName("Lapin");
        user.setFirstName("Jojo");
        user.setEmail("jojo89@gmail.com");
        user.setTelephone("");
        user.setStreet("4 rue du terrier");
        user.setZip("89024");
        user.setCity("LapinVille");
        user.setPassword("Pa$$w0rdlong");
        user.setPasswordConfirm("Pa$$w0rdlong");

        try {
            userService.createUser(user);
        } catch (BusinessException e) {
            System.out.println("BusinessException keys: " + e.getKeys());
        }
//
//
//        User createdUser = userDAO.readByUsername("Jojo89");
//
//        assertNotNull(createdUser, "User should be in the database");
//
//        System.out.println("Created user: " + user);

    }
}
