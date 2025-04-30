package fr.tp.eni.encheresskyjo.bo;

import java.util.Objects;

public class Pickup {

    private String street;
    private String zip;
    private String city;

    private Article article;

    public Pickup() {
    }

    public Pickup(String street, String zip, String city) {
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    public Pickup(String street, String zip, String city, Article article) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.article = article;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pickup{");
        sb.append("street='").append(street).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", article=").append(article);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pickup retrait = (Pickup) o;
        return Objects.equals(street, retrait.street) && Objects.equals(zip, retrait.zip) && Objects.equals(city, retrait.city) && Objects.equals(article, retrait.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, zip, city, article);
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
