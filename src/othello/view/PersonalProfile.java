package othello.view;

import othello.controller.HistoryController;
import othello.entities.Account;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PersonalProfile {
    private JPanel menuPanel;
    private JTextField txtUsername;
    private JTextField txtWinGame;
    private JTextField txtTotalGame;
    private JTextField txtWinRate;
    private JButton btnChangePass;
    private JPanel panel1;
    private JFrame frame;

    private Account account;

    public PersonalProfile() {
        btnChangePass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                dispose();

                new ChangePassword().run(account);
            }
        });

    }

    private void setInformation() {
        txtUsername.setText(account.getUsername());

        HistoryController historyController = new HistoryController();
        try {
            int totalGames = historyController.getTotalGames(account.getId());
            int totalWinGames = historyController.getTotalWinGames(account.getId());

            txtTotalGame.setText(Integer.toString(totalGames));
            txtWinGame.setText(Integer.toString(totalWinGames));
            txtWinRate.setText(Double.toString((totalWinGames/totalGames)*100));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            // alert error message
        }
    }

    public void run(Account account) {
        this.account = account;

        frame = new JFrame("Personal Profile");

        //setup menu, parameter is account
        MenubarGame menubarGame = new MenubarGame(account);
        menubarGame.createMenu(frame);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        setInformation();
    }

    private void dispose() {
        this.frame.dispose();
    }
}
