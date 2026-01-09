package demo.mowed.models;

import demo.mowed.core.Genre;

/**
 * A detailed view of a books info
 * loosely equivalent to BigBooks.API, BookDetailsDto.cs
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
        return String.format("Key: %d, Title: %s, Author: %s, Genre: %s, Price: %f",
                this.key,
                this.title,
                this.author,
                this.genre().toString(),
                this.price);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BookDetailsRecord(int key,
                                             String title,
                                             String author,
                                             String isbn,
                                             String description,
                                             Genre genre,
                                             float price,
                                             boolean isAvailable,
                                             Float rating,
                                             int reviewCount)){
            // TODO ~ complete this
            return (key == this.key)
                    && title.equals(this.title)
                    && author.equals(this.author)
                    && isbn.equals(this.isbn);

        }
        return false;
    }
}
