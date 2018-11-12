package othello.view;

import othello.controller.LogicGame;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class OthelloServer {

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

    //MenuBar
    MenuBar menubar;

    public OthelloServer() {
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
        LogicGame logicGame = new LogicGame();

        f.setMenuBar(menubar);// tao menubar cho frame
        Menu game = new Menu("Game");
        menubar.add(game);
        MenuItem newItem = new MenuItem("New Game");
        game.add(newItem);
        MenuItem exit = new MenuItem("Exit");
        game.add(exit);
        game.addSeparator();
        newItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    oos.writeObject("newgame,123");
                } catch (IOException ie) {
                    //
                }
            }

        });
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //khung chat
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
                        logicGame.setChat(temp,enterchat,content,oos);
                    }
                    catch (Exception r)
                    {
                        r.printStackTrace();
                    }
                }
            }
        });


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
                            boolean check = logicGame.checkHang(bt,matrandanh,x,y,a,b,oos,1,2, Color.BLACK);
                            boolean check1 = logicGame.checkCot(bt,matrandanh,x,y,a,b,oos,1,2, Color.BLACK);
                            boolean check2 = logicGame.checkCheoXuong(bt,matrandanh,x,y,a,b,oos,1,2, Color.BLACK);
                            boolean check4 = logicGame.checkCheoLen(bt,matrandanh,x,y,a,b,oos,1,2, Color.BLACK);
                            if (check2 == true || check1 == true || check == true || check4 == true) {
                                matrandanh[a][b] = 2;
                                bt[a][b].setBackground(Color.BLACK);
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




        try {
            serversocket = new ServerSocket(1234);
            System.out.println("Dang doi client...");
            socket = serversocket.accept();
            System.out.println("Client da ket noi!");
            os = socket.getOutputStream();
            is = socket.getInputStream();
            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);
            while (true) {
                String stream = ois.readObject().toString();
                String[] data = stream.split(",");
                if (data[0].equals("chat")) {
                    temp += "Khách:" + data[1] + '\n';
                    content.setText(temp);
                }
                if (data[0].equals("doihang")) {
                    int y0 = Integer.valueOf(data[1]);
                    int y1 = Integer.valueOf(data[2]);
                    int x0 = Integer.valueOf(data[3]);
                    for(int i=y0; i<=y1; i++) {
                        matrandanh[x0][i] = 1;
                        bt[x0][i].setBackground(Color.RED);
                        bt[x0][i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt,matrandanh,true,x,y);
                }
                if (data[0].equals("doicot")) {
                    int x0 = Integer.valueOf(data[1]);
                    int x1 = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=x0; i<=x1; i++) {
                        matrandanh[i][y0] = 1;
                        bt[i][y0].setBackground(Color.RED);
                        bt[i][y0].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt,matrandanh,true,x,y);
                }
                if (data[0].equals("doiCheoXuong1")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=0; i<=k; i++) {
                        matrandanh[x0+i][y0+i] = 1;
                        bt[x0+i][y0+i].setBackground(Color.RED);
                        bt[x0+i][y0+i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt,matrandanh,true,x,y);
                }
                if (data[0].equals("doiCheoXuong2")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=0; i<=k; i++) {
                        matrandanh[x0-i][y0-i] = 1;
                        bt[x0-i][y0-i].setBackground(Color.RED);
                        bt[x0-i][y0-i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt,matrandanh,true,x,y);
                }
                if (data[0].equals("doiCheoLen1")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=0; i<=k; i++) {
                        matrandanh[x0+i][y0-i] = 1;
                        bt[x0+i][y0-i].setBackground(Color.RED);
                        bt[x0+i][y0-i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt,matrandanh,true,x,y);
                }
                if (data[0].equals("doiCheoLen2")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=0; i<=k; i++) {
                        matrandanh[x0-i][y0+i] = 1;
                        bt[x0-i][y0+i].setBackground(Color.RED);
                        bt[x0-i][y0+i].setEnabled(false);
                    }
                    logicGame.setEnableButton(bt,matrandanh,true,x,y);
                }
            }
        } catch (Exception ie) {
        }
    }
    public static void main(String[] args) {
        new OthelloServer();
    }

}
