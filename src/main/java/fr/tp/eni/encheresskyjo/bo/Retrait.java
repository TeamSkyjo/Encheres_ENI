package fr.tp.eni.encheresskyjo.bo;

import java.util.Objects;

public class Retrait {

    private String rue;
    private String codePostal;
    private String ville;

    private ArticleVendu articleVendu;

    public Retrait() {
    }

    public Retrait(String rue, String codePostal, String ville) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public Retrait(String rue, String codePostal, String ville, ArticleVendu articleVendu) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.articleVendu = articleVendu;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Retrait{");
        sb.append("rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", articleVendu=").append(articleVendu);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Retrait retrait = (Retrait) o;
        return Objects.equals(rue, retrait.rue) && Objects.equals(codePostal, retrait.codePostal) && Objects.equals(ville, retrait.ville) && Objects.equals(articleVendu, retrait.articleVendu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rue, codePostal, ville, articleVendu);
    }
}
