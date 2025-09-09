package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DLC implements StoreItem{

    private String title;
    private BigDecimal price;
    private LocalDateTime releaseDate;
    private Game game;
    private double sumOfRatings = 0.0;
    private int ratings = 0;
    public DLC(String title, BigDecimal price, LocalDateTime releaseDate, Game game) {
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.game = game;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public BigDecimal getPrice() {
        return this.getPrice();
    }

    @Override
    public double getRating() {
        return this.sumOfRatings / this.ratings;
    }

    @Override
    public LocalDateTime getReleaseDate() {
        return this.releaseDate;
    }

    public Game getGame() {
        return this.game;
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
            throw new IllegalArgumentException("Invalid rating!");
        }
        this.ratings++;
        this.sumOfRatings += rating;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
