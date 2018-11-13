package othello.view;

import othello.controller.AccountController;
import othello.entities.Account;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ranking {
    private Account account;
    private JPanel panel1;
    private JButton btnWonGames;
    private JButton btnWinRate;
    private JList list1;
    private JFrame frame;

    public Ranking() {
        btnWonGames.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AccountController accountController = new AccountController();
                List<Account> accounts;
                try {
                    accounts = accountController.getTopWonGames(15);

                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    for (Account acc:accounts) {
                        listModel.addElement(String.format("%s - %d", acc.getUsername(), acc.getWonGames()));
                    }

                    list1.setModel(listModel);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnWinRate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AccountController accountController = new AccountController();
                List<Account> accounts;
                try {
                    accounts = accountController.getTopWinRate(15);

                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    for (Account acc:accounts) {
                        listModel.addElement(String.format("%s - %.2f", acc.getUsername(), acc.getWinRate()*100).concat("%"));
                    }

                    list1.setModel(listModel);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void run(Account account) {
        this.account = account;

        frame = new JFrame("Ranking");

        //setup menu, parameter is account
        MenubarGame menubarGame = new MenubarGame(account);
        menubarGame.createMenu(frame);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setLocation(dim.width/2 + frame.getSize().width/2, dim.height/2 + frame.getSize().height/2);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        btnWonGames.doClick();
    }
}
