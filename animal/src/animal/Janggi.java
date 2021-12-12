package animal;


import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Janggi {
   
      final int NULL = 0;  //����ִ� ���� 0
      
      final int r90king = 1; //�������� 1���� 9����
      int r0sang = 2;
      int r90sang = 3;
      int r0ja = 4;
      int r90ja = 5;
      int r0cha = 6;
      int r90cha = 7;
      int r0who = 8;
      int r90who = 9;
      
      final int g90king = 11; //������� 11���� 19����
      int g0sang = 12;
      int g90sang = 13;
      int g0ja = 14;
      int g90ja = 15;
      int g0cha = 16;
      int g90cha = 17;
      int g0who = 18;
      int g90who = 19;
      
      static int k_count1 = 0; //king�� ����������� 2�� ��Ƽ�� �̱�� ���� �Ǵ��ϴ� ��ü.
      static int k_count2 = 0; // k_count = 2�� �Ǹ� ������.
      static int myTurn = 0;  //ù���ʴ� player1�� ���� �����Ѵ�
      boolean start = false; // start�� false�� ����, true�� �Ǹ� ������ �����.
      
      Information information;//
      CheckStart check;//

      JFrame mainFrame; //���� ��ü �������� �����.
      JPanel panel = new JPanel(); //�� �ȿ� ���� �г��� ����.
   
      JPanel gameZone = new JPanel() {  //
      public void paintComponent(Graphics g) {
         g.drawImage(icon.getImage(), 0, 0, 700, 700, this);
         setOpaque(false); // background�� ���ĵ��� 0���� ���� ����� �����ϰ� ����� �Լ�, 
         super.paintComponent(g);
      }
   };

   ImageIcon icon;
   MovePieces checkmove = new MovePieces();
   static int[][] janggiBoard;
   int[] malIndex, malIndextmp;

   public Janggi() {
      mainFrame = new JFrame();
      mainFrame.setTitle("�������");
      mainFrame.setLayout(null);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      information = new Information();
      icon = new ImageIcon("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\����ȭ��\\board.png");
      check = new CheckStart();
      
      
      panel.setLayout(null);
      gameZone.setLayout(null); 
      janggiBoard = Janggipan(); 				// Janggipan = ���� ��ó�� ���� �� ��ġ
      gameZone = Locate(janggiBoard, gameZone);
      panel.add(gameZone); 						//���� �÷����г� �߰�
      panel.add(information); 					//�ð� �� ���� �г� �߰�
      check.start();
      gameZone.setSize(700, 700); // 700x700���� �Ǿ��ִ� ������� ����ũ��
      mainFrame.setContentPane(panel); 
      mainFrame.setBounds(0, 0, 1000, 690); //������üũ��
      mainFrame.setResizable(false);
      mainFrame.setVisible(true);
   }
   
   private int[][] Janggipan() {//����� ó���� ���� ������
	  	int[][] Janggipan = {
	            {r90sang, r90king, r90cha},
	            {NULL, r90ja, NULL},
	            {NULL, g90ja, NULL},
	            {g90cha, g90king, g90sang} };
		return Janggipan;
   }

   // �����带 �̿��ؼ� ������ run��Ű�� ��
   class CheckStart extends Thread 
   {
      int count = 0;
      public void run(){
         while (true){
            if(!information.IsStart&& count==0){
               janggiBoard = Janggipan();  	// �ʱ� ���� �迭���¸� Janggipan���� �ʱ�ȭ
               gameZone.removeAll();		
               gameZone = Locate(janggiBoard, gameZone);// ���� ����ȭ�鿡 ��ġ��Ų��
               gameZone.repaint();			// ȭ�� ����
               myTurn = 0; 					// ���ʴ� Player1���� ����
               information.Player[0].setSelected(true);	//setSelected�� �����ư��� true�� false�� �Ǹ�
               information.Player[1].setSelected(false);//���ʸ� ��Ÿ����.
               count++;               
            }
            else if(information.IsStart && count != 0){
            	count = 0;
               }
         }         
      }
   }
   
   //�� �ϳ��ϳ��� ����
   public class mal extends JButton {
      int x;
      int y;
      ImageIcon icon;

      public mal(int i, int j, String imagePath)  //JPanel Locate���� for�� ���� ������ �ϳ��ϳ��� �ְ� ���⼭�� ��ġ�� �����Ѵ�. 
      {
         setSize(90, 90); //�� �ϳ��� ũ��
         setLocation((i * 130 + 100), (j * 110 + 205)); //����ġ
         icon = new ImageIcon(imagePath);

         addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
               // TODO Auto-generated method stub
               if (information.IsStart) { //start��ư ������
                  JButton btn = (JButton) e.getSource(); //Ŭ���� Ȱ��ȭ�ȴ�.
                  btn.setSelected(false); 
                  x = e.getXOnScreen() - mainFrame.getX()-50; //x��ǥ�� ���Ѵ�.
                  y = e.getYOnScreen() - mainFrame.getY()-50; //y��ǥ�� ���Ѵ�.
                  System.out.println("Ŭ���� ��ǥ�� ��ġ�� "+x + ", " + y); // �ܼ�â�� x,y��ǥ�� ��ġ�� ����
                  malIndex = getIndex(x, y);  				//getIndex�� x��ǥ�� y��ǥ�� ������ ���� �迭�� ��ġ�� ���Ѵ�.
                  System.out.println("�̵��� �迭 ��ǥ ��ġ = ("+malIndex[0] + "," + malIndex[1]+")"); //�̵��� ���� �迭�� ��ġ�� �ܼ�â�� ��Ÿ����.
                  System.out.println("������ player"+(myTurn+1)+" ����");	//�ܼ�â�� ���ʸ� ��Ÿ���ش�.
                  System.out.println("player1�� kingcount�� "+(k_count1));//�� �÷��̾��� kingcount�� �ܼ�â�� ��Ÿ���ش�.
                  System.out.println("player2�� kingcount�� "+(k_count2));
                
                  if (janggiBoard[malIndextmp[0]][malIndextmp[1]] / 10 == myTurn) //�ڽ����� ��쿡�� �ڽŸ��� ������ �� �ִ�.
                  {
                	  if (malIndex[0] == malIndextmp[0] && malIndextmp[1] == malIndex[1]); // ������ ���� �� ���� �ִ°�� �ƹ��ϵ� �Ͼ�� ����
    /*���� �򰥷��� �ϳ��� ������. �����ε��� = �������� �ε��� , �����ε���= �����̱��� �ε���*/
                	  
                	  else if (checkmove.CheckMalMove(janggiBoard, janggiBoard[malIndextmp[0]][malIndextmp[1]], malIndextmp, malIndex))
                	  {   //checkmove�� ���� ���õ� ���� �ε����� �����ε����� ���ؼ� ������ �� �ִ� ������ �Ǵ��Ѵ�. ������ �� ���� ���̸� checkmove���� false�� ��ȯ�Ͽ� �ٽ� �����ؾ��Ѵ�.
                		
                		 if (janggiBoard[malIndex[0]][malIndex[1]] == NULL) //���� �ε����� ����ִ� ���
                		 { 
                        	 if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 5 && malIndex[0] == 3) // �����ڰ� ��� ������ �����ؼ� �� �ķ� ���Ұ��
                   		  	{
                        		 janggiBoard[malIndex[0]][malIndex[1]] = 9; 			//�����ε����� �ڿ��� �ķ� �ٲ��
                        		 janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL; 	//���� �ε����� NULL������ �ʱ�ȭ. �ƹ��͵� ���� ����
                        		 checkJang(); 	//���ʸ� ����ħ.
                   		  	}	 
                        	 else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 15 && malIndex[0]==0) //����ڰ� ��� ������ �����ؼ� �� �ķ� ���Ұ��
                   		  	{ 
                        		 janggiBoard[malIndex[0]][malIndex[1]] = 19;			//�����ε����� �ڿ��� �ķ� �ٲ��
                        		 janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;	//���� �ε����� NULL������ �ʱ�ȭ. �ƹ��͵� ���� ����
                        		 checkJang();	//���ʸ� ����ħ.
                   		  	}
                        	 else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 1 && malIndex[0] == 3) //���� ŷ�� ����ִ� ��������� ���� ī��Ʈ�� �ö󰡴� ���           	                	  
                       	  	{
                       		  	janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //�ϴ� �װ��� �԰�
                       		  	janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
                       		  	k_count1 = 1; //ī��Ʈ�� 1�� �����Ѵ�.
                       		  	JOptionPane.showConfirmDialog(null, "���� ��������� ���Խ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
                       		  	checkJang(); //���ʸ� ��ħ.
                       	  	}
                       	  	 else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 11 && malIndex[0] == 0)      //��� ŷ�� ����ִ� ���������� ���� ī��Ʈ�� �ö󰡴� ���                   	  
                       	  	{
                       	  		 janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //�װ��� �԰�
                       	  		 janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
                       	  		 k_count2 = 1; //ī��Ʈ�� 1�� �����ϰ�
                       	  	JOptionPane.showConfirmDialog(null, "���� ���������� ���Խ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
                       	  		 checkJang(); //���ʸ� ��ģ��.
                       	  	}
                       	  	 else {	//�׳� ����ִ� ������ ���
                           janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //���� �ε����� ���� �ε������� �ְ�
                           janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL; //�����ε����� NULL�� �ʱ�ȭ.
                           checkJang(); //���ʸ� ��ħ.
                        	 }
                        } 
                		 
                   	  else if (janggiBoard[malIndex[0]][malIndex[1]] / 10 == janggiBoard[malIndextmp[0]][malIndextmp[1]] / 10);
                   	  //�̵��� ���� ���� ���� ���� ��� �ƹ��ϵ� �Ͼ�� ����.
                   	  
                     else //���� �ε����� ���� ���� �־ �����ε����� ��ƸԴ� ���
                        {
                        	if (janggiBoard[malIndex[0]][malIndex[1]] == 11 ) //�����ε����� ���� player2�� ���� ���
                           { //player1�� �¸��� ���� ��
                              janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]];
                              janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
                              start = false;
                              JOptionPane.showConfirmDialog
                              (null, "player1�� �¸�", "�ȳ�", JOptionPane.WARNING_MESSAGE);
                              information.IsStart = false;
                              information.ResetTimer();
                              information.IsStart= false;
                  
                           }
                          
                           else if (janggiBoard[malIndex[0]][malIndex[1]] == 1 )	//�����ε����� ���� player1�� ���ΰ��
                           { //player2�� �¸��� ���ӳ�
                              janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]];
                              janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
                              start = false;
                              JOptionPane.showConfirmDialog
                              (null, "Player2�� �¸�", "�ȳ�", JOptionPane.WARNING_MESSAGE);
                              information.IsStart = false;
                              information.ResetTimer();
                              information.IsStart=false;
                           }

                           else
                           {
                        	  if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 5 && malIndex[0] == 3) // �����ڰ� ��������� �ִ� �����ε����� �԰� ���İ� �Ǵ°��
                        		  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]); 	//���� ��(�����ε�����)���гο� ��Ÿ����.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = 9;				//�����ε����� �� �� ���� �־���.
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;		//�����ε����� NULL���� ��.
                        		  checkJang();//���ʸ� ��ħ
                        		  }
                        	  else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 15 && malIndex[0]==0)  // ����ڰ� ��������� �ִ� �����ε����� �԰� ���İ� �Ǵ°�� 
                        		  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]); 	//���� ��(�����ε�����)���гο� ��Ÿ����.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = 19;				//���� �ε����� ��� ���ĸ� �־���
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;		//���� �ε����� NULL���� ��.
                        		  checkJang();//���ʸ� ��ħ.
                        		  }
                        	  else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 1 && malIndex[0] == 3)   //���� ŷ�� ��������� ���� ī��Ʈ�� �ö󰡴� ���
                        	  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]);	//���� ��(�����ε�����)���гο� ��Ÿ����.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //�����ε����� �����ε������� �־���.
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL; //���� �ε����� NULL���� ��.
                        		  k_count1 = 1; //ī��Ʈ�� ����
                        		  JOptionPane.showConfirmDialog(null, "���� ��������� ���Խ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
                        		  checkJang();//���ʸ� ��ħ.
                        	  }
                        	  else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 11 && malIndex[0] == 0)   //���ŷ�� ���������� ���� ī��Ʈ�� �ö󰡴� ���
                        	  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]);	//���� ��(�����ε�����)���гο� ��Ÿ����.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //�����ε����� �����ε������� �־���.
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;	//���� �ε����� NULL���� ��.
                        		  k_count2 = 1; //ī��Ʈ�� ����
                        		  JOptionPane.showConfirmDialog(null, "���� ���������� ���Խ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
                        		  checkJang();	//���ʸ� ��ħ.
                        	  }
                        	  else  //Ư���� ��찡 �ƴϰ�, �����ε����� �����ε����� �Դ°��
                        	  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]); //���� ��(�����ε�����)���гο� ��Ÿ����.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //�����ε����� �����ε������� �־���.
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;	//���� �ε����� NULL���� ��.
                        		  checkJang(); //���ʸ� ��ħ.
                        	  }
                           }
                        }
                     }
                  }
                  //���ʰ� ������ ���� ������ ���¸� �������ش�.
                  gameZone.removeAll();				
                  gameZone = Locate(janggiBoard, gameZone);	
                  gameZone.repaint();					
                  System.out.println();
               }
            }
            
            @Override
            public void mousePressed(MouseEvent e) { //�׳� �����⸸ �������� �迭�� ��ġ�� �ܼ�â�� ���� �� �� �ְ� �Ѵ�.
               // TODO Auto-generated method stub
               JButton btn = (JButton) e.getSource();
               btn.setSelected(false);
               x = e.getXOnScreen() - mainFrame.getX() - 50;//
               y = e.getYOnScreen() - mainFrame.getY() - 50;//
               malIndextmp = getIndex(x, y); // ��ǥ x,y���� getIndex�� ���� �迭�� ��ġ�� ��Ÿ��.
               System.out.println("Ŭ�� �� ��ġ �迭 ��ǥ "+ malIndextmp[0] + ", " + malIndextmp[1]);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
               // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
               // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
               // TODO Auto-generated method stub
            }
         });

         addMouseMotionListener(new MouseMotionListener()
         {
            @Override
            public void mouseDragged(MouseEvent e) {
               // TODO Auto-generated method stub
               if (information.IsStart) {
                  JButton btn = (JButton) e.getSource();
                  x = e.getXOnScreen() - mainFrame.getX();
                  y = e.getYOnScreen() - mainFrame.getY();
                  if (x < 700 && x > 100 && y < 700 && y > 100) //���� �����ϼ��ִ� ����
                	  btn.setLocation(x -45, y - 80); //�����̴� ���� �巡���Ҷ� ��ġ�ϴ� ���콺�� ��ġ
               }
            }
            @Override
            public void mouseMoved(MouseEvent arg0) {
               // TODO Auto-generated method stub
            }
         }
         );
      }
      
      public void paintComponent(Graphics g) //
      {
         g.drawImage(icon.getImage(), 0, 0, 90, 90, null);
      }
   }

   void setPlayerEatMal(int killedMal) { //�����¸��� ����� �ͼ� JLabel�� PlayerPanel�� ��Ÿ���� �����ش�.
	   String MalImg = null;
	   //������ ���� �Դ°��
	   	if(myTurn == 1) {
	         if(killedMal ==1)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90king.png";
	         else if(killedMal ==3)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0sang.png";
	         else if (killedMal ==5 || killedMal ==9)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0ja.png";
	         else if(killedMal==7)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0cha.png";
	   	}
	    //�ʷϻ� ���� �Դ°��
	   	else {
	         if(killedMal == 11)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90king.png";
	         else if(killedMal ==13)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0sang.png";
	         else if (killedMal ==15 || killedMal ==19)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0ja.png";
	         else if(killedMal==17)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0cha.png";
	   	}	   	
	    Information.PlayerPanel[myTurn].add(new JLabel(new ImageIcon(MalImg)));
	    //�������� �ڽ��� ������ PlayerPanel��  �߰��Ѵ�.
   	}
   
   public int[] getIndex(int ox, int oy) {
      int[] Index = new int[2]; //index[0]�� index[1]�� ������
      int i = 0 , j = 0;	
      if (ox >=50 && ox <= 150)      	//Ŭ���� x��ǥ ��ġ�� ������ ���� ���õ� x��ǥ �迭�� ����
      		i = 0;					 	
      else if(ox >= 170 && ox <= 270)	// �پ��� ������� ��������� ��ǥ���� ���� �迭�Ǵ��� ���� ��Ȯ�� ����� �� ���Ƽ�
    	  	i = 1;						// �ϳ��ϳ��� ��ǥ�� ���ϰ� ��ǥ���� ������ �迭 [x],[y]�� ����  ���ϴ� ����� ����.
      else if(ox >= 310 && ox <= 410)
    	  	i = 2;
      else if(ox >= 440 && ox <= 540)
    	  	i = 3;
      
      if(oy >= 180 && oy <= 280) 		//Ŭ���� y��ǥ ��ġ�� ������ ���� ���õ� y��ǥ �迭�� ����
    	  	j = 0;
      else if(oy >= 300 && oy <=400)
    	  	j = 1;
      else if(oy >= 410 && oy <= 510)
    	  	j = 2;
      
      Index[0] = i;
      Index[1] = j;   
      return Index;
   }
   
   //board�迭�� �ִ� ��⸻�� ����ǿ� �̹����� ��Ÿ���ش�. 
   public JPanel Locate(int[][] board, JPanel Janggi) {
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 3;j++) {
            if (board[i][j] == r90king) {
               mal r90king = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90king.png");
               r90king.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90king.png");
               Janggi.add(r90king);
            } else if (board[i][j] == r0sang) {
               mal r0sang = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0sang.png");
               r0sang.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0sang.png");
               Janggi.add(r0sang);
            } else if (board[i][j] == r90sang) {
               mal r90sang = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90sang.png");
               r90sang.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90sang.png");
               Janggi.add(r90sang);
            } else if (board[i][j] ==r0ja) {
               mal r0ja = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0ja.png");
               r0ja.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0ja.png");
               Janggi.add(r0ja);
            } else if (board[i][j] == r90ja ) {
               mal r90ja  = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90ja.png");
               r90ja.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90ja.png");
               Janggi.add(r90ja );
            } else if (board[i][j] == r0cha) {
               mal r0cha = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0cha.png");
               r0cha.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0cha.png");
               Janggi.add(r0cha);
            } else if (board[i][j] == r90cha) {
               mal r90cha = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90cha.png");
               r90cha.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90cha.png");
               Janggi.add(r90cha);
            } else if (board[i][j] == r0who) {
               mal r0who = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0who.png");
               r0who.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r0who.png");
               Janggi.add(r0who);
            } else if (board[i][j] == r90who) {
               mal r90who = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90who.png");
               r90who.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\������⻡����\\r90who.png");
               Janggi.add(r90who);
            }

            else if (board[i][j] == g90king) {
               mal g90king = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90king.png");
               g90king.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90king.png");
               Janggi.add(g90king);
            } else if (board[i][j] == g0sang) {
               mal g0sang = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0sang.png");
               g0sang.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0sang.png");
               Janggi.add(g0sang);   
            }
            else if (board[i][j] == g90sang) {
               mal g90sang = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90sang.png");
               g90sang.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90sang.png");
               Janggi.add(g90sang);
            } else if (board[i][j] == g0ja) {
               mal g0ja = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0ja.png");
               g0ja.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0ja.png");
               Janggi.add(g0ja);
            } else if (board[i][j] == g90ja) {
               mal g90ja = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90ja.png");
               g90ja.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90ja.png");
               Janggi.add(g90ja);
            } else if (board[i][j] == g0cha) {
               mal g0cha = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0cha.png");
               g0cha.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0cha.png");
               Janggi.add(g0cha);
            } else if (board[i][j] == g90cha) {
               mal g90cha = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90cha.png");
               g90cha.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90cha.png");
               Janggi.add(g90cha);
            } else if (board[i][j] == g0who) {
               mal g0who = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0who.png");
               g0who.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g0who.png");
               Janggi.add(g0who);
            } else if (board[i][j] == g90who) {
                  mal g90who = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90who.png");
                  g90who.setName("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��������ʷϸ�\\g90who.png");
                  Janggi.add(g90who);
               }
         }
      }
      return Janggi;
   }

   public void checkJang() { //���� �������� �����ų� �ð��� �����ų� ��� �ڽ��� ���ʰ� ������ ���� �ٲٴ� ������ ���ش�.
      if (myTurn == 0 && k_count2 != 1) { //���ʰ� 0�̰� ��ī��Ʈ�� 1�� �ƴϸ�
         myTurn = 1; 						//���ʰ� 1�� �ٲ��
         information.setTurnIsChangeToTrue(); 
      } 
      
      //���� ��� �������� ������ ��Ƽ�� ������ �¸��ȴ�. �̸� k_count�� Ȯ���ؼ� ���и� ����.
      else if(myTurn == 1 && k_count1 == 1) //player2�� ���ʿ��� player1�� ���� ��������� ���� ������ ��ƾ ���¸�
 	  {										//player1�� �¸��� ������.
           start = false;					//���� ��
           JOptionPane.showConfirmDialog(null, "Player1�� �¸�", "�ȳ�", JOptionPane.WARNING_MESSAGE);
           information.IsStart = false;		//
           information.ResetTimer();		//Ÿ�̸� ����
           information.IsStart=false;			
        } 
      
      else if(myTurn == 0 && k_count2 == 1)	//player1�� ���ʿ��� player2�� ���� ��������� ���� ������ ��ƾ ���¸�
 	  {										//player2�� �¸��� ������.
           start = false;
           JOptionPane.showConfirmDialog(null,
           "Player2�� �¸�", "�ȳ�", JOptionPane.WARNING_MESSAGE);
           information.IsStart = false;
           information.ResetTimer();
           information.IsStart=false;       
        }
      
      else { 								//��� ����Ǽ��� �ƴϸ� ���ʰ� player1�̰�  ��ī��Ʈ�� 1�� �ƴ� ���� �Ǵ�
         myTurn = 0;						//player2�� ������ �ٲ��.
         information.setTurnIsChangeToTrue();
      }
   }
   
   public static void changeTurn() //���ʸ� �ٲ��ش�.
   {
      if (myTurn == 0)
      {
         myTurn = 1;
      }
      else
      {
         myTurn = 0;
      }
   }
   
   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new Janggi();
   }
}