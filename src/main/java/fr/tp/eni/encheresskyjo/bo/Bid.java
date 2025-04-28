package fr.tp.eni.encheresskyjo.bo;

import java.time.LocalDate;
import java.util.Objects;

public class Bid {

    private LocalDate bidDate;
    private int bidPrice;
    private User buyer;
    private Article article;

    public Bid() {
    }

    public Bid(LocalDate bidDate, int bidPrice, User buyer, Article article) {
        this.bidDate = bidDate;
        this.bidPrice = bidPrice;
        this.buyer = buyer;
        this.article = article;
    }

    public LocalDate getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDate bidDate) {
        this.bidDate = bidDate;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Bid{");
        sb.append("bidDate=").append(bidDate);
        sb.append(", bidPrice=").append(bidPrice);
        sb.append(", buyer=").append(buyer);
        sb.append(", article=").append(article);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return bidPrice == bid.bidPrice && Objects.equals(bidDate, bid.bidDate) && Objects.equals(buyer, bid.buyer) && Objects.equals(article, bid.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bidDate, bidPrice, buyer, article);
    }
}
