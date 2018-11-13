package othello.models;

import othello.dbConnection.DbConnection;
import othello.interfaces.IHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
