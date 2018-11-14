package othello.view;

import othello.controller.AccountController;
import othello.entities.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {
    private JPanel panel1;
    private JTextField txtUsername;
    private JPasswordField txtPwd;
    private JButton btnLogin;
    private JButton btnRegister;
    private JFrame frame;

    private AccountController accountController;

    public Login() {
        accountController = new AccountController();
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = txtUsername.getText().trim();
                String password = String.valueOf(txtPwd.getPassword());
                Account account = new Account(username, password);
                try {
                    int id = accountController.Login(account);
                    if (id >= 0) {
                        account.setId(id);
                        //forward to set address and port view
                        dispose();
                        new JoinGame().run(account);
//                        JOptionPane.showMessageDialog(panel1, "Login successful!");
                    } else {
                        JOptionPane.showMessageDialog(panel1, "Login failed!");
                    }
                } catch (Exception ex) {
                    //show error
                    JOptionPane.showMessageDialog(panel1, "Login error!");
                }
            }
        });
        btnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Register().run();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        new Login().run();
    }

    public void run() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setLocation(dim.width/2 + frame.getSize().width/2, dim.height/2 + frame.getSize().height/2);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void dispose() {
        frame.dispose();
    }
}
