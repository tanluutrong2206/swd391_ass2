package othello.interfaces;

import othello.entities.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccount {
    /**
     *
     * @param account
     * @return  id if login successfully else return -1
     * @throws SQLException
     */
    int Login(Account account) throws SQLException;

    /**
     *
     * @param account
     * @return id if create successfully else return -1
     * @throws SQLException
     */
    int Create(Account account) throws SQLException;
    int Update(Account account) throws SQLException;
    List<Account> GetTopWonGames(int top) throws SQLException;

    List<Account> GetTopWinRate(int top) throws SQLException;
}
