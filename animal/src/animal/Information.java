package animal;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Information extends JPanel {
   JLabel RemainTime, PlayTime;
   JButton Startbu; //시작 버튼
   JButton Endbu; //끝내기 버튼
   JPanel jp2; 
   JButton onesuB; //시작버튼이 눌린 후 한
   JToggleButton[] Player;
   PlayTimer PlayTimer; //총 플레이 시간
   RemainTimer RemainTimer; //현재 턴 남은 시간
   static JPanel[] PlayerPanel;
   int hours, mins, secs, hours1, mins1, secs1; //시간 표시 변수
   double oo, xx, oo1, xx1; //시간 표시할 때 쓰일 시간 계산용 변수
   boolean loop, first = true, TurnIsChange = false, imageIsChange = false;
   ImageIcon icon;
   
   boolean IsStart;

   void setTurnIsChangeToTrue() {// TurnIsChange를 True로 만듬.턴이바뀌면 남은시간 초기화를 위해서
      TurnIsChange = true;
      SetTurn();
   }
   
   void setimageIsChangeToTrue() {
      imageIsChange = true;
   }
   
   void SetTurn(){//턴이 바뀌면 토글버튼도 바뀌게
      if(Player[0].isSelected())
      {
         Player[0].setSelected(false);
         Player[1].setSelected(true);
      }
      else
      {
         Player[1].setSelected(false);
         Player[0].setSelected(true);
      }
   }

   void ResetTimer(){
      PlayTime.setText("00:00:00");
      RemainTime.setText("00:00:00");
      onesuB.setVisible(false);
      Startbu.setVisible(true);
      PlayTimer.suspend();
      RemainTimer.suspend();
      IsStart=false;
   }

   public Information() {
      setLayout(null);
      icon = new ImageIcon("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\게임화면\\board.png");
      jp2 = new JPanel();
      //time = new JPanel();
      jp2.setLayout(null);
      add(jp2);
      
      //제한시간 표시 설정
      TitledBorder TB = new TitledBorder(new LineBorder(Color.white));
      Font font = new Font("Arial", Font.ITALIC, 20);
      RemainTime = new JLabel();
      RemainTime.setBounds(0, 0, 300, 70);
      RemainTime.setHorizontalAlignment(SwingConstants.CENTER);
      RemainTime.setForeground(Color.red); 
      RemainTime.setText("00:00:00");
      RemainTime.setBorder(TB);
      RemainTime.setFont(font);
      PlayTime = new JLabel(){
         public void paintComponents(Graphics g){
            //super.paintComponents(g);
            g.drawImage(icon.getImage(), 0, 0, 50, 80, null);
         }
      };
      
      //플레이시간 표시 설정
      PlayTime.setBounds(0, 70, 300, 70);
      PlayTime.setHorizontalAlignment(SwingConstants.CENTER);
      PlayTime.setForeground(Color.yellow); 
      PlayTime.setText("00:00:00");
      PlayTime.setBorder(TB);
      PlayTime.setFont(font);
      
      //턴 넘기는 버튼 설정
      onesuB = new JButton();
      onesuB.setBounds(0, 560, 150, 100);
      onesuB.setVisible(false);
      onesuB.setText("턴 넘기기");
      onesuB.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
        	 Janggi.changeTurn(); 
            setTurnIsChangeToTrue();
         }
      });

      //player 1, 2 나타내는 패널
      Player = new JToggleButton[2];
      for (int i = 0; i < 2; i++) {
         Player[i] = new JToggleButton();
         Player[i].setText("Player " + (i + 1));
         Player[i].setBorder(TB);
         Player[i].setBounds(150 * i, 140, 150, 100); 
         Player[i].setEnabled(false);
         add(Player[i]);
      }
      Player[0].setSelected(true);
      
      //player 1, 2 가 먹은 말을 나타내는 패널
      PlayerPanel = new JPanel[2];
      for (int i = 0; i < 2; i++) 
      {
         PlayerPanel[i] = new JPanel();
         PlayerPanel[i].setLayout(new FlowLayout());
         PlayerPanel[i].setBounds(150 * i, 235, 150, 325); 
         PlayerPanel[i].setBorder(TB);
         add(PlayerPanel[i]);
      }

      Startbu = new JButton();
      Startbu.setBounds(0, 560, 150, 100);
      Startbu.setText("시작");
      Startbu.addMouseListener(new ButtonMouseListener());
      
      Endbu = new JButton();
      Endbu.setBounds(150, 560, 150, 100);
      Endbu.setText("끝내기");
      Endbu.addMouseListener(new ButtonMouseListener());

      add(RemainTime);
      add(PlayTime);
      add(onesuB);
      add(Startbu);
      add(Endbu);

      setFocusable(false);
      setLocation(700, 0);// 게임판 옆에 정보나타내주는 영역의 시작지점
      setBackground(Color.BLACK);//검정배경 만들기
      setSize(500, 1000); // 정보 보드 크기
      
      setVisible(true);
      PlayTimer = new PlayTimer();
      RemainTimer = new RemainTimer();
   }

   class PlayTimer extends Thread {
      void timer1() {// 플레이 시간
         xx = (System.currentTimeMillis() - oo) / 1000d;
         hours = ((int) xx % 86400) / 3600;
         mins = ((int) xx % 3600) / 60;
         secs = (int) xx % 60;
         PlayTime.setText(String.format("%02d:%02d:%02d  ", hours, mins, secs));
      }

      public void run() {
         while (true) {
            try {
               if (loop) 
                     {
                        Thread.sleep(100);
                        timer1();
                     }
               else 
               {
                 Thread.sleep(1000 * 60 * 60 * 24); // 24시간 정지상태
               }
            } catch (InterruptedException e)
            {
               break;
            }
         }
      }
   }

   class RemainTimer extends Thread {
      void timer() {
        // 남은시간
         xx1 = (oo1 + (30000d) - System.currentTimeMillis()) / 1000d;
         hours1 = ((int) xx1 % 86400) / 3600;
         mins1 = ((int) xx1 % 3600) / 60;
         secs1 = (int) xx1 % 60;
         RemainTime.setText(String.format("%02d:%02d:%02d  ", hours1, mins1,
               secs1));
         
         // 턴이 바뀌었거나 시간이 초과됐을때
         if (oo1 + (30000d) - System.currentTimeMillis() <= 0d || TurnIsChange) 
            {
               if (oo1 + (30000d) - System.currentTimeMillis() <= 0d)// 만약 상태가 시간초과라면                                  
               {   Janggi.changeTurn();// 턴을 바꾼다
                  setTurnIsChangeToTrue();
               }
            oo1 = System.currentTimeMillis();
            TurnIsChange = false;
         }
      }

      public void run() {
         while (true) {
            try {
               if (loop) {
                  Thread.sleep(100);
                  timer();
               } else {
                  Thread.sleep(1000 * 60 * 60 * 24); // 24시간 정지상태
               }
            } catch (InterruptedException e) {
               break;
            }
         }
      }

   }

   class ButtonMouseListener implements MouseListener {

      @SuppressWarnings("deprecation")
      @Override
      
      public void mouseClicked(MouseEvent e) {
         if (e.getSource() == Startbu) {
            
            if (first) {// 처음 시작 눌렀을때(처음 실행할때)              
               loop = true;
               oo = System.currentTimeMillis();//기준 시간을 정한다.
               oo1 = System.currentTimeMillis();//기준 시간을 정함
               PlayTimer.start();
               RemainTimer.start();
               first = false;               
            }
            
            else {// 끝내기 누른후 시작 눌렀을때(처음 실행이 아닐때)
               oo = System.currentTimeMillis();
               oo1 = System.currentTimeMillis();
               PlayTimer.resume();
               RemainTimer.resume();            
            }
            IsStart=true;
            onesuB.setVisible(true);
            Startbu.setVisible(false);
         } else if (e.getSource() == Endbu) {
            ResetTimer();
            IsStart=false;
         }
      }

      @Override
      public void mouseEntered(MouseEvent e) { //마우스가 접근했을 때
      }

      @Override
      public void mouseExited(MouseEvent e) { //마우스가 접근안했을 때
      }

      @Override
      
      public void mousePressed(MouseEvent e) { //마우스 눌렸을 때
      }

      @Override
      public void mouseReleased(MouseEvent e) { //마우스 떼졌을 때
      }

   }
}
