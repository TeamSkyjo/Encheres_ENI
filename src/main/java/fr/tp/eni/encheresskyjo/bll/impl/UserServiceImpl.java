package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.converter.UserCreateDtoToUserConverter;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Teamskyjo
 * @Version 1.0
 */

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private UserCreateDtoToUserConverter userCreateDtoToUserConverter;

    public UserServiceImpl(UserDAO userDAO, UserCreateDtoToUserConverter userCreateDtoToUserConverter) {
        this.userDAO = userDAO;
        this.userCreateDtoToUserConverter = userCreateDtoToUserConverter;
    }

    /**
     * Creates a new user in the system.
     *
     *  <p>
     *      <ol>
     *          <li>Validates the input data according to the business rules.</li>
     *          <li>Checks for username and email uniqueness.</li>
     *          <li>Converts the input DTO into a User business object.</li>
     *          <li>Persists the User object using the DAL.</li>
     *          </ol>
     *  </p>
     *
     * @param dto the user input dto containing user details
     * @throws BusinessException if validation fails or if the user is not unique.
     */
    @Transactional
    @Override
    public void createUser(UserCreateDTO dto) {
        BusinessException businessException = new BusinessException();
        boolean isValid = validateUser(dto, businessException);

        if (!isValid) {
            businessException.addKey(BusinessCode.VALID_USER);
            throw businessException;

        } else {
            if(!isUserUnique(dto, businessException)) {
                businessException.addKey(BusinessCode.VALID_USER_UNIQUENESS);
                throw businessException;
            }

            User user = userCreateDtoToUserConverter.convert(dto);
            userDAO.create(user);
        }
    }

    /**
     *
     * @param dto
     */
    @Override
    @Transactional
    public void updateUser(UserCreateDTO dto) {
        //User existingUser = userDAO.readById(user.getUserId());


        BusinessException businessException = new BusinessException();
        boolean isValid = validateUser(dto, businessException);

        // TODO : converter UserInputDTO -> User

//        if (isValid) {
//            this.userDAO.updateAll(dto);
//        } else {
//            businessException.addKey(BusinessCode.VALID_USER);
//            throw businessException;
//        }

    }

    private boolean validateUser(UserCreateDTO dto, BusinessException businessException) {
        boolean isValid = true;

        isValid = isUsernameValid(dto.getUsername(), businessException);
        isValid &= isFirstnameValid(dto.getFirstName(), businessException);
        isValid &= isLastnameValid(dto.getLastName(), businessException);
        isValid &= isEmailValid(dto.getEmail(), businessException);
        isValid &= isPhoneValid(dto.getTelephone(), businessException);
        isValid &= isStreetValid(dto.getStreet(), businessException);
        isValid &= isZipValid(dto.getZip(), businessException);
        isValid &= isCityValid(dto.getCity(), businessException);
        isValid &= isPasswordValid(dto.getPassword(), dto.getPasswordConfirm(), businessException);

        return isValid;
    }

//  Passer par une DTO :
    // DAO: readById() -> retourne User
    // BLL: convertit en DTO
    // Contrôleur: passe UserDTO dans Formulaire

    // profil d'un autre utilisateur (lien depuis pseudo)
    @Override
    public User loadUser(String username) {
        return userDAO.readByUsername(username);
    }



    @Override
    public void changePassword(String email, String newPassword) {
        // TODO
        userDAO.updatePassword(email, newPassword);
    }

    //TODO
    @Override
    public void deleteUser(int userId) {
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


    private boolean isUserUnique(UserCreateDTO dto, BusinessException businessException) {
        boolean isUsernameUnique = userDAO.isUsernameUnique(dto.getUsername());
        boolean isEmailUnique = userDAO.isEmailUnique(dto.getEmail());

        if (!isUsernameUnique) {
            businessException.addKey(BusinessCode.VALID_USER_USERNAME_UNIQUENESS);
        }

        if (!isEmailUnique) {
            businessException.addKey(BusinessCode.VALID_USER_EMAIL_UNIQUENESS);
        }

        return isUsernameUnique && isEmailUnique;
    }


}
