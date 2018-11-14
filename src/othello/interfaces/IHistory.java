package othello.interfaces;

import othello.entities.History;

import java.sql.SQLException;
import java.util.List;

public interface IHistory {
    int getTotalGames(int userId) throws SQLException;
    int getTotalWinGames(int userId) throws SQLException;
    void createHistoryGame(int windUserId, int loseUserId) throws SQLException;

    List<History> getLastestMatch(int topSelected, int userId) throws SQLException;
}
