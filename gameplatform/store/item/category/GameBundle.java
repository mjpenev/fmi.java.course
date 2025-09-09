package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class GameBundle implements StoreItem {

    private String title;
    private BigDecimal price;
    private LocalDateTime releaseDate;
    private Game[] games;
    private double sumOfRatings = 0.0;
    private int ratings = 0;

    public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games) {
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.games = games;
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
    @Override
    public LocalDateTime getReleaseDate() {
        return this.releaseDate;
    }
    public Game[] getGames() {
        return this.games;
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
    public void addGame(Game game) {
        Game[] newGames = new Game[this.games.length + 1];
        System.arraycopy(this.games, 0, newGames, 0, this.games.length);
        newGames[this.games.length] = game;
        this.games = newGames;
    }

}