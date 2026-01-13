package demo.mowed.responses;

import demo.mowed.utils.TimeHelper;

import java.time.LocalDateTime;

public record BookReviewRecord(int key, String title, int score, LocalDateTime reviewDateTime, String description) {

    @Override
    public String toString() {
        return String.format("Key: %d, Title: %s, Score: %d, ReviewDate: %s, %s",
                key,
                title,
                score,
                TimeHelper.formatDateTime(reviewDateTime),
                description);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BookReviewRecord) {
            BookReviewRecord compare = (BookReviewRecord)obj;
            return (compare.key == this.key)
                    && compare.title.equals(this.title)
                    && (compare.score == this.score)
                    && compare.reviewDateTime.equals(this.reviewDateTime)
                    && String.valueOf(compare.description).equals(String.valueOf(this.description)); // extra boxing for null handling
        }
        return false;
    }
}
