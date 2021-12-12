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
   JButton Startbu; //���� ��ư
   JButton Endbu; //������ ��ư
   JPanel jp2; 
   JButton onesuB; //���۹�ư�� ���� �� ��
   JToggleButton[] Player;
   PlayTimer PlayTimer; //�� �÷��� �ð�
   RemainTimer RemainTimer; //���� �� ���� �ð�
   static JPanel[] PlayerPanel;
   int hours, mins, secs, hours1, mins1, secs1; //�ð� ǥ�� ����
   double oo, xx, oo1, xx1; //�ð� ǥ���� �� ���� �ð� ���� ����
   boolean loop, first = true, TurnIsChange = false, imageIsChange = false;
   ImageIcon icon;
   
   boolean IsStart;

   void setTurnIsChangeToTrue() {// TurnIsChange�� True�� ����.���̹ٲ�� �����ð� �ʱ�ȭ�� ���ؼ�
      TurnIsChange = true;
      SetTurn();
   }
   
   void setimageIsChangeToTrue() {
      imageIsChange = true;
   }
   
   void SetTurn(){//���� �ٲ�� ��۹�ư�� �ٲ��
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
      icon = new ImageIcon("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\����ȭ��\\board.png");
      jp2 = new JPanel();
      //time = new JPanel();
      jp2.setLayout(null);
      add(jp2);
      
      //���ѽð� ǥ�� ����
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
      
      //�÷��̽ð� ǥ�� ����
      PlayTime.setBounds(0, 70, 300, 70);
      PlayTime.setHorizontalAlignment(SwingConstants.CENTER);
      PlayTime.setForeground(Color.yellow); 
      PlayTime.setText("00:00:00");
      PlayTime.setBorder(TB);
      PlayTime.setFont(font);
      
      //�� �ѱ�� ��ư ����
      onesuB = new JButton();
      onesuB.setBounds(0, 560, 150, 100);
      onesuB.setVisible(false);
      onesuB.setText("�� �ѱ��");
      onesuB.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
        	 Janggi.changeTurn(); 
            setTurnIsChangeToTrue();
         }
      });

      //player 1, 2 ��Ÿ���� �г�
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
      
      //player 1, 2 �� ���� ���� ��Ÿ���� �г�
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
      Startbu.setText("����");
      Startbu.addMouseListener(new ButtonMouseListener());
      
      Endbu = new JButton();
      Endbu.setBounds(150, 560, 150, 100);
      Endbu.setText("������");
      Endbu.addMouseListener(new ButtonMouseListener());

      add(RemainTime);
      add(PlayTime);
      add(onesuB);
      add(Startbu);
      add(Endbu);

      setFocusable(false);
      setLocation(700, 0);// ������ ���� ������Ÿ���ִ� ������ ��������
      setBackground(Color.BLACK);//������� �����
      setSize(500, 1000); // ���� ���� ũ��
      
      setVisible(true);
      PlayTimer = new PlayTimer();
      RemainTimer = new RemainTimer();
   }

   class PlayTimer extends Thread {
      void timer1() {// �÷��� �ð�
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
                 Thread.sleep(1000 * 60 * 60 * 24); // 24�ð� ��������
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
        // �����ð�
         xx1 = (oo1 + (30000d) - System.currentTimeMillis()) / 1000d;
         hours1 = ((int) xx1 % 86400) / 3600;
         mins1 = ((int) xx1 % 3600) / 60;
         secs1 = (int) xx1 % 60;
         RemainTime.setText(String.format("%02d:%02d:%02d  ", hours1, mins1,
               secs1));
         
         // ���� �ٲ���ų� �ð��� �ʰ�������
         if (oo1 + (30000d) - System.currentTimeMillis() <= 0d || TurnIsChange) 
            {
               if (oo1 + (30000d) - System.currentTimeMillis() <= 0d)// ���� ���°� �ð��ʰ����                                  
               {   Janggi.changeTurn();// ���� �ٲ۴�
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
                  Thread.sleep(1000 * 60 * 60 * 24); // 24�ð� ��������
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
            
            if (first) {// ó�� ���� ��������(ó�� �����Ҷ�)              
               loop = true;
               oo = System.currentTimeMillis();//���� �ð��� ���Ѵ�.
               oo1 = System.currentTimeMillis();//���� �ð��� ����
               PlayTimer.start();
               RemainTimer.start();
               first = false;               
            }
            
            else {// ������ ������ ���� ��������(ó�� ������ �ƴҶ�)
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
      public void mouseEntered(MouseEvent e) { //���콺�� �������� ��
      }

      @Override
      public void mouseExited(MouseEvent e) { //���콺�� ���پ����� ��
      }

      @Override
      
      public void mousePressed(MouseEvent e) { //���콺 ������ ��
      }

      @Override
      public void mouseReleased(MouseEvent e) { //���콺 ������ ��
      }

   }
}
