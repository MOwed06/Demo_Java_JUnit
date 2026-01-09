package demo.mowed.models;

import demo.mowed.core.Genre;

/**
 * A summary view of a books info
 * (typically returned as an element within a list)
 * loosely equivalent to BigBooks.API, BookOverviewDto.cs
 */
public record BookOverviewRecord(int key, String title, String author, Genre genre, Float rating, int reviewCount) {
}
