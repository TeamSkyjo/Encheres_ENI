package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import fr.tp.eni.encheresskyjo.dto.RegisterDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public void createUser(User user) {
        boolean isValid = true;
        BusinessException businessException = new BusinessException();

        isValid = isUsernameValid(user.getUsername(), businessException);

        isValid &= isFirstnameValid(user.getFirstName(), businessException);
        isValid &= isLastnameValid(user.getLastName(), businessException);
        isValid &= isEmailValid(user.getEmail(), businessException);
        isValid &= isPhoneValid(user.getTelephone(), businessException);
        isValid &= isStreetValid(user.getStreet(), businessException);
        isValid &= isZipValid(user.getZip(), businessException);
        isValid &= isCityValid(user.getCity(), businessException);

        isValid &= isPasswordValid(user.getPassword(), user.getPasswordConfirm(), businessException);



        if (isValid) {
            // TODO Spring Security - BCryptPasswordEncoder

            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setFirstName(user.getFirstName());
            newUser.setPassword(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setTelephone(user.getTelephone());
            newUser.setStreet(user.getStreet());
            newUser.setZip(user.getZip());
            newUser.setCity(user.getCity());
            // password with {bcrypt}
            newUser.setPassword(user.getPassword());

            userDAO.create(newUser);
        } else {
            throw businessException;
        }
    }

    @Override
    public User LoadUser(int userId) {
        return userDAO.readById(userId);
    }

    @Override
    public void ChangePassword(String email, String newPassword) {
        // TODO
        userDAO.updatePassword(email, newPassword);
    }

    @Override
    public void deleteProfile(int userId) {
        userDAO.delete(userId);
    }

    private boolean isUsernameValid(String username, BusinessException businessException) {
        boolean isValid = true;

        User user = userDAO.readByUsername(username);
        if (user != null) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_EXIST_ALREADY);
        }

        // possible problème ici
        if (username.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_USERNAME);
        }

        if (username.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_USERNAME_LENGTH);
        }

        return isValid;
    }

    private boolean isFirstnameValid(String firstname, BusinessException businessException) {
        boolean isValid = true;
        if (firstname.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_FIRSTNAME);
        }
        if (firstname.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USERFIRSTNAME_LENGTH);
        }
        return isValid;
    }

    private boolean isLastnameValid(String lastname, BusinessException businessException) {
        boolean isValid = true;
        if (lastname.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_LASTNAME);
        }

        if (lastname.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_LASTNAME_LENGTH);
        }
        return isValid;
    }

    private boolean isEmailValid(String email, BusinessException businessException) {
        boolean isValid = true;
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

        if (email == null || !email.matches(emailRegex)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_EMAIL);
        }
        return isValid;
    }

    // TODO
    private boolean isPhoneValid(String phone, BusinessException businessException) {
        boolean isValid = true;
        // TO DO: VARCHAR 15 - regex
        if (phone.length() != 15  ) {

            // VALID_USER_PHONE
        }

        return isValid;
    }

    private boolean isStreetValid(String street, BusinessException businessException) {
        boolean isValid = true;
        //TODO VARCHAR 30 NOT NULL
        // VALID_STREET_NAME


        return isValid;
    }

    private boolean isZipValid(String zip, BusinessException businessException) {
        boolean isValid = true;
        //TODO VARCHAR 10 NOT NULL
        // VALID_ZIP

        return isValid;
    }

    private boolean isCityValid(String city, BusinessException businessException) {
        boolean isValid = true;

        // TODO VARCHAR 30 NOT NULL
        // VALID_CITY

        return isValid;
    }

    private boolean isPasswordValid(String password, String passwordConfirm, BusinessException businessException) {
        boolean isValid = true;

        // Bonnes pratiques: au minimum 12 caractères comprenant des majuscules, des minuscules, des chiffres
        // et des caractères spéciaux à choisir dans une liste de caractères spéciaux !@#$%^&*()_+

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+]).{12,250}$";

        if (!password.matches(passwordRegex)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_REGEX);
        }

        if (!password.equals(passwordConfirm)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_CONFIRM);
        }

        return isValid;

    }



}
