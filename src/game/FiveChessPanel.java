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
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.applet.*;
import javax.swing.JApplet;
import java.net.URL;
import java.awt.Font;


public class FiveChessPanel extends JPanel{
    private int width = 600;
    private int height = 600;
    private int widthOfGrid = 30;
    private int heightOfGrid = 30;
    private int x = 0;
    private int y = 0;
    private int[][] allChessPieces = new int[19][19];
    private boolean isBlack=true;
    boolean canPlay=true;
    private String message;
    private AudioClip audioClip1;
    private AudioClip audioClip2;
    
    
    
    public int getChessPieces(){
        return allChessPieces[x][y];//创建该方法，返回棋子，方便在别的类中调用
    }
    
    public FiveChessPanel(){
        
        this.addMouseListener(new MouseAdapter(){        
                public void mousePressed(MouseEvent e){                    
                    x = e.getX();
                    y = e.getY();                   
                    if(canPlay&&x > widthOfGrid && x < widthOfGrid * 19 && y >heightOfGrid && y < heightOfGrid * 19){
                        x = (int)Math.round(1.0 * (x - widthOfGrid) / widthOfGrid);
                        y = (int)Math.round(1.0 * (y - heightOfGrid) / heightOfGrid);
                        if (allChessPieces[x][y]==0){                                            
                        if(isBlack){
                            allChessPieces[x][y] = 1;
                            
                            URL urlForAudio = getClass().getResource("put.aif");//获取该音频的地址
                            audioClip2 = Applet.newAudioClip(urlForAudio);//创建audioClip
                            audioClip2.play();//播放黑子落棋声
                            
                            message="白方走";//创建“白方走”的字符串
                            isBlack=false;                          
                        } 
                        else {
                            allChessPieces[x][y]=2;
                            
                            URL urlForAudio = getClass().getResource("put.aif");//获取该音频的地址
                            audioClip2 = Applet.newAudioClip(urlForAudio);//创建audioClip
                            audioClip2.play();//播放白子落棋声
                            
                            message="黑方走";//创建“黑方走”的字符串
                            isBlack=true;
                        }
                        repaint();//先画出最后一步落下的棋子   
                        
                        if(checkWin()){
                           
                            URL urlForAudio = getClass().getResource("win.aif");//获取“开始”音效的地址
                            audioClip1  = Applet.newAudioClip(urlForAudio);//为类目录下的声音文件 WIN_1.WAV 创建一个音频剪辑AudioClip对象
                            audioClip1.play();//播放音乐，且只播放一次                              
                            JOptionPane.showMessageDialog(null,"恭喜，"+(allChessPieces[x][y]==1?"黑方":"白方")+"赢了!");//如果赢了，就弹出获胜一方的对话框
                            audioClip1.stop();//点“确定”按钮后音乐就会立即停止
                            canPlay=false;//赢了后设置为棋盘不能再下棋了
                                                       
                        }                                                                        
                    }  
                    }                 
                }                
        });         
    }
   
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawString("游戏主手："+message,178,620);//画一个显示游戏主手的字符串
        setFont(new Font("宋体",Font.BOLD,30));
        for(int i = 0; i < 19; i++){
            
            g.drawLine(widthOfGrid,heightOfGrid * (1 + i),widthOfGrid * 19,heightOfGrid * (1 + i));
            g.drawLine(widthOfGrid * (1 + i),heightOfGrid,widthOfGrid * (1 + i),heightOfGrid * 19);
            
        }
        
        for(int i = 0; i < 3; i++){
            for(int j = 0; j< 3; j++){
                g.fillOval(widthOfGrid * 4 - 4 + 6 * widthOfGrid * j, widthOfGrid * 4 -4 + 6 * heightOfGrid * i, 8, 8);
            }
        }
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(allChessPieces[i][j] == 1){
                    int tempX = i * widthOfGrid + widthOfGrid;
                    int tempY = j * heightOfGrid + heightOfGrid;
                    g.setColor(Color.BLACK);
                    g.fillOval(tempX - (int)(widthOfGrid * 0.4),tempY - (int)(heightOfGrid * 0.4),(int)(widthOfGrid * 0.8),(int)(heightOfGrid * 0.8));
                }
                else if(allChessPieces[i][j] == 2){
                    int tempX = i * widthOfGrid + widthOfGrid;
                    int tempY = j * heightOfGrid + heightOfGrid;
                    g.setColor(Color.WHITE);
                    g.fillOval(tempX - (int)(widthOfGrid * 0.4),tempY - (int)(heightOfGrid * 0.4),(int)(widthOfGrid * 0.8),(int)(heightOfGrid * 0.8));
                    g.setColor(Color.RED);
                    g.drawOval(tempX - (int)(widthOfGrid * 0.4), tempY - (int)(heightOfGrid * 0.4), (int)(widthOfGrid * 0.8), (int)(heightOfGrid * 0.8));
                    
                }
            }
        }  
    }
           
       
        public boolean getCanPlay()//定义“是否可以在棋盘上下棋”的方法，设为boolean型
    {
            return canPlay;
        }
        public void setCanPlay(boolean canPlay){
            this.canPlay=canPlay;
        }
       public void cleanChess(){
           for(int i=0;i<19;i++){
               for (int j=0;j<19;j++){
                   allChessPieces[i][j]=0;
               }
           }
       }
       
       
  
    boolean checkWin() {//创建判断五子连成一条线的方法

       boolean winflag = false;
       int count = 1;
       int color = allChessPieces[x][y]; 
       count = this.checkCount(1, 0, color); //把checkCount()方法赋给count,checkCount()方法，东西走向判断五子是否一条线
       if (count >= 5)
           winflag = true;//如果count为5，则就判断为赢
       else {
           count = this.checkCount(0, 1, color);//南北走向判断五子是否一条线
           if (count >= 5)
              winflag = true;//如果count为5，则就判断为赢
           else {
              count = this.checkCount(1, -1, color);//西北到东南方向判断五子是否一条线
              if (count >= 5)
                  winflag = true;//如果count为5，则就判断为赢
              else {
                  count = this.checkCount(1, 1, color);//东北西南方向判断五子是否一条线
                  if (count >= 5)
                     winflag = true;//如果count为5，则就判断为赢
              }
           }
       }
       return winflag;
    }
  
    public int checkCount(int i1, int i2, int color) {//创建checkCount方法
       int count = 1;
       int tempX = i1;//棋子在x轴方向的变化单位
       int tempY = i2; //棋子在y方向的变化单位
       while ((x + i1 >= 0) && (x + i1 < 19) && (y + i2 >= 0)&& (y + i2 < 19)&&color == allChessPieces[x + i1][y + i2]) {
           
           if (i1 != 0)
              i1++;
           if (i2 != 0)
              if (i2 > 0)
                  i2++;
              else   
                  i2--;
           count++;
           
       }
       i1 = tempX;//i1有设为初始值，不再是上一个while语句里的i1
       i2 = tempY;//i2有设为初始值，不再是上一个while语句里的i2
       while ((x - i1 >= 0) && (x - i1 < 19) && (y - i2 >= 0)&& (y - i2 < 19)&&color == allChessPieces[x - i1][y - i2]) {
           
           if (i1 != 0)
              i1++;
           if (i2 != 0)
              if (i2 > 0)
                  i2++;
              else
                  i2--;
         count++;  
       }
       return count;
    }
}
       

