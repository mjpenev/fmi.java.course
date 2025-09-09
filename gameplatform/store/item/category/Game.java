package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game implements StoreItem {

    private String title;
    private BigDecimal price;
    private LocalDateTime releaseDate;
    private String genre;
    private double sumOfRatings = 0.0;
    private int ratings = 0;

    public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre) {
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.genre = genre;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public double getRating() {
        return this.sumOfRatings / this.ratings;
    }

    public String getGenre() {
        return this.genre;
    }

    @Override
    public LocalDateTime getReleaseDate() {
        return this.releaseDate;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void rate(double rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Not a valid rating!");
        }
        this.ratings++;
        this.sumOfRatings += rating;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
