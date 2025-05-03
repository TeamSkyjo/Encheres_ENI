package fr.tp.eni.encheresskyjo.dto;

/**
 * Récupère les informations des Formulaires de Création et modification
 *
 * Contrôleur reçoit un UserCreateDTO
 * BLL valide les données, hashe mdp, transforme DTO en User, appelle userDAO.create(user)
 * DAO construit la requête SQL INSERT
 */
public class UserInputDTO {

    private String username;
    private String lastName;
    private String firstName;
    private String email;
    private String telephone;
    private String street;
    private String zip;
    private String city;
    private String password;
    private String passwordConfirm;

    public UserInputDTO() {
    }

    public UserInputDTO(String username, String lastName, String firstName, String email, String telephone, String street, String zip, String city, String password, String passwordConfirm) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.telephone = telephone;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        final StringBuffer sb = new StringBuffer("UserInputDTO{");
        sb.append("username='").append(username).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
