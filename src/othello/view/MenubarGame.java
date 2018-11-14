package othello.view;

import othello.entities.Account;

import javax.swing.*;
import java.awt.*;

public class MenubarGame {
    private Account account;
    public MenubarGame(Account account) {
        this.account = account;
    }
    public void createMenu(JFrame f) {
        MenuBar menubar = new MenuBar();
        f.setMenuBar(menubar);// tao menubar cho frame
        Menu game = new Menu("Game");
        menubar.add(game);
        MenuItem newItem = new MenuItem("New Game");
        game.add(newItem);
        MenuItem exit = new MenuItem("Exit");
        game.add(exit);
        game.addSeparator();
        Menu accountM = new Menu("Account");
        menubar.add(accountM);
        MenuItem profile = new MenuItem("Profile");
        accountM.add(profile);
        MenuItem history = new MenuItem("History");
        accountM.add(history);
        MenuItem rank = new MenuItem("Rank");
        accountM.add(rank);
        MenuItem logout = new MenuItem("Logout");
        accountM.add(logout);
        accountM.addSeparator();

        profile.addActionListener(e -> {
            PersonalProfile personalProfile = new PersonalProfile();
            f.dispose();
            personalProfile.run(account);
        });

        history.addActionListener(e -> {
            HistoryMatch historyMatch = new HistoryMatch();
            f.dispose();
            historyMatch.run(account);
        });

        rank.addActionListener(e -> {
            Ranking ranking = new Ranking();
            f.dispose();
            ranking.run(account);
        });
        newItem.addActionListener(e -> {
            f.dispose();
            new JoinGame().run(account);
        });
        exit.addActionListener(e -> System.exit(0));
    }
}
