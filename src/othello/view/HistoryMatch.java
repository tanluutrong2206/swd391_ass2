package othello.view;

import othello.controller.HistoryController;
import othello.entities.Account;
import othello.entities.History;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoryMatch {
    private Account account;

    private JFrame frame;
    private JPanel panel1;
    private JScrollPane scroll1;
    private JTable table1;

    private final String[] headers = {"Opponent", "Status", "Played date time"};
    private final int TOP_SELECTED = 15;

    public void run(Account account) {
        this.account = account;

        frame = new JFrame("History of your match");

        //setup menu, parameter is account
        MenubarGame menubarGame = new MenubarGame(account);
        menubarGame.createMenu(frame);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        loadingLastestMatch(TOP_SELECTED);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private void loadingLastestMatch(int topSelected) {
        List<History> histories;
        try {
            histories = new HistoryController().getLastestMatch(topSelected, account.getId());

            System.out.println(histories.size());
            setTableHeader(setRowData(histories, account.getId()));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel1, "Something error :( Please try again later");
        }
    }

    private String[][] setRowData(List<History> histories, int userId) {
        String[][] rowData = new String[histories.size()][3];
        int i = 0;
        for (History history : histories) {
            if (history.getWinAcc().getId() == userId) {
                rowData[i++] = new String[]{history.getLoseAcc().getUsername(), "WIN", new SimpleDateFormat("dd-MM-yyyy").format(history.getPlayedDate())};
            } else {
                rowData[i++] = new String[]{history.getWinAcc().getUsername(), "LOSE", new SimpleDateFormat("dd-MM-yyyy").format(history.getPlayedDate())};
            }
        }
        return rowData;
    }

    private void setTableHeader(String[][] rowData) {
        table1 = new JTable(rowData, headers);
        table1.getColumnModel().getColumn(0).setMinWidth(200);
        JTableHeader header = table1.getTableHeader();
        header.setBackground(Color.yellow);

        header.setBackground(Color.yellow);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scroll1.getViewport().add(table1);
    }
}
