package othello.interfaces;

import othello.entities.Account;

import java.sql.SQLException;

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
}
