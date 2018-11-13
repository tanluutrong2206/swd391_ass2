package othello.view;

import othello.controller.LogicGame;
import othello.entities.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
        accountM.addSeparator();

        accountM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PersonalProfile personalProfile = new PersonalProfile();
                f.dispose();
                personalProfile.run(account);
            }
        });

        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryMatch historyMatch = new HistoryMatch();
                f.dispose();
                historyMatch.run(account);
            }
        });

        rank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ranking ranking = new Ranking();
                f.dispose();
                ranking.run(account);
            }
        });
        newItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new JoinGame().run(account);
            }

        });
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
