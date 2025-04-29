package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.SQLOutput;

@SpringBootTest
public class TestUserDAO {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void test_create() {

        User test = new User("skyjo", "vrd", "Laety", "laety@vrd.fr", null, "rue des mouettes", "29000", "Quimper", "$2a$10$kHbAVAt47pD.9mFChqZ1jOS3Cu9csyhKUk.ZqxShspqVEIQntFtfa", 500,false);
        System.out.println(test);
        userDAO.create(test);
        System.out.println(test);
        //Object userId linked with database -> OK
    }

    @Test
    public void test_readById() {
        User user = userDAO.readById(2);
        System.out.println(user);
        //Claire Durand -> OK
    }

    @Test
    public void test_readByUsername() {
        User user = userDAO.readByUsername("sporty");
        System.out.println(user);
    }

    @Test
    public void test_readByEmail() {
        User user = userDAO.readByEmail("lucas.martin@email.com");
        System.out.println(user);
    }

    @Test
    public void test_update() {
        User user = new User(8,"skyjo", "vrd", "Laety", "laety@vrd.fr", null, "rue des mouettes", "29000", "Quimper", "$2a$10$kHbAVAt47pD.9mFChqZ1jOS3Cu9csyhKUk.ZqxShspqVEIQntFtfa", 500,false);
        userDAO.updateAll(user);
    }

    @Test
    public void test_updatePassword() {
        userDAO.updatePassword("laety@vrd.fr", "nouveaumotdepassedetest");
    }

}
