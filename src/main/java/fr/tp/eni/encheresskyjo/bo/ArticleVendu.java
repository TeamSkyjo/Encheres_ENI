package fr.tp.eni.encheresskyjo.bo;

import java.time.LocalDate;
import java.util.Objects;

public class ArticleVendu {
    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int miseAPrix;
    private int prixVente;
    private String etatVente;

    private Utilisateur vendeur;
    private Categorie categorieArticle;
    private Retrait retrait;

    public ArticleVendu() {
    }

    public ArticleVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseAPrix, int prixVente, String etatVente) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
    }

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseAPrix, int prixVente, String etatVente) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(int miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }

    public Utilisateur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Utilisateur vendeur) {
        this.vendeur = vendeur;
    }

    public Categorie getCategorieArticle() {
        return categorieArticle;
    }

    public void setCategorieArticle(Categorie categorieArticle) {
        this.categorieArticle = categorieArticle;
    }

    public Retrait getRetrait() {
        return retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ArticleVendu{");
        sb.append("noArticle=").append(noArticle);
        sb.append(", nomArticle='").append(nomArticle).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dateDebutEncheres=").append(dateDebutEncheres);
        sb.append(", dateFinEncheres=").append(dateFinEncheres);
        sb.append(", miseAPrix=").append(miseAPrix);
        sb.append(", prixVente=").append(prixVente);
        sb.append(", etatVente='").append(etatVente).append('\'');
        sb.append(", vendeur=").append(vendeur);
        sb.append(", categorieArticle=").append(categorieArticle);
        sb.append(", retrait=").append(retrait);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArticleVendu that = (ArticleVendu) o;
        return noArticle == that.noArticle;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(noArticle);
    }
}
