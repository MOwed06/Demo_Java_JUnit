package demo.mowed.models;

import demo.mowed.core.Genre;

public class BookAddUpdateDto {
    private String title;
    private String author;
    private String isbn;
    private String description;
    private Genre genre;
    private float price;
    private int stockQuantity;

    public BookAddUpdateDto() {
    }

    public BookAddUpdateDto(String title, String author, String isbn, String description, Genre genre, float price, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
