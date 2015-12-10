/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Administrator
 */
import javax.swing.JFrame;
import java.awt.Color;

public class FiveChessApp extends JFrame{
    public static void main(String[] args){
         FiveChessApp frame = new FiveChessApp();
         FiveChessControl fiveChessControl=new FiveChessControl();
         frame.add(fiveChessControl);
         frame.setTitle("五子棋");
         frame.setSize(740, 685);
         frame.setLocationRelativeTo(null);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
    }
}
