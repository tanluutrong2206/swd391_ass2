package othello.view;

import othello.entities.Account;

import javax.swing.*;

public class HistoryMatch {
    private Account account;

    private JFrame frame;

    public void run(Account account) {
        this.account = account;

        frame = new JFrame("History of your match");

        //setup menu, parameter is account
        MenubarGame menubarGame = new MenubarGame(account);
        menubarGame.createMenu(frame);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setContentPane(panel1);
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setLocation(dim.width/2 + frame.getSize().width/2, dim.height/2 + frame.getSize().height/2);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
