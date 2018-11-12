package client;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class OthelloClient {

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

    public OthelloClient() {
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
                newgame();
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

                        temp+="Tôi: " + enterchat.getText() + "\n";
                        content.setText(temp);
                        oos.writeObject("chat," + enterchat.getText());
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
                        flat = true;// server da click
                        try {
                            boolean check = checkHang(a, b);
                            boolean check1 = checkCot(a, b);
                            boolean check2 = checkCheoXuong(a, b);
                            boolean check4 = checkCheoLen(a,b);
                            if (check2 == true || check1 == true || check == true || check4 == true) {
                                matrandanh[a][b] = 1;
                                bt[a][b].setBackground(Color.RED);
                                setEnableButton(false);
                            }
                            for (int i = 0; i < x; i++) {
                                for (int j = 0; j < y; j++){

                                    if(j==7) {
                                        System.out.println(matrandanh[i][j] + "\n");
                                    } else {
                                        System.out.println(matrandanh[i][j] + " ");
                                    }
                                }
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

  /*      for(int i = 0; i<x; i++) {
            for(int j =0; j<y; j++) {
                matrandanh[i][j]=0;
            }
        }*/

        bt[(x/2)-1][(y/2)-1].setBackground(Color.RED);
        bt[x/2][y/2].setBackground(Color.RED);
        bt[(x/2)-1][(y/2)].setBackground(Color.BLACK);
        bt[(x/2)][(y/2)-1].setBackground(Color.BLACK);
        bt[(x/2)][(y/2)-1].setEnabled(false);
        bt[x/2][y/2].setEnabled(false);
        bt[(x/2)-1][(y/2)].setEnabled(false);
        bt[(x/2)-1][(y/2)-1].setEnabled(false);
        matrandanh[(x/2)-1][(y/2)-1]=1;
        matrandanh[x/2][y/2] = 1;
        matrandanh[(x/2)-1][(y/2)] = 2;
        matrandanh[(x/2)][(y/2)-1]=2;



        try {
            socket = new Socket("127.0.0.1",1234);
            System.out.println("Da ket noi toi server!");
            os=socket.getOutputStream();
            is=socket.getInputStream();
            oos= new ObjectOutputStream(os);
            ois= new ObjectInputStream(is);
            while (true) {
//                boolean check = checkNextStep();
//                if(check==true) {
//                    oos.writeObject("tiep, danh");
//                    setEnableButton(true);
//                }
//                int cw =checkWin();
//                if(cw==1) {
//                    int m = JOptionPane.showConfirmDialog(f,
//                            "Ban da thang.Ban co muon choi lai khong?", "Thong bao",
//                            JOptionPane.YES_NO_OPTION);
//                    if (m == JOptionPane.YES_OPTION) {
//                        setVisiblePanel(p);
//                        newgame();
//                        try {
//                            oos.writeObject("newgame,123");
//                        } catch (IOException ie) {
//                            //
//                        }
//                    }
//                } if(cw==2) {
//                    int m = JOptionPane.showConfirmDialog(f,
//                            "Ban da thua.Ban co muon choi lai khong?", "Thong bao",
//                            JOptionPane.YES_NO_OPTION);
//                    if (m == JOptionPane.YES_OPTION) {
//                        setVisiblePanel(p);
//                        newgame();
//                        try {
//                            oos.writeObject("newgame,123");
//                        } catch (IOException ie) {
//                            //
//                        }
//                    }
//                }
                String stream = ois.readObject().toString();
                String[] data = stream.split(",");
//                if(data[0].equals("tiep")) {
//                    setEnableButton(false);
//                }
                if (data[0].equals("chat")) {
                    temp += "Khách:" + data[1] + '\n';
                    content.setText(temp);
                }
                if (data[0].equals("doihang")) {
                    int y0 = Integer.valueOf(data[1]);
                    int y1 = Integer.valueOf(data[2]);
                    int x0 = Integer.valueOf(data[3]);
                    for(int i=y0; i<=y1; i++) {
                        matrandanh[x0][i] = 2;
                        bt[x0][i].setBackground(Color.BLACK);
                        bt[x0][i].setEnabled(false);
                    }
                    setEnableButton(true);
                }
                if (data[0].equals("doicot")) {
                    int x0 = Integer.valueOf(data[1]);
                    int x1 = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=x0; i<=x1; i++) {
                        matrandanh[i][y0] = 2;
                        bt[i][y0].setBackground(Color.BLACK);
                        bt[i][y0].setEnabled(false);
                    }
                    setEnableButton(true);
                }
                if (data[0].equals("doiCheoXuong1")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=0; i<=k; i++) {
                        matrandanh[x0+i][y0+i] = 2;
                        bt[x0+i][y0+i].setBackground(Color.BLACK);
                        bt[x0+i][y0+i].setEnabled(false);
                    }
                    setEnableButton(true);
                }
                if (data[0].equals("doiCheoXuong2")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=0; i<=k; i++) {
                        matrandanh[x0-i][y0-i] = 2;
                        bt[x0-i][y0-i].setBackground(Color.BLACK);
                        bt[x0-i][y0-i].setEnabled(false);
                    }
                    setEnableButton(true);
                }
                if (data[0].equals("doiCheoLen1")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=0; i<=k; i++) {
                        matrandanh[x0+i][y0-i] = 2;
                        bt[x0+i][y0-i].setBackground(Color.BLACK);
                        bt[x0+i][y0-i].setEnabled(false);
                    }
                    setEnableButton(true);
                }
                if (data[0].equals("doiCheoLen2")) {
                    int x0 = Integer.valueOf(data[1]);
                    int k = Integer.valueOf(data[2]);
                    int y0 = Integer.valueOf(data[3]);
                    for(int i=0; i<=k; i++) {
                        matrandanh[x0-i][y0+i] = 2;
                        bt[x0-i][y0+i].setBackground(Color.BLACK);
                        bt[x0-i][y0+i].setEnabled(false);
                    }
                    setEnableButton(true);
                }
                if (data[0].equals("newgame")) {
                    newgame();
                }
            }
        } catch (Exception ie) {
            // ie.printStackTrace();
        } //finally {
        //      socket.close();
        //      serversocket.close();
        //}

    }

    public void newgame() {
        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++) {
                bt[i][j].setBackground(Color.LIGHT_GRAY);
                matran[i][j] = 0;
                matrandanh[i][j] = 0;
            }
        }
        setEnableButton(true);
    }

    public void setVisiblePanel(JPanel pHienthi) {
        f.add(pHienthi);
        pHienthi.setVisible(true);
        pHienthi.updateUI();// ......

    }

    public void setEnableButton(boolean b) {
        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++) {
                if (matrandanh[i][j] == 0)
                    bt[i][j].setEnabled(b);
            }
        }
    }

    //thuat toan tinh thang thua
    public boolean checkHang(int x0, int y0) throws IOException {
        boolean check = false;
        if((y0+1)==y) {
        } else if (matrandanh[x0][y0+1] == 2) {
            int k =y0+1;
            int i = y0+1;
            while(i<y) {
                if(matrandanh[x0][i] == 2) {
                    i++;
                } else {
                    k = i;
                    break;
                }
            }
            if(k==y){

            }
            else if((matrandanh[x0][k] == 1)) {
                check = true;
                oos.writeObject("doihang,"+String.valueOf(y0)+","+String.valueOf(k)+"," +String.valueOf(x0));
                for(int t=y0+1; t<k; t++) {
                    matrandanh[x0][t] = 1;
                    bt[x0][t].setBackground(Color.RED);
                    bt[x0][t].setEnabled(false);
                }
            }
        }
        if((y0-1)==-1) {
        } else if(matrandanh[x0][y0-1] == 2){
            int p =y0-1;
            int q = y0-1;
            while(p>=0) {
                if(matrandanh[x0][p] == 2) {
                    p--;
                } else {
                    q = p;
                    break;
                }
            }
            if(q==(-1)){

            }
            else if((matrandanh[x0][q] == 1)) {
                check =true;
                oos.writeObject("doihang,"+String.valueOf(q)+","+String.valueOf(y0)+"," +String.valueOf(x0));
                for(int t=q+1; t<=y0-1; t++) {
                    matrandanh[x0][t] = 1;
                    bt[x0][t].setBackground(Color.RED);
                    bt[x0][t].setEnabled(false);
                }
            }
        }

        return check;
    }

    public boolean checkCot(int x0, int y0) throws IOException {
        boolean check = false;
        if((x0+1)==x) {

        }
        else if(matrandanh[x0+1][y0] == 2) {
            int k =x0+1;
            int i = x0+1;
            while(i<y) {
                if(matrandanh[i][y0] == 2) {
                    i++;
                } else {
                    k = i;
                    break;
                }
            }
            if(k==x) {

            }
            else if( (matrandanh[k][y0] == 1)) {
                check = true;
                oos.writeObject("doicot,"+String.valueOf(x0)+","+String.valueOf(k)+"," +String.valueOf(y0));
                for(int t=x0+1; t<k; t++) {
                    matrandanh[t][y0] = 1;
                    bt[t][y0].setBackground(Color.RED);
                    bt[t][y0].setEnabled(false);
                }
            }
        }
        if((x0-1)==-1) {

        }
        else if(matrandanh[x0-1][y0] == 2){
            int p =x0-1;
            int q = x0-1;
            while(p>=0) {
                if(matrandanh[p][y0] == 2) {
                    p--;
                } else {
                    q = p;
                    break;
                }
            }
            if(q==(-1)){} else if((matrandanh[q][y0] == 1)) {
                check =true;
                oos.writeObject("doicot,"+String.valueOf(q)+","+String.valueOf(x0)+"," +String.valueOf(y0));
                for(int t=q+1; t<=x0-1; t++) {
                    matrandanh[t][y0] = 1;
                    bt[t][y0].setBackground(Color.RED);
                    bt[t][y0].setEnabled(false);
                }
            }
        }

        return check;
    }

    public boolean checkCheoXuong(int x0, int y0) throws IOException {
        boolean check = false;
        if((x0+1)==x || (y0+1) == y) {

        }
        else if(matrandanh[x0+1][y0+1] == 2) {
            int k =1;
            int i = 1;
            while((i+y0<y) && (x0+i)<x) {
                if(matrandanh[x0+i][y0+i] == 2) {
                    i++;
                } else {
                    k = i;
                    break;
                }
            }
            if((k+y0)==y || (k+x0)==x){} else if((matrandanh[x0+k][y0+k] == 1)) {
                check = true;
                oos.writeObject("doiCheoXuong1,"+String.valueOf(x0)+","+String.valueOf(k)+"," +String.valueOf(y0));
                for(int t=1; t<k; t++) {
                    matrandanh[x0+t][y0+t] = 1;
                    bt[x0+t][y0+t].setBackground(Color.RED);
                    bt[x0+t][y0+t].setEnabled(false);
                }
            }
        }
        if((x0-1)==-1 || (y0-1) == -1) {

        }
        else if(matrandanh[x0-1][y0-1] == 2){
            int p =1;
            int q = 1;
            while((y0-p>=0) && ((x0-p)>=0)) {
                if(matrandanh[x0-p][y0-p] == 2) {
                    p++;
                } else {
                    q = p;
                    break;
                }
            }
            if((x0-q)==(-1) || (y0-q)==(-1)){} else if((matrandanh[x0-q][y0-q] == 1)) {
                check =true;
                oos.writeObject("doiCheoXuong2,"+String.valueOf(x0)+","+String.valueOf(q)+"," +String.valueOf(y0));
                System.out.println("doiCheoXuong2,"+String.valueOf(x0)+","+String.valueOf(q)+"," +String.valueOf(y0));
                for(int t=1; t<q; t++) {
                    matrandanh[x0-t][y0-t] = 1;
                    bt[x0-t][y0-t].setBackground(Color.RED);
                    bt[x0-t][y0-t].setEnabled(false);
                }
            }
        }

        return check;
    }

    public boolean checkCheoLen(int x0, int y0) throws IOException {
        boolean check = false;
        if((x0+1)==x || (y0-1) == -1) {

        }
        else if(matrandanh[x0+1][y0-1] == 2) {
            int k =1;
            int i = 1;
            while((i+x0<x) && (y0-i)>=0) {
                if(matrandanh[x0+i][y0-i] == 2) {
                    i++;
                } else {
                    k = i;
                    break;
                }
            }
            if((k+x0)==x || (y0-k) ==-1){} else if((matrandanh[x0+k][y0-k] == 1)) {
                check = true;
                oos.writeObject("doiCheoLen1,"+String.valueOf(x0)+","+String.valueOf(k)+"," +String.valueOf(y0));
                for(int t=1; t<k; t++) {
                    matrandanh[x0+t][y0-t] = 1;
                    bt[x0+t][y0-t].setBackground(Color.RED);
                    bt[x0+t][y0-t].setEnabled(false);
                }
            }
        }
        if((x0-1)==-1 || (y0+1) == y) {

        }
        else if(matrandanh[x0-1][y0+1] == 2){
            int p =1;
            int q = 1;
            while(x0-p>=0 && (y0+p)<y) {
                if(matrandanh[x0-p][y0+p] == 2) {
                    p++;
                } else {
                    q = p;
                    break;
                }
            }
            if((x0-q)==(-1) || (y0+p)==y ){} else if((matrandanh[x0-q][y0+q] == 1)) {
                check =true;
                oos.writeObject("doiCheoLen2,"+String.valueOf(x0)+","+String.valueOf(q)+"," +String.valueOf(y0));
                for(int t=1; t<q; t++) {
                    matrandanh[x0-t][y0+t] = 1;
                    bt[x0-t][y0+t].setBackground(Color.RED);
                    bt[x0-t][y0+t].setEnabled(false);
                }
            }
        }

        return check;
    }

    public boolean checkLose() throws IOException {
        boolean check = true;
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                if(matrandanh[i][j] == 0 && checkHang(i,j) == true) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }
    public boolean checkNextStep() throws IOException {
        boolean check = true;
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                if(matrandanh[i][j] == 0 && checkHang(i,j) == false && checkCot(i,j) == false && checkCheoLen(i,j) == false && checkCheoXuong(i,j) == false) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }

    public int checkWin() throws IOException {
        if(checkStop()==true) {
            int dem1 = 0, dem2 = 0;
            for(int i=0; i<x; i++) {
                for(int j=0; j<y; j++) {
                    if(matrandanh[i][j] == 1) {
                        dem1++;
                    } else if(matrandanh[i][j] == 2) {
                        dem2++;
                    }
                }
            }
            if(dem1>=dem2) {
                return 1;
            } else {
                return 2;
            }
        }
        return 0;
    }

    public boolean checkStop() throws IOException {
        boolean check = true;
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                if(matrandanh[i][j] == 0) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }
    public static void main(String[] args) {
        new OthelloClient();
    }

}
