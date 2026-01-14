package demo.mowed.responses;

import demo.mowed.utils.StringHelper;

import java.util.List;

public record AccountDetailsRecord (int key,
                                    String userEmail,
                                    boolean isAdmin,
                                    boolean isActive,
                                    float wallet,
                                    List<TransactionOverviewRecord> transactions) {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Key: %d, Email: %s, Admin: %b, Active: %b, Wallet: %s",
                key,
                userEmail,
                isAdmin,
                isActive,
                StringHelper.floatToCurrency(wallet)));
        if (!transactions.isEmpty()) {
            for (TransactionOverviewRecord t : transactions) {
                sb.append("\n");
                sb.append(t.toString());
            }
        }
        return sb.toString();
    }
}
