package demo.mowed.interfaces;

import demo.mowed.requests.AccountAddMessage;
import demo.mowed.requests.GetMessage;
import demo.mowed.responses.AccountDetailsRecord;

public interface IAccountService {
    AccountDetailsRecord getAccount(GetMessage request);

    AccountDetailsRecord addAccount(AccountAddMessage request);

    boolean checkUserExists(String userEmail);
}
