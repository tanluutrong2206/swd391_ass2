package othello.controller;

import othello.entities.Account;
import othello.entities.History;
import othello.models.AccountModel;
import othello.models.HistoryModel;

import java.sql.SQLException;
import java.util.List;

public class HistoryController {
    private HistoryModel historyModel;

    public HistoryController() {
        historyModel = new HistoryModel();
    }

    public int getTotalGames(int userId) throws SQLException {
        return this.historyModel.getTotalGames(userId);
    }

    public int getTotalWinGames(int userId) throws SQLException {
        return this.historyModel.getTotalWinGames(userId);
    }

    public void createHistoryGame(int windUserId, int loseUserId) throws SQLException {
        this.historyModel.createHistoryGame(windUserId, loseUserId);
    }

    public List<History> getLastestMatch(int topSelected, int userId) throws SQLException {
        return this.historyModel.getLastestMatch(topSelected, userId);
    }
}
