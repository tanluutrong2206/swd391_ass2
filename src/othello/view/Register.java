package othello.view;

import othello.controller.AccountController;
import othello.entities.Account;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Register {
    private JPanel panel1;
    private JTextField txtUsername;
    private JPasswordField txtPwd;
    private JPasswordField txtConfirmPwd;
    private JButton btnRegister;
    private JButton btnReset;
    private JFrame frame;

    private AccountController accountController;

    public Register() {
        accountController = new AccountController();
        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtUsername.setText("");
                txtPwd.setText("");
                txtConfirmPwd.setText("");
            }
        });
        btnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (String.valueOf(txtPwd.getPassword()).equals(String.valueOf(txtConfirmPwd.getPassword()))) {
                    // create account
                    Account account = new Account(txtUsername.getText().trim(), String.valueOf(txtPwd.getPassword()));
                    try {
                        int result = accountController.Register(account);
                        if(result < 0) {
                            // create account fail
                            // show message
                            showMessageDialog("Something went wrong :( Please try again later");
                        } else {
                            // create successfully
                            // show message
                            account.setId(result);
                            showMessageDialog("Create account successfully.");

                            // redirect to main
                            dispose();
                            new JoinGame().run(account);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //show message error
                        showMessageDialog("Something went wrong :( Please try again later");
                    }
                } else {
                    // display message password is not same
                    showMessageDialog("Please ensure that your password and confirm password is the same!");
                }
            }
        });
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(panel1, message);
    }

    public void run() {
        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void dispose() {
        frame.dispose();
    }
}
