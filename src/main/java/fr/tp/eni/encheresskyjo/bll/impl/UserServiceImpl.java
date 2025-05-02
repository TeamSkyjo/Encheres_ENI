package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import fr.tp.eni.encheresskyjo.dto.RegisterDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Teamskyjo
 * @Version 1.0
 */

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Transactional
    @Override
    public void createUser(User user) {
        boolean isValid = true;
        BusinessException businessException = new BusinessException();

        //username and email
        isValid = isUsernameValid(user.getUsername(), businessException);
        isValid &= isFirstnameValid(user.getFirstName(), businessException);
        isValid &= isLastnameValid(user.getLastName(), businessException);
        isValid &= isEmailValid(user.getEmail(), businessException);
        isValid &= isPhoneValid(user.getTelephone(), businessException);
        isValid &= isStreetValid(user.getStreet(), businessException);
        isValid &= isZipValid(user.getZip(), businessException);
        isValid &= isCityValid(user.getCity(), businessException);

        isValid &= isPasswordValid(user.getPassword(), user.getPasswordConfirm(), businessException);

        isValid &= isUserUnique(user, businessException);

        if (isValid) {
            // TODO Spring Security - BCryptPasswordEncoder

            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setLastName(user.getLastName());
            newUser.setFirstName(user.getFirstName());
            newUser.setEmail(user.getEmail());
            newUser.setTelephone(user.getTelephone());
            newUser.setStreet(user.getStreet());
            newUser.setZip(user.getZip());
            newUser.setCity(user.getCity());
            // password with {bcrypt}
            newUser.setPassword(user.getPassword());
            newUser.setCredit(0);
            newUser.setAdmin(false);

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

        if (username == null || username.isBlank()) {
            businessException.addKey(BusinessCode.VALID_USER_USERNAME_BLANK);
            return false;
        }

        if (username.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_USERNAME_LENGTH_MAX);
        }

        return isValid;
    }

    private boolean isFirstnameValid(String firstname, BusinessException businessException) {
        boolean isValid = true;

        if (firstname == null || firstname.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_FIRSTNAME_BLANK);
        } else if (firstname.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_FIRSTNAME_LENGTH_MAX);
        }

        return isValid;
    }

    private boolean isLastnameValid(String lastname, BusinessException businessException) {
        boolean isValid = true;

        if (lastname == null || lastname.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_LASTNAME_BLANK);
        } else if (lastname.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_LASTNAME_LENGTH_MAX);
        }
        return isValid;
    }

    private boolean isEmailValid(String email, BusinessException businessException) {
        boolean isValid = true;
        String emailRegexValidation = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

        if (email == null || email.isBlank()) {
            businessException.addKey(BusinessCode.VALID_USER_EMAIL_BLANK);
            return false;
        }

        if (!email.matches(emailRegexValidation)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_EMAIL_FORMAT);
        } else if (email.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_EMAIL_LENGTH_MAX);
        }

        return isValid;
    }


    private boolean isPhoneValid(String phone, BusinessException businessException) {
        boolean isValid = true;

        // FR local number : 0 + 9 digits (ex: 0612345678)
        String phoneValidationRegex = "[0]{1}[1-9]{1}[0-9]{8}";

        if (phone != null && !phone.isBlank()) {
            if (!phone.matches(phoneValidationRegex)) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_USER_PHONE_FORMAT);
            } else if (phone.length() > 15) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_USER_PHONE_LENGTH_MAX);
            }
        }

        return isValid;
    }


    private boolean isStreetValid(String street, BusinessException businessException) {
        boolean isValid = true;

        String streetValidationRegex = "^[0-9]{1,4}(?: ?[A-Za-z]{1,3})?\\s+[\\p{L}0-9 .,'-]+$";

        if (street == null || street.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ADDRESS_STREET_NAME_BLANK);
        } else {
            if (street.length() > 30) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ADDRESS_STREET_NAME_LENGTH_MAX);
            } else if (!street.matches(streetValidationRegex)) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ADDRESS_STREET_NAME_FORMAT);
            }
        }

        return isValid;
    }


    private boolean isZipValid(String zip, BusinessException businessException) {
        boolean isValid = true;

        String postalCodeRegex = "^(0[1-9]|[1-8][0-9]|9[0-8])[0-9]{3}$";

        if (zip == null || zip.isBlank()) {
            businessException.addKey(BusinessCode.VALID_ADDRESS_ZIP_BLANK);
            return false;
        }

        if (zip.length() > 10) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ADDRESS_ZIP_LENGTH_MAX);
        } else if (!zip.matches(postalCodeRegex)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ADDRESS_ZIP_FORMAT);
        }

        return isValid;
    }


    private boolean isCityValid(String city, BusinessException businessException) {
        boolean isValid = true;


        if (city == null || city.isBlank()) {
            businessException.addKey(BusinessCode.VALID_ADDRESS_CITY_BLANK);
            return false;
        }

        if (city.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ADDRESS_CITY_LENGTH_MAX);
        }

        return isValid;
    }

    private boolean isPasswordValid(String password, String passwordConfirm, BusinessException businessException) {
        boolean isValid = true;

        // Password must contain at least 12 characters, including
        //  - one uppercase letter,
        //  - one lowercase letter,
        //  - one digit,
        //  - and one special character chosen from the following list : !@#$%^&*()_+

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+]).{12,250}$";

        if (password == null || password.isBlank()) {
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_BLANK);
            return false;
        }

        if (!password.matches(passwordRegex)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_FORMAT);
        }

        if (!password.equals(passwordConfirm)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_CONFIRM);
        }

        return isValid;

    }

    private boolean isUserUnique(User user, BusinessException businessException) {
        boolean isValid = userDAO.isUserUnique(user);

        if (!isValid) {
            businessException.addKey(BusinessCode.VALID_USER_UNIQUENESS);
        }

        return isValid;
    }


}
