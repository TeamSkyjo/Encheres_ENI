package fr.tp.eni.encheresskyjo.bo;

import java.util.Objects;

public class User {

    private int userId;
    private String username;
    private String lastName;
    private String firstName;
    private String email;
    private String telephone;
    private String street;
    private String zip;
    private String city;
    private String password;
    private int credit;
    private boolean admin;

    public User() {
    }

    public User(String username, String lastName, String firstName, String email, String telephone, String street, String zip, String city, String password, int credit, boolean admin) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.telephone = telephone;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.password = password;
        this.credit = credit;
        this.admin = admin;
    }

    public User(int userId, String username, String lastName, String firstName, String email, String telephone, String street, String zip, String city, String password, int credit, boolean admin) {
        this.userId = userId;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.telephone = telephone;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.password = password;
        this.credit = credit;
        this.admin = admin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("userId=").append(userId);
        sb.append(", username='").append(username).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", admin=").append(admin);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
