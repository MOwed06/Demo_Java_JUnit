package demo.mowed.responses;

import demo.mowed.core.TransactionType;
import demo.mowed.utils.TimeHelper;

import java.time.LocalDateTime;

public record TransactionOverviewRecord(int key, LocalDateTime transactionDateTime, TransactionType transactionType, Float transactionAmount, Integer bookKey, Integer purchaseQuantity) {

    @Override
    public String toString() {
        if (TransactionType.DEPOSIT.equals(transactionType)) {
            return String.format("Key: %d, %s, Date: %s, Amount: %f",
                    key,
                    transactionType.toString(),
                    TimeHelper.formatDateTime(transactionDateTime),
                    transactionAmount);
        }
        // book purchase
        return String.format("Key: %d, %s, Date: %s, Amount: %f, Book: %d, Qty: %d",
                key,
                transactionType.toString(),
                TimeHelper.formatDateTime(transactionDateTime),
                transactionAmount,
                bookKey,
                purchaseQuantity);
    }
}
