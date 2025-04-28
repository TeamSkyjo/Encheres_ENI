package fr.tp.eni.encheresskyjo.bo;

import java.time.LocalDate;
import java.util.Objects;

public class Enchere {

    private LocalDate dateEnchere;
    private int montantEnchere;
    private Utilisateur acheteur;
    private ArticleVendu articleVendu;

    public Enchere() {
    }

    public Enchere(LocalDate dateEnchere, int montantEnchere, Utilisateur acheteur, ArticleVendu articleVendu) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.acheteur = acheteur;
        this.articleVendu = articleVendu;
    }

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public Utilisateur getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(Utilisateur acheteur) {
        this.acheteur = acheteur;
    }

    public ArticleVendu getArticleVendu() {
        return articleVendu;
    }

    public void setArticleVendu(ArticleVendu articleVendu) {
        this.articleVendu = articleVendu;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Enchere{");
        sb.append("dateEnchere=").append(dateEnchere);
        sb.append(", montantEnchere=").append(montantEnchere);
        sb.append(", acheteur=").append(acheteur);
        sb.append(", articleVendu=").append(articleVendu);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enchere enchere = (Enchere) o;
        return montantEnchere == enchere.montantEnchere && Objects.equals(dateEnchere, enchere.dateEnchere) && Objects.equals(acheteur, enchere.acheteur) && Objects.equals(articleVendu, enchere.articleVendu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateEnchere, montantEnchere, acheteur, articleVendu);
    }
}
