package fr.tp.eni.encheresskyjo.dto;

/**
 * Data Transfer Object used to handle user input when updating a user profile.
 *
 * <p>
 *     This DTO extends general user information with additional fields specifically required for password updates,
 *     such as the current password, new password, and password confirmation.
 * </p>
 *
 * @author TeamSkyjo
 * @version 1.0
 */
public class UserUpdateDTO extends UserGeneralDTO {

    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;

    public UserUpdateDTO() {
        super();
    }

    public UserUpdateDTO(String username, String lastName, String firstName, String email, String telephone, String street, String zip, String city) {
        super(username, lastName, firstName, email, telephone, street, zip, city);
    }

    public UserUpdateDTO(String username, String lastName, String firstName, String email, String telephone, String street, String zip, String city, String currentPassword, String newPassword, String newPasswordConfirm) {
        super(username, lastName, firstName, email, telephone, street, zip, city);
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }


    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

}
