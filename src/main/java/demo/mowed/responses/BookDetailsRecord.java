package demo.mowed.responses;

import demo.mowed.core.Genre;
import demo.mowed.utils.StringHelper;

/**
 * A detailed view of a book's info
 */
public record BookDetailsRecord(int key,
                                String title,
                                String author,
                                String isbn,
                                String description,
                                Genre genre,
                                float price,
                                boolean isAvailable,
                                Float rating,
                                int reviewCount) {

    @Override
    public String toString() {
        return String.format("Key: %d, Title: %s, Author: %s, Genre: %s, Price: %s, Available: %b, Rating: %f, Reviews: %d",
                this.key,
                this.title,
                this.author,
                this.genre().toString(),
                StringHelper.floatToCurrency(this.price),
                this.isAvailable,
                this.rating,
                this.reviewCount);
    }
}
