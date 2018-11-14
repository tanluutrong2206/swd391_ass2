package othello.controller;

import othello.entities.Account;
import othello.models.AccountModel;

import java.sql.SQLException;
import java.util.List;

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

    public int Update(Account account) throws SQLException {
        return this.account.Update(account);
    }

    public List<Account> getTopWonGames(int top) throws SQLException {
        return this.account.GetTopWonGames(top);
    }

    public List<Account> getTopWinRate(int top) throws SQLException {
        return this.account.GetTopWinRate(top);
    }
}
