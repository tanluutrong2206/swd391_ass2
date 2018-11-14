package othello.view;

import othello.controller.LogicGame;
import othello.entities.Account;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class OthelloClient implements Runnable {
    private Account account;
    private String address = "127.0.0.1";
    private int port = 1234;

    public static JFrame f;
    JButton[][] bt;
    static boolean flat = false;
    boolean winner;
    JButton send;
    String temp="",strNhan = "";
    JTextArea content;
    JTextField nhap,enterchat;
    JPanel p;
    int xx, yy, x, y;
    int[][] matran;
    int[][] matrandanh;

    // Server Socket
    ServerSocket serversocket;
    Socket socket;
    OutputStream os;// ....
    InputStream is;// ......
    ObjectOutputStream oos;// .........
    ObjectInputStream ois;//

    private Thread thread;
    private LogicGame logicGame;
    //MenuBar
    MenuBar menubar;

    public OthelloClient() {
        thread = new Thread(this);
        logicGame = new LogicGame();
    }
//    public static void main(String[] args) throws IOException {
//        new OthelloClient().initGUI();
//    }

    public void run(String address, int port, Account account) {
        this.account = account;
        this.address = address;
        this.port = port;

        thread.start();
    }

    public void initGUI() {
        f = new JFrame();
        f.setTitle("Game Othello");
        f.setSize(750, 500);
        x = 8;
        y = 8;
        f.getContentPane().setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setResizable(false);

        matran = new int[x][y];
        matrandanh = new int[x][y];
        menubar = new MenuBar();
        p = new JPanel();
        p.setBounds(10, 30, 400, 400);
        p.setLayout(new GridLayout(x, y));
        f.add(p);
        logicGame = new LogicGame();

        MenubarGame menubarGame = new MenubarGame(account);
        menubarGame.createMenu(f);

        //khung chat
        Font fo = new Font("Arial",Font.BOLD,15);
        addChatArea();
        addButton();
        handleLogic();

    }

    private void handleLogic() {
        try {
            socket = new Socket(address, port);
            System.out.println("Da ket noi toi othello!");
            os=socket.getOutputStream();
            is=socket.getInputStream();
            oos= new ObjectOutputStream(os);
            ois= new ObjectInputStream(is);
            do {
                boolean check = logicGame.checkStep(bt, matrandanh, x, y, oos, 2, 1);
                if (check == false) {
                    logicGame.setEnableButton(bt, matrandanh, false, x, y);
                    System.out.println("ditiep");
                }
                String stream = ois.readObject().toString();
                String[] data = stream.split(",");
                if (data[0].equals("chat")) {
                    temp += data[1] + '\n';
                    content.setText(temp);
                }
                if (data[0].equals("ditiep")) {
                    logicGame.setEnableButton(bt, matrandanh, true, x, y);
                }
                if (data[0].equals("thua")) {
                    logicGame.dialogQuestionNewGame(bt, matrandanh, x, y, matran, false, oos, f, "thua");
                }
                if (data[0].equals("thang")) {
                    logicGame.dialogQuestionNewGame(bt, matrandanh, x, y, matran, false, oos, f, "thắng");
                }
                if (data[0].equals("hoa")) {
                    logicGame.dialogQuestionNewGame(bt, matrandanh, x, y, matran, false, oos, f, "hòa");
                }
                if (data[0].equals("doihang")) {
                    int y0 = Integer.valueOf(data[1]);
                    int y1 = Integer.valueOf(data[2]);
                    int x0 = Integer.valueOf(data[3]);
                    for (int i = y0; i <= y1; i++) {
                        matrandanh[x0][i] = 2;
                        bt[x0][i].setBackground(Color.BLACK);
                        bt[x0][i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt, matrandanh, true, x, y);
                }
                if (data[0].equals("doicot")) {
                    int x0 = Integer.valueOf(data[1]);
                    int x1 = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for (int i = x0; i <= x1; i++) {
                        matrandanh[i][y0] = 2;
                        bt[i][y0].setBackground(Color.BLACK);
                        bt[i][y0].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt, matrandanh, true, x, y);
                }
                if (data[0].equals("doiCheoXuong1")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for (int i = 0; i <= k; i++) {
                        matrandanh[x0 + i][y0 + i] = 2;
                        bt[x0 + i][y0 + i].setBackground(Color.BLACK);
                        bt[x0 + i][y0 + i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt, matrandanh, true, x, y);
                }
                if (data[0].equals("doiCheoXuong2")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for (int i = 0; i <= k; i++) {
                        matrandanh[x0 - i][y0 - i] = 2;
                        bt[x0 - i][y0 - i].setBackground(Color.BLACK);
                        bt[x0 - i][y0 - i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt, matrandanh, true, x, y);
                }
                if (data[0].equals("doiCheoLen1")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for (int i = 0; i <= k; i++) {
                        matrandanh[x0 + i][y0 - i] = 2;
                        bt[x0 + i][y0 - i].setBackground(Color.BLACK);
                        bt[x0 + i][y0 - i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt, matrandanh, true, x, y);
                }
                if (data[0].equals("doiCheoLen2")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for (int i = 0; i <= k; i++) {
                        matrandanh[x0 - i][y0 + i] = 2;
                        bt[x0 - i][y0 + i].setBackground(Color.BLACK);
                        bt[x0 - i][y0 + i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt, matrandanh, true, x, y);
                } else if (data[0].equals("newgame")) {
                    logicGame.newgame(bt, matrandanh, x, y, matran, false);
                }
                boolean checkWin = logicGame.checkWin(bt, matrandanh, x, y);
                System.out.println(checkWin);
                int dem1 = 0;
                int dem2 = 0;
                if (checkWin == true) {
                    for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                            if (matrandanh[i][j] == 1) {
                                dem1++;
                            }
                            if (matrandanh[i][j] == 2) {
                                dem2++;
                            }
                        }
                    }

                    if (dem2 < dem1) {
                        oos.writeObject("thua,");
                    }
                    if (dem1 < dem2) {
                        oos.writeObject("thang,,");
                    }
                    if (dem1 == dem2) {
                        oos.writeObject("hoa,");
                    }
                    break;

                }
            } while (true);
        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }

    private void addButton() {
        //button
        bt = new JButton[x][y];
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                final int a = i, b =j;
                bt[a][b] = new JButton();
                bt[a][b].setBackground(Color.LIGHT_GRAY);
                bt[a][b].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        flat = true;// othello da click
                        try {
                            boolean check = logicGame.checkHang(bt,matrandanh,x,y,a,b,oos,2,1, Color.RED);
                            boolean check1 = logicGame.checkCot(bt,matrandanh,x,y,a,b,oos,2,1, Color.RED);
                            boolean check2 = logicGame.checkCheoXuong(bt,matrandanh,x,y,a,b,oos,2,1, Color.RED);
                            boolean check4 = logicGame.checkCheoLen(bt,matrandanh,x,y,a,b,oos,2,1, Color.RED);
                            if (check2 == true || check1 == true || check == true || check4 == true) {
                                matrandanh[a][b] = 1;
                                bt[a][b].setBackground(Color.RED);
                                bt[a][b].setEnabled(false);
                                logicGame.setEnableButton(bt,matrandanh,false,x,y);
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                });
                p.add(bt[a][b]);
                p.setVisible(false);
                p.setVisible(true);
            }
        }

        logicGame.addBoard(bt,matrandanh,x,y);
    }

    private void addChatArea() {
        Font fo = new Font("Arial",Font.BOLD,15);
        content = new JTextArea();
        content.setFont(fo);
        content.setBackground(Color.white);

        content.setEditable(false);
        JScrollPane sp = new JScrollPane(content);
        sp.setBounds(430,170,300,180);
        send = new JButton("Gui");
        send.setBounds(640, 390, 70, 40);
        enterchat = new JTextField("");
        enterchat.setFont(fo);
        enterchat.setBounds(430, 400, 200, 30);
        enterchat.setBackground(Color.white);
        f.add(enterchat);
        f.add(send);
        f.add(sp);
        f.setVisible(true);
        send.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(send))
                {
                    try
                    {
                        temp += account.getUsername() + ": " + enterchat.getText() + "\n";
                        content.setText(temp);
                        oos.writeObject("chat," + account.getUsername().concat(": ").concat(enterchat.getText().trim()));
                        enterchat.setText("");
                        //temp = "";
                        enterchat.requestFocus();
                        content.setVisible(false);
                        content.setVisible(true);
                    }
                    catch (Exception r)
                    {
                        r.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void run() {
        initGUI();
    }
}
