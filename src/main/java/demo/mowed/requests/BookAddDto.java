package demo.mowed.requests;

import demo.mowed.core.ApplicationConstants;
import demo.mowed.core.BookStoreException;
import demo.mowed.core.Genre;
import demo.mowed.utils.RandomHelper;

import java.util.ArrayList;
import java.util.List;

public class BookAddDto {
    private final int BOOK_TITLE_MIN_SIZE = 2;
    private final int BOOK_TITLE_MAX_SIZE = 150;
    private final int AUTHOR_MIN_SIZE = 4;
    private final int AUTHOR_MAX_SIZE = 100;
    private final int GUID_SIZE = 36;
    private final float PRICE_MIN = 0.01f;
    private final float PRICE_MAX = 1000.0f;
    private final int STOCK_MIN = 0;
    private final int STOCK_MAX = 1000;
    private final int DESCRIPTION_MAX_SIZE = 500;

    private String title;
    private String author;
    private String isbn;
    private String description;
    private Genre genre;
    private float price;
    private int stockQuantity;

    public BookAddDto() {
    }

    public BookAddDto(String title, String author, String isbn, String description, Genre genre, float price, int stockQuantity) {
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

    public void validate() {
        List<String> validationErrors = new ArrayList<>();

        if (ApplicationConstants.RANDOM_REPLACE.equals(title)) {
            title = RandomHelper.generatePhrase();
        }

        if (title == null) {
            validationErrors.add("title is required");
        } else if (title.length() < BOOK_TITLE_MIN_SIZE) {
            validationErrors.add("title length < " + BOOK_TITLE_MIN_SIZE);
        } else if (title.length() > BOOK_TITLE_MAX_SIZE) {
            validationErrors.add("title length < " + BOOK_TITLE_MAX_SIZE);
        }

        if (ApplicationConstants.RANDOM_REPLACE.equals(author)) {
            author = RandomHelper.generatePerson();
        }

        if (author == null) {
            validationErrors.add("author is required");
        } else if (author.length() < AUTHOR_MIN_SIZE) {
            validationErrors.add("author length < " + AUTHOR_MIN_SIZE);
        } else if (author.length() > AUTHOR_MAX_SIZE) {
            validationErrors.add("author length < " + AUTHOR_MAX_SIZE);
        }

        if (ApplicationConstants.RANDOM_REPLACE.equals(isbn)) {
            isbn = RandomHelper.generateGUID();
        }

        // a parse of string to Guid would be better, but ... excessive for this demo
        if (isbn == null) {
            validationErrors.add("isbn is required");
        } else if (isbn.length() != GUID_SIZE) {
            validationErrors.add("invalid isbn value " + isbn);
        }

        if (price < PRICE_MIN) {
            validationErrors.add("price < " + PRICE_MIN);
        } else if (price > PRICE_MAX) {
            validationErrors.add("price < " + PRICE_MAX);
        }

        if (stockQuantity < STOCK_MIN) {
            validationErrors.add("stockQuantity < " + STOCK_MIN);
        } else if (stockQuantity > STOCK_MAX) {
            validationErrors.add("stockQuantity < " + STOCK_MAX);
        }

        if (ApplicationConstants.RANDOM_REPLACE.equals(description)) {
            description = RandomHelper.generatePhrase();
        }

        if ((description != null) && (description.length() > DESCRIPTION_MAX_SIZE)) {
            validationErrors.add("description < " + DESCRIPTION_MAX_SIZE);
        }

        if (!validationErrors.isEmpty()) {
            // if any validation errors, throw exception
            throw new BookStoreException(String.join(", ", validationErrors));
        }
    }
}
