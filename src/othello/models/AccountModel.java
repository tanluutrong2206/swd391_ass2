package othello.models;

import othello.dbConnection.DbConnection;
import othello.entities.Account;
import othello.interfaces.IAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountModel implements IAccount {
    private static final String TABLE_NAME = "Accounts";
    private static final String USERNAME_COL = "username";
    private static final String PASSWORD_COL = "password";
    private static final String ID_COL = "id";

    @Override
    public int Login(Account account) throws SQLException {
        String query = String.format("select * from %s where %s = ? and %s = ?", TABLE_NAME, USERNAME_COL, PASSWORD_COL);

        int result = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (account.getPassword() == null | account.getUsername() == null) {
            return -1;
        }

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            System.out.println(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getInt(ID_COL);
            }
            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
        } catch (Exception e) {
            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int Create(Account account) throws SQLException {
        if (account.getPassword() == null | account.getUsername() == null) {
            return -1;
        }
        if (isExistAccount(account.getUsername())) {
            return -1;
        }
        String query = String.format("INSERT INTO %s(%s, %s) VALUES(?, ?)  SELECT SCOPE_IDENTITY()", TABLE_NAME, USERNAME_COL, PASSWORD_COL);
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = -1;

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
        } catch (Exception e) {
            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
            e.printStackTrace();
        }
        return result;
    }

    private boolean isExistAccount(String username) throws SQLException {
        String query = String.format("select count(*) from %s where %s = ? LIMIT 1", TABLE_NAME, USERNAME_COL);
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = -1;

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setString(1, username);
            rs = ps.executeQuery();

            while (rs.next()) {
                result = rs.getInt(1);
            }

            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
        } catch (Exception e) {
            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
            e.printStackTrace();
        }

        return result <= 0;
    }

    public int Update(Account account) throws SQLException {

        String query = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLE_NAME, PASSWORD_COL, ID_COL);
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setString(1, account.getPassword());
            ps.setInt(2, account.getId());
            result = ps.executeUpdate();

            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
        } catch (Exception e) {
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Account> GetTopWonGames(int top) throws SQLException {
        String query = "  select top(?) a.username, count(h.playerWinId) as [number_win], h.playerWinId from History h inner join Accounts a on h.playerWinId = a.id\n" +
                "  group by h.playerWinId, a.username\n" +
                "  order by number_win DESC";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> accounts = new ArrayList<>();

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setInt(1, top);
            rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                int numberWonGames = rs.getInt("number_win");

                accounts.add(new Account(username, numberWonGames));
            }

            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
        } catch (Exception e) {
            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public List<Account> GetTopWinRate(int top) throws SQLException {
        String query = "\n" +
                "  select top(?) a.username, count(h.playerWinId)/(select count(*) from History h1 where h1.playerLoseId = h.playerWinId or h1.playerWinId = h.playerWinId) as [win_rate], h.playerWinId from History h inner join Accounts a on h.playerWinId = a.id\n" +
                "  group by h.playerWinId, a.username\n" +
                "  order by win_rate DESC";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> accounts = new ArrayList<>();

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setInt(1, top);
            rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                double numberWinRate = rs.getDouble("win_rate");

                accounts.add(new Account(username, numberWinRate));
            }

            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
        } catch (Exception e) {
            DbConnection.closeResultSet(rs);
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
            e.printStackTrace();
        }

        return accounts;
    }
}
