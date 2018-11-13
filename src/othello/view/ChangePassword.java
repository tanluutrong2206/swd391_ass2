package othello.view;

import othello.controller.AccountController;
import othello.entities.Account;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangePassword {
    private JPanel panel1;
    private JTextField txtUsername;
    private JPasswordField txtPwd;
    private JPasswordField txtConfirmPwd;
    private JButton btnSave;
    private JFrame frame;

    private Account account;

    public ChangePassword() {
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                AccountController accountController = new AccountController();
                if (String.valueOf(txtPwd.getPassword()).equals(String.valueOf(txtConfirmPwd.getPassword()))) {
                    // update password
                    account.setPassword(String.valueOf(txtPwd.getPassword()));
                    try {
                        if(accountController.Update(account) == 0) {
                            // update fail
                            // show message
                            JOptionPane.showMessageDialog(panel1, "Something went wrong :( Please try again later");
                        } else {
                            // update successfully
                            // show message
                            JOptionPane.showMessageDialog(panel1, "Your password update successfully");
                            txtPwd.setText("");
                            txtConfirmPwd.setText("");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //show message error
                        JOptionPane.showMessageDialog(panel1, "Something went wrong :( Please try again later");
                    }
                } else {
                    // display message password is not same
                    JOptionPane.showMessageDialog(panel1, "Please ensure that your password and confirm password is the same!");
                }
            }
        });
    }

    public void run(Account account) {
        this.account = account;

        frame = new JFrame("Change password");

        //setup menu, parameter is account

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void dispose() {
        this.frame.dispose();
    }
}
