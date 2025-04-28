package fr.tp.eni.encheresskyjo.bo;

import java.time.LocalDate;
import java.util.Objects;

public class Article {
    private int articleId;
    private String articleName;
    private String description;
    private LocalDate bidStartDate;
    private LocalDate bidEndDate;
    private int startingPrice;
    private int sellingPrice;
    private String status;

    private User seller;
    private Category category;
    private Pickup pickup;

    public Article() {
    }

    public Article(String articleName, String description, LocalDate bidStartDate, LocalDate bidEndDate, int startingPrice, int sellingPrice, String status) {
        this.articleName = articleName;
        this.description = description;
        this.bidStartDate = bidStartDate;
        this.bidEndDate = bidEndDate;
        this.startingPrice = startingPrice;
        this.sellingPrice = sellingPrice;
        this.status = status;
    }

    public Article(int articleId, String articleName, String description, LocalDate bidStartDate, LocalDate bidEndDate, int startingPrice, int sellingPrice, String status) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.description = description;
        this.bidStartDate = bidStartDate;
        this.bidEndDate = bidEndDate;
        this.startingPrice = startingPrice;
        this.sellingPrice = sellingPrice;
        this.status = status;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBidStartDate() {
        return bidStartDate;
    }

    public void setBidStartDate(LocalDate bidStartDate) {
        this.bidStartDate = bidStartDate;
    }

    public LocalDate getBidEndDate() {
        return bidEndDate;
    }

    public void setBidEndDate(LocalDate bidEndDate) {
        this.bidEndDate = bidEndDate;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Pickup getPickup() {
        return pickup;
    }

    public void setPickup(Pickup pickup) {
        this.pickup = pickup;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Article{");
        sb.append("articleId=").append(articleId);
        sb.append(", articleName='").append(articleName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", bidStartDate=").append(bidStartDate);
        sb.append(", bidEndDate=").append(bidEndDate);
        sb.append(", startingPrice=").append(startingPrice);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", status='").append(status).append('\'');
        sb.append(", seller=").append(seller);
        sb.append(", category=").append(category);
        sb.append(", pickup=").append(pickup);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Article that = (Article) o;
        return articleId == that.articleId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(articleId);
    }
}
