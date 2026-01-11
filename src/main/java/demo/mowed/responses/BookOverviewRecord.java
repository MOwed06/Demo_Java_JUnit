package demo.mowed.responses;

import demo.mowed.core.Genre;

/**
 * A summary view of a books info
 * (typically returned as an element within a list)
 */
public record BookOverviewRecord(int key, String title, String author, Genre genre) {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BookOverviewRecord) {
            BookOverviewRecord compare = (BookOverviewRecord)obj;
            return (compare.key == this.key)
                    && compare.title.equals(this.title)
                    && compare.author.equals(this.author)
                    && (compare.genre.getCode() == this.genre.getCode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(key, title, author, genre);
    }

    @Override
    public String toString() {
        return String.format("Key: %d, Title: %s, Author: %s, Genre: %s",
                key,
                title,
                author,
                genre.toString());
    }
}
