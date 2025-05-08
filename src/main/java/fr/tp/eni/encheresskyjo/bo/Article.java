package fr.tp.eni.encheresskyjo.bo;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Objects;

public class Article {
    private int articleId;
    private String articleName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int startingPrice;
    private int sellingPrice;
    private int bestPrice;
    private String imageUrl;

    private User seller;
    private Category category;
    private Pickup pickup;

    // Dynamic attribute
    public ArticleStatus readStatus() {
        LocalDate now = LocalDate.now();
        if (now.isBefore(this.startDate)) {
            return ArticleStatus.NOT_STARTED;
        } else if (now.isAfter(this.endDate)) {
            return ArticleStatus.ENDED;
        } else {
            return ArticleStatus.ONGOING;
        }
    }

    public Article() {
    }

    public Article(String articleName, String description, LocalDate startDate, LocalDate endDate, int startingPrice, int sellingPrice, int bestPrice, String imageUrl) {
        this.articleName = articleName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.sellingPrice = sellingPrice;
        this.imageUrl = imageUrl;
    }

    public Article(int articleId, String articleName, String description, LocalDate startDate, LocalDate endDate, int startingPrice, int sellingPrice, int bestPrice, String imageUrl) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.sellingPrice = sellingPrice;
        this.imageUrl = imageUrl;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getBestPrice() {
        return bestPrice;
    }

    public void setBestPrice(int bestPrice) {
        this.bestPrice = bestPrice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Article{");
        sb.append("articleId=").append(articleId);
        sb.append(", articleName='").append(articleName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", startingPrice=").append(startingPrice);
        sb.append(", bestPrice=").append(bestPrice);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", imageUrl='").append(imageUrl).append('\'');
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
