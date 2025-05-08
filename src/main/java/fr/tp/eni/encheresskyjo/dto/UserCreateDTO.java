package fr.tp.eni.encheresskyjo.dto;

/**
 *
 * Data Transfer Object used to handle user input when creating a user.
 *
 * <p>
 *     This DTO extends general user information with additional fields required for validation before persisting the user,
 *     such as the password and password confirmation.
 * </p>
 * <p>
 *     <strong>Note:</strong> The {@code passwordConfirm} field is used only for validation purposes
 *     and is not mapped to the User business object or stored in the database.
 * </p>
 *
 * @author TeamSkyjo
 * @version 1.1
 */
public class UserCreateDTO extends UserGeneralDTO {

    private String password;
    private String passwordConfirm;

    public UserCreateDTO() {
        super();
    }

    public UserCreateDTO(String username, String lastName, String firstName, String email, String telephone, String street, String zip, String city) {
        super(username, lastName, firstName, email, telephone, street, zip, city);
    }

    public UserCreateDTO(String username, String lastName, String firstName, String email, String telephone, String street, String zip, String city, String password, String passwordConfirm) {
        super(username, lastName, firstName, email, telephone, street, zip, city);
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

}
