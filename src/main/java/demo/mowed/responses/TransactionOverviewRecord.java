package demo.mowed.responses;

import demo.mowed.core.TransactionType;
import demo.mowed.utils.TimeHelper;

import java.time.LocalDateTime;

public record TransactionOverviewRecord(int key,
                                        LocalDateTime transactionDateTime,
                                        TransactionType transactionType,
                                        Float transactionAmount,
                                        Integer bookKey,
                                        Integer purchaseQuantity) {

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  TransactionOverviewRecord){
            TransactionOverviewRecord compare = (TransactionOverviewRecord)obj;

            if (TransactionType.PURCHASE.equals(this.transactionType)) {
                return (compare.key == this.key)
                        && compare.transactionDateTime.equals(this.transactionDateTime)
                        && compare.transactionType.equals(this.transactionType)
                        && compare.transactionAmount.equals(this.transactionAmount)
                        && compare.bookKey.equals(this.bookKey)
                        && compare.purchaseQuantity.equals(this.purchaseQuantity);
            }
            // for deposit, both bookKey and purchaseQuantity must be null
            return (compare.key == this.key)
                    && compare.transactionDateTime.equals(this.transactionDateTime)
                    && compare.transactionType.equals(this.transactionType)
                    && compare.transactionAmount.equals(this.transactionAmount)
                    && (compare.bookKey == null)
                    && (compare.purchaseQuantity == null);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(key, transactionDateTime, transactionType, transactionAmount, bookKey, purchaseQuantity);
    }
}
