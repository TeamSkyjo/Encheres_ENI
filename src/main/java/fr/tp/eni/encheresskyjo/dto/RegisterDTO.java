package fr.tp.eni.encheresskyjo.dto;

/**
 * PAS UTILISE - INUTILE POUR LE MOMENT ?
 */
public class RegisterDTO {

    private String username;
    private String password;
    private String passwordConfirm;

    public RegisterDTO() {
    }

    public RegisterDTO(String username, String password, String passwordConfirm) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RegisterDTO{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", passwordConfirm='").append(passwordConfirm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
