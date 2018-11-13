package othello.interfaces;

import java.sql.SQLException;

public interface IHistory {
    int getTotalGames(int userId) throws SQLException;
    int getTotalWinGames(int userId) throws SQLException;
    void createHistoryGame(int windUserId, int loseUserId) throws SQLException;
}
