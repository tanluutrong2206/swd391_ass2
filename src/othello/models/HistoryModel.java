package othello.models;

import othello.dbConnection.DbConnection;
import othello.entities.Account;
import othello.entities.History;
import othello.interfaces.IHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryModel implements IHistory {

    private static final String TABLE_NAME = "History";
    private static final String WIND_USER_ID = "playerWinId";
    private static final String LOSE_USER_ID = "playerLoseId";

    @Override
    public int getTotalGames(int userId) throws SQLException {
        String query = String.format("select COUNT(*) from %s where %s = ? or %s = ?", TABLE_NAME, WIND_USER_ID, LOSE_USER_ID);

        int result = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, userId);
            System.out.println(query);
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

    @Override
    public int getTotalWinGames(int userId) throws SQLException {
        String query = String.format("select COUNT(*) from %s where %s = ?", TABLE_NAME, WIND_USER_ID);

        int result = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            System.out.println(query);
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

    @Override
    public void createHistoryGame(int windUserId, int loseUserId) throws SQLException {
        String query = String.format("INSERT INTO %s(%s, %s) VALUES(?, ?)", TABLE_NAME, WIND_USER_ID, LOSE_USER_ID);
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setInt(1, windUserId);
            ps.setInt(2, loseUserId);
            ps.executeUpdate();

            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
        } catch (Exception e) {
            DbConnection.closePrepareStatement(ps);
            DbConnection.closeConn(con);
            e.printStackTrace();
        }
    }

    @Override
    public List<History> getLastestMatch(int topSelected, int userId) throws SQLException {
        String query = "SELECT TOP(?) a1.username as [lose_account]," +
                " a2.username as [win_account]," +
                " h.datePlayed," +
                " a2.id as [win_id]," +
                " a1.id as [lose_id]" +
                "  FROM [dbo].[History] h" +
                "  INNER JOIN Accounts a1 ON a1.id = h.playerLoseId" +
                "  INNER JOIN Accounts a2 ON a2.id = h.playerWinId" +
                "  WHERE h.playerWinId = ? OR h.playerLoseId = ?" +
                "  ORDER BY h.datePlayed DESC";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<History> histories = new ArrayList<>();

        try {
            con = DbConnection.connToMssql();
            ps = con.prepareStatement(query);

            ps.setInt(1, topSelected);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Account userWin = new Account(rs.getInt("win_id"), rs.getString("win_account"));
                Account userLose = new Account(rs.getInt("lose_id"), rs.getString("lose_account"));
                Date playedDate = rs.getDate("datePlayed");

                histories.add(new History(userWin, userLose, new java.util.Date(playedDate.getTime())));
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

        return histories;
    }


}
