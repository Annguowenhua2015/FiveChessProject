/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
package game;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.JApplet;
import java.applet.AudioClip; 
import java.applet.Applet; 
import java.awt.Frame; 
import java.net.URL; 

public class FiveChessControl extends JPanel{
    
    private FiveChessPanel fiveChessPanel=new FiveChessPanel();
    private JButton jbtStart=new JButton("开始");    
    private JButton jbtLose=new JButton("认输");
    private JButton jbtPauseMusic = new JButton("停止音乐");
    boolean pause=true;
    private JButton jbtIntroduction=new JButton("游戏说明");
    private JButton jbtAbout=new JButton("关于");
    private JButton jbtExit=new JButton("退出");    
    private AudioClip audioClip;//创建一个名为audioClip的对象
    private AudioClip audioClip1;
    
        
    public FiveChessControl(){
        
        fiveChessPanel.setBackground(new Color(230,184,135));
        fiveChessPanel.setCanPlay(false);
        JPanel Panel=new JPanel();
        Panel.setLayout(new GridLayout(6,0));//让按钮从上到下垂直排列。
        jbtLose.setEnabled(false);//让“认输”按钮变灰;
        Panel.add(jbtStart);//在面板里添加“开始”按钮 
        jbtStart.setFont(new Font("宋体",Font.BOLD,20));
        Panel.add(jbtLose);//在面板里添加“认输”按钮
        jbtLose.setFont(new Font("宋体",Font.BOLD,20));
        Panel.add(jbtPauseMusic);//在面板里添加“停止音乐”按钮
        jbtPauseMusic.setFont(new Font("宋体",Font.BOLD,20));
        Panel.add(jbtIntroduction);//在面板里添加“游戏介绍”按钮
        jbtIntroduction.setFont(new Font("宋体",Font.BOLD,20));
        Panel.add(jbtAbout);//在面板里添加“关于”按钮
        jbtAbout.setFont(new Font("宋体",Font.BOLD,20));
        Panel.add(jbtExit);//在面板里添加“退出”按钮
        jbtExit.setFont(new Font("宋体",Font.BOLD,20));
        
        
        setLayout(new BorderLayout());//将Panel设置为BorderLayout布局格式
        add(fiveChessPanel,BorderLayout.CENTER);//将fiveChessPanel布局在正中央
        add(Panel,BorderLayout.EAST);//将Panel布局在整个面板的东侧
        
        jbtStart.addActionListener( new jbtStartListener());//为“开始”按钮添加监听器
        jbtPauseMusic.addActionListener(new jbtMusicListener());//为“停止音乐”按钮添加监听器
        jbtExit.addActionListener(new jbtExitListener());//为“退出”按钮添加监听器
        jbtLose.addActionListener(new jbtLoseListener());//为“认输”按钮添加监听器
        jbtAbout.addActionListener(new jbtAboutListener());//为“关于”按钮添加监听器
        jbtIntroduction.addActionListener(new jbtIntroductionListener());//为“游戏介绍”按钮添加监听器
             
    }
    
   
    
    private class jbtStartListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            fiveChessPanel.setCanPlay(true);//点击“开始”按钮后才可以下棋  
            
            URL urlForAudio = getClass().getResource("begin.aif");//获取“开始”音效的地址
            audioClip  = Applet.newAudioClip(urlForAudio);//为类目录下的声音文件 begin.aif 创建一个音频剪辑AudioClip对象
            audioClip.loop();//循环播放          
            fiveChessPanel.cleanChess();//清屏
            fiveChessPanel.repaint();//重新下棋
            jbtLose.setEnabled(true);//通过“开始”按钮，激活“认输按钮;           
        }
    }
    
     private class jbtMusicListener implements ActionListener{        
        public void actionPerformed(ActionEvent e){
            
            if(pause){
                jbtPauseMusic.setText("播放音乐");//如果pause为true，原来的“停止音乐”按钮就变成“播放音乐”
                audioClip.stop();//给“停止音乐”按钮添加监听器，调用audioClip的stop，停止播放音乐方法
                pause=false;//pause变为false                
            }
            else{
                jbtPauseMusic.setText("停止音乐");//如果开始播放了pause就变为了true，按钮的字就变成了“停止音乐”
                audioClip.loop();//音乐播放
                pause=true;
            }                        
       }
   }
    
    private class jbtExitListener implements ActionListener{        
        public void actionPerformed(ActionEvent e){
            int choose1;
            choose1=JOptionPane.showConfirmDialog(null,"你确定要退出吗？","Warning",JOptionPane.YES_NO_OPTION);//创建选择对话框
            if(choose1==JOptionPane.YES_OPTION)//如果选择“Yes”按钮
                System.exit(0);//退出游戏                                
        }
    }
    private class jbtLoseListener implements ActionListener{
        public void actionPerformed(ActionEvent e){            
            jbtLose.setEnabled(false);//设置“认输”按钮为灰色            
            int choose2;
            choose2=JOptionPane.showConfirmDialog(null,"你确定要认输吗？","Warning",JOptionPane.YES_NO_OPTION);//创建选择对话框
            if(choose2==JOptionPane.YES_OPTION)//如果选择“Yes”按钮
            {                
                URL urlForAudio = getClass().getResource("win.aif");//获取“开始”音效的地址
                audioClip1  = Applet.newAudioClip(urlForAudio);//为类目录下的声音文件 WIN_1.WAV 创建一个音频剪辑AudioClip对象
                audioClip1.play();//循环播放
                
                if(fiveChessPanel.getChessPieces()==2)//如果最后一步是黑棋
                    JOptionPane.showMessageDialog(null,"游戏结束，黑方胜！");//弹出对话框，赢家是黑棋
                else                    
                    JOptionPane.showMessageDialog(null,"游戏结束，白方胜！");//否则赢家是白棋
                  
                    jbtLose.setEnabled(false);//让“认输”按钮变灰;
                    fiveChessPanel.setCanPlay(false);//设置为棋盘上不能下棋
                audioClip1.stop(); //音乐停止
                    
            }           
            if(choose2==JOptionPane.NO_OPTION)//如果选择“否”按钮
                jbtLose.setEnabled(true);//“认输”按钮不变灰，下次还可以使用                       
        }
    }
    private class jbtIntroductionListener implements ActionListener{       
        public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(null,"该五子棋由冯彩云，张思思，郭文华联合开发。");//创建新的对话框，点击“游戏介绍”按钮。弹出该信息
            
        }
    }
    private class jbtAboutListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(null, "\t版本： 宇宙无敌1.0");//创建新的对话框，点击“关于”按钮，弹出该信息
        }
    }
}
    
