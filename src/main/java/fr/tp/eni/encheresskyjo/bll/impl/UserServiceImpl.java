package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.converter.UserCreateDtoToUserConverter;
import fr.tp.eni.encheresskyjo.converter.UserToUserGeneralDTOConverter;
import fr.tp.eni.encheresskyjo.converter.UserUpdateDtoToUserConverter;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;
import fr.tp.eni.encheresskyjo.dto.UserGeneralDTO;
import fr.tp.eni.encheresskyjo.dto.UserUpdateDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Teamskyjo
 * @Version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserToUserGeneralDTOConverter userToUserGeneralDTOConverter;
    private final UserCreateDtoToUserConverter userCreateDtoToUserConverter;
    private final UserUpdateDtoToUserConverter userUpdateDtoToUserConverter;

    public UserServiceImpl(
            UserDAO userDAO,
            UserToUserGeneralDTOConverter userToUserGeneralDTOConverter,
            UserCreateDtoToUserConverter userCreateDtoToUserConverter,
            UserUpdateDtoToUserConverter userUpdateDtoToUserConverter
    ) {
        this.userDAO = userDAO;
        this.userToUserGeneralDTOConverter = userToUserGeneralDTOConverter;
        this.userCreateDtoToUserConverter = userCreateDtoToUserConverter;
        this.userUpdateDtoToUserConverter = userUpdateDtoToUserConverter;

    }

    /**
     * Creates a new user in the system.
     *
     * <p>
     *     <ol>
     *         <li>Validates the input data according to the business rules.</li>
     *         <li>Checks for username and email uniqueness.</li>
     *         <li>Converts the input DTO into a User business object.</li>
     *         <li>Persists the User object using the DAL.</li>
     *         </ol>
     * </p>
     *
     * @param dto the user input dto containing user details
     * @throws BusinessException if validation fails or if the user is not unique.
     */
    @Transactional
    @Override
    public void createUser(UserCreateDTO dto) {
        BusinessException businessException = new BusinessException();
        boolean isValid = validateCreateUser(dto, businessException);
        isValid &= isUserUnique(dto, businessException);

        if (!isValid) {
            businessException.addKey(BusinessCode.VALID_USER);
            throw businessException;
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(dto.getPassword());
            User user = userCreateDtoToUserConverter.convert(dto);
            user.setPassword("{bcrypt}" + encodedPassword);
            userDAO.create(user);
        }
    }

    /**
     * Updates an existing user's information.
     *
     * @param dto the UserUpdateDTO containing updated user data.
     * @throws BusinessException if validation fails.
     */
    @Override
    @Transactional
    public void updateUser(UserUpdateDTO dto) {

        BusinessException businessException = new BusinessException();

        // TODO : with Spring Security, validate current Password against the authenticated user.
        boolean isValid = validateUpdateUser(dto, businessException);

        if (!isValid) {
            businessException.addKey(BusinessCode.VALID_USER);
            throw businessException;
        }

        User existingUser = userDAO.readById(dto.getId());
        if (dto.getUsername() != null) existingUser.setUsername(dto.getUsername());
        if (dto.getLastName() != null) existingUser.setLastName(dto.getLastName());
        if (dto.getFirstName() != null) existingUser.setFirstName(dto.getFirstName());
        if (dto.getEmail() != null) existingUser.setEmail(dto.getEmail());
        if (dto.getTelephone() != null) existingUser.setTelephone(dto.getTelephone());
        if (dto.getStreet() != null) existingUser.setStreet(dto.getStreet());
        if (dto.getZip() != null) existingUser.setZip(dto.getZip());
        if (dto.getCity() != null) existingUser.setCity(dto.getCity());


        if (isPasswordChanged(dto)) {
            // TODO: encode password
            existingUser.setPassword(dto.getNewPassword());
        }

        userDAO.updateAll(existingUser);

//        User existingUser = userDAO.readByUsername(dto.getUsername());
//        User updatedUser = userUpdateDtoToUserConverter.convert(dto);
//        if (isPasswordChanged(dto)) {
//            updatedUser.setPassword(dto.getNewPassword());  // Encoder le mot de passe ici
//        } else {
//            updatedUser.setPassword(existingUser.getPassword());
//        }
//        userDAO.updateAll(updatedUser);

    }

    /**
     * Validates the general user information fields
     *
     * @param dto               the UserGeneralDTO containing the user general information.
     * @param businessException the object to collect any validation errors.
     * @return true if all general user fields are valid; false otherwise.
     */
    private boolean validateUserGeneral(UserGeneralDTO dto, BusinessException businessException) {
        boolean isValid = true;

        isValid = isUsernameValid(dto.getUsername(), businessException);
        isValid &= isFirstnameValid(dto.getFirstName(), businessException);
        isValid &= isLastnameValid(dto.getLastName(), businessException);
        isValid &= isEmailValid(dto.getEmail(), businessException);
        isValid &= isPhoneValid(dto.getTelephone(), businessException);
        isValid &= isStreetValid(dto.getStreet(), businessException);
        isValid &= isZipValid(dto.getZip(), businessException);
        isValid &= isCityValid(dto.getCity(), businessException);

        return isValid;
    }

    /**
     * Validate the creation of a user by checking both general user information
     * and password rules.
     *
     * @param dto               the UserCreateDTO containing the user information to validate.
     * @param businessException the object to collect any validation errors.
     * @return true if all fields and password validation pass; false otherwise.
     */
    private boolean validateCreateUser(UserCreateDTO dto, BusinessException businessException) {
        boolean isValid = validateUserGeneral(dto, businessException);
        isValid &= isPasswordValid(dto.getPassword(), dto.getPasswordConfirm(), businessException);
        return isValid;
    }

    /**
     * Validate the user update request, including password change attempt.
     *
     * @param dto               the DTO containing user update information.
     * @param businessException an exception object used to store validation errors.
     * @return true if the user update is valid, false otherwise.
     */
    private boolean validateUpdateUser(UserUpdateDTO dto, BusinessException businessException) {
        boolean isValid = true;

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            isValid &= isUsernameValid(dto.getUsername(), businessException);
        }
        if (dto.getLastName() != null && !dto.getLastName().isBlank()) {
            isValid &= isLastnameValid(dto.getLastName(), businessException);
        }
        if (dto.getFirstName() != null && !dto.getFirstName().isBlank()) {
            isValid &= isFirstnameValid(dto.getFirstName(), businessException);
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            isValid &= isEmailValid(dto.getEmail(), businessException);
        }
        if (dto.getTelephone() != null && !dto.getTelephone().isBlank()) {
            isValid &= isPhoneValid(dto.getTelephone(), businessException);
        }
        if (dto.getStreet() != null && !dto.getStreet().isBlank()) {
            isValid &= isStreetValid(dto.getStreet(), businessException);
        }
        if (dto.getZip() != null && !dto.getZip().isBlank()) {
            isValid &= isZipValid(dto.getZip(), businessException);
        }
        if (dto.getCity() != null && !dto.getCity().isBlank()) {
            isValid &= isCityValid(dto.getCity(), businessException);
        }

        if (isPasswordChanged(dto)) {
            // TODO: with Spring Security, validate if the current password matches the one in th DB.
            // (PasswordEncoder)

            // checks if currentPassword field is provided (TO BE REPLACED BY SPRING SECURITY validation)
            if (dto.getCurrentPassword() == null || dto.getCurrentPassword().isBlank()) {
                businessException.addKey(BusinessCode.VALID_USER_PASSWORD_CURRENT_BLANK);
                isValid = false;
            }

            // Validate new password and its confirmation.
            isValid &= isPasswordValid(dto.getNewPassword(), dto.getNewPasswordConfirm(), businessException);
        }

        return isValid;
    }

    /**
     * Checks if the user has attempted to change their password.
     *
     * @param dto the DTO containing user update information.
     * @return true if any of the three password-related fields (current password, new password, confirmation) are not null or not blank.
     */
    private boolean isPasswordChanged(UserUpdateDTO dto) {

        if (dto.getCurrentPassword() != null && !dto.getCurrentPassword().isBlank()) {
            return true;
        }

        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            return true;
        }

        if (dto.getNewPasswordConfirm() != null && !dto.getNewPasswordConfirm().isBlank()) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves the profile information of a user by their username.
     * When a user is logged in, they can view the profiles of other users by clicking on their username.
     *
     * @param username the username of the user whose profile is to be displayed.
     * @return a UserGeneralDTO containing general profile information of the specified user.
     */
    @Override
    public UserGeneralDTO loadUser(String username) {

        BusinessException businessException = new BusinessException();

        if (!isUsernameValid(username, businessException)) {
            throw businessException;
        }

        try {
            User user = userDAO.readByUsername(username);
            return userToUserGeneralDTOConverter.convert(user);
        } catch (EmptyResultDataAccessException e) {
            businessException.addKey(BusinessCode.USER_NOT_FOUND);
            throw businessException;
        }

    }


    @Override
    public void changePassword(String email, String newPassword) {
        // TODO
        userDAO.updatePassword(email, newPassword);
    }

    /**
     * Deletes a user account by its ID.
     *
     * <p>
     * This method is used when a logged-in user chooses to delete their own account,
     * or by an administrator to delete another user's account.
     * </p>
     *
     * @param userId the ID of the user to be deleted.
     */
    @Override
    public void deleteUser(int userId) {
        // TODO : contraintes de clés étrangères Articles et Enchères
        //TODO: vérifier userId correspond à user connecté

        User user = userDAO.readById(userId);
        if (user == null) {
            BusinessException businessException = new BusinessException();
            businessException.addKey(BusinessCode.USER_NOT_FOUND);
            throw businessException;
        }
        userDAO.delete(userId);
    }

    @Override
    public User getByUsername(String username) {
        return userDAO.readByUsername(username);
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
