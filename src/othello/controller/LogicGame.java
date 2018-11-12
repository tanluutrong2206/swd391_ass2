package othello.controller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LogicGame {
    public void addBoard(JButton[][] bt, int[][] matrandanh, int x, int y) {
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
    }

    public void setEnableButton(JButton[][] bt, int[][] matrandanh, boolean b,int x,int y) {
        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++) {
                if (matrandanh[i][j] == 0)
                    bt[i][j].setEnabled(b);
            }
        }
    }

    //thuat toan tinh thang thua
    public boolean checkHang(JButton[][] bt, int[][] matrandanh, int x, int y, int x0, int y0, ObjectOutputStream oos, int index1, int index2, Color color) throws IOException {
        boolean check = false;
        if((y0+1)==y) {
        } else if (matrandanh[x0][y0+1] == index1) {
            int k =y0+1;
            int i = y0+1;
            while(i<y) {
                if(matrandanh[x0][i] == index1) {
                    i++;
                } else {
                    k = i;
                    break;
                }
            }
            if(k==y){

            }
            else if((matrandanh[x0][k] == index2)) {
                check = true;
                oos.writeObject("doihang,"+String.valueOf(y0)+","+String.valueOf(k)+"," +String.valueOf(x0));
                for(int t=y0+1; t<k; t++) {
                    matrandanh[x0][t] = index2;
                    bt[x0][t].setBackground(color);
                    bt[x0][t].setEnabled(false);
                }
            }
        }
        if((y0-1)==-1) {
        } else if(matrandanh[x0][y0-1] == index1){
            int p =y0-1;
            int q = y0-1;
            while(p>=0) {
                if(matrandanh[x0][p] == index1) {
                    p--;
                } else {
                    q = p;
                    break;
                }
            }
            if(q==(-1)) {

            }
            else if ((matrandanh[x0][q] == index2)) {
                check =true;
                oos.writeObject("doihang,"+String.valueOf(q)+","+String.valueOf(y0)+"," +String.valueOf(x0));
                for(int t=q+1; t<=y0-1; t++) {
                    matrandanh[x0][t] = index2;
                    bt[x0][t].setBackground(color);
                    bt[x0][t].setEnabled(false);
                }
            }
        }

        return check;
    }

    public boolean checkCot(JButton[][] bt, int[][] matrandanh, int x, int y, int x0, int y0, ObjectOutputStream oos, int index1, int index2, Color color) throws IOException {
        boolean check = false;
        if((x0+1)==x) {

        }
        else if(matrandanh[x0+1][y0] == index1) {
            int k =x0+1;
            int i = x0+1;
            while(i<x) {
                if(matrandanh[i][y0] == index1) {
                    i++;
                } else {
                    k = i;
                    break;
                }
            }
            if(k==x) {

            }
            else if((matrandanh[k][y0] == index2)) {
                check = true;
                oos.writeObject("doicot,"+String.valueOf(x0)+","+String.valueOf(k)+"," +String.valueOf(y0));
                for(int t=x0+1; t<k; t++) {
                    matrandanh[t][y0] = index2;
                    bt[t][y0].setBackground(color);
                    bt[t][y0].setEnabled(false);
                }
            }
        }
        if((x0-1)==-1) {

        }
        else if(matrandanh[x0-1][y0] == index1){
            int p =x0-1;
            int q = x0-1;
            while(p>=0) {
                if(matrandanh[p][y0] == index1) {
                    p--;
                } else {
                    q = p;
                    break;
                }
            }
            if(q==(-1)) {

            }
            else if((matrandanh[q][y0] == index2)) {
                check =true;
                oos.writeObject("doicot,"+String.valueOf(q)+","+String.valueOf(x0)+"," +String.valueOf(y0));
                for(int t=q+1; t<=x0-1; t++) {
                    matrandanh[t][y0] = index2;
                    bt[t][y0].setBackground(color);
                    bt[t][y0].setEnabled(false);
                }
            }
        }

        return check;
    }

    public boolean checkCheoXuong(JButton[][] bt, int[][] matrandanh, int x, int y, int x0, int y0, ObjectOutputStream oos, int index1, int index2, Color color) throws IOException {
        boolean check = false;
        if((x0+1)==x || (y0+1) == y) {

        }
        else if(matrandanh[x0+1][y0+1] == index1) {
            int k =1;
            int i = 1;
            while((i+y0<y) && (x0+i)<x) {
                if(matrandanh[x0+i][y0+i] == index1) {
                    i++;
                } else {
                    k = i;
                    break;
                }
            }
            if((k+y0)==y || (k+x0)==x) {

            }
            else if( (matrandanh[x0+k][y0+k] == index2)) {
                check = true;
                oos.writeObject("doiCheoXuong1,"+String.valueOf(x0)+","+String.valueOf(k)+"," +String.valueOf(y0));
                for(int t=1; t<k; t++) {
                    matrandanh[x0+t][y0+t] = index2;
                    bt[x0+t][y0+t].setBackground(color);
                    bt[x0+t][y0+t].setEnabled(false);
                }
            }
        }
        if((x0-1)==-1 || (y0-1) == -1) {

        }
        else if(matrandanh[x0-1][y0-1] == index1){
            int p =1;
            int q = 1;
            while((y0-p>=0) && ((x0-p)>=0)) {
                if(matrandanh[x0-p][y0-p] == index1) {
                    p++;
                } else {
                    q = p;
                    break;
                }
            }
            if((x0-q)==(-1) || (y0-q)==(-1)) {

            } else if(matrandanh[x0-q][y0-q] == index2) {
                check =true;
                oos.writeObject("doiCheoXuong2,"+String.valueOf(x0)+","+String.valueOf(q)+"," +String.valueOf(y0));
                System.out.println("doiCheoXuong2,"+String.valueOf(x0)+","+String.valueOf(q)+"," +String.valueOf(y0));
                for(int t=1; t<q; t++) {
                    matrandanh[x0-t][y0-t] = index2;
                    bt[x0-t][y0-t].setBackground(color);
                    bt[x0-t][y0-t].setEnabled(false);
                }
            }
        }

        return check;
    }

    public boolean checkCheoLen(JButton[][] bt, int[][] matrandanh, int x, int y, int x0, int y0, ObjectOutputStream oos, int index1, int index2, Color color) throws IOException {
        boolean check = false;
        if((x0+1)==x || (y0-1) == -1) {

        }
        else if(matrandanh[x0+1][y0-1] == index1) {
            int k =1;
            int i = 1;
            while((i+x0<x) && (y0-i)>=0) {
                if(matrandanh[x0+i][y0-i] == index1) {
                    i++;
                } else {
                    k = i;
                    break;
                }
            }
            if((k+x0)==x || (y0-k) ==-1){

            } else if (matrandanh[x0+k][y0-k] == index2) {
                check = true;
                oos.writeObject("doiCheoLen1,"+String.valueOf(x0)+","+String.valueOf(k)+"," +String.valueOf(y0));
                for(int t=1; t<k; t++) {
                    matrandanh[x0+t][y0-t] = index2;
                    bt[x0+t][y0-t].setBackground(color);
                    bt[x0+t][y0-t].setEnabled(false);
                }
            }
        }
        if((x0-1)==-1 || (y0+1) == y) {

        }
        else if(matrandanh[x0-1][y0+1] == index1){
            int p =1;
            int q = 1;
            while(x0-p>=0 && (y0+p)<y) {
                if(matrandanh[x0-p][y0+p] == index1) {
                    p++;
                } else {
                    q = p;
                    break;
                }
            }
            if((x0-q)==(-1) || (y0+p)==y){

            }
            else if(matrandanh[x0-q][y0+q] == index2) {
                check =true;
                oos.writeObject("doiCheoLen2,"+String.valueOf(x0)+","+String.valueOf(q)+"," +String.valueOf(y0));
                for(int t=1; t<q; t++) {
                    matrandanh[x0-t][y0+t] = index2;
                    bt[x0-t][y0+t].setBackground(color);
                    bt[x0-t][y0+t].setEnabled(false);
                }
            }
        }

        return check;
    }

    public void setChat(String temp, JTextField enterchat, JTextArea content, ObjectOutputStream oos) throws IOException {
        temp+="Tôi: " + enterchat.getText() + "\n";
        content.setText(temp);
        oos.writeObject("chat," + enterchat.getText());
        enterchat.setText("");
        //temp = "";
        enterchat.requestFocus();
        content.setVisible(false);
        content.setVisible(true);
    }
}
