package othello.controller;

import othello.entities.Account;
import othello.models.AccountModel;

import java.sql.SQLException;

public class AccountController {
    private AccountModel account;

    public AccountController() {
        account = new AccountModel();
    }

    /**
     *
     * @param account
     * @return account id if login successful else return -1
     */
    public int Login(Account account) throws SQLException {
        return this.account.Login(account);
    }

    public int Register(Account account) throws SQLException {
        return this.account.Create(account);
    }
}
