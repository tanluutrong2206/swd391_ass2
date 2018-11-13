package othello.view;

import othello.entities.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class JoinGame {
    private JPanel menuPanel;
    private JTextField txtAddress;
    private JTextField txtPort;
    private JButton btnGame;
    private JPanel panel1;
    private JFrame frame;

    private Account account;

    public JoinGame() {
        btnGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                final String address = txtAddress.getText().trim();
                final int port = convertStringToInt(txtPort.getText().trim());

                if (address == null || address.isEmpty() || port < 0) {
                    // invalid so create as server side
                    //                        new OthelloServer(account);
                    dispose();
//                        server.initGUI();
//                        server.handleLogic();
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            OthelloServer server = null;
                            try {
                                server = new OthelloServer();
                            server.run(account);
//                                server.run();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        }
                    });
//                        server.handleLogic();
                } else {
                    dispose();
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new OthelloClient().run(address, port, account);
                        }
                    });
//                        new OthelloClient().initGUI();
                }
            }
        });
    }

    private void dispose() {
        this.frame.dispose();
    }

    public void run(Account account) {
        this.account = account;
        frame = new JFrame("Join Game");

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
    }

    private int convertStringToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return -1;
        }
    }
}
