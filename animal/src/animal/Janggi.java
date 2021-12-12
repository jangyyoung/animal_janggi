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
   
      final int NULL = 0;  //비어있는 상태 0
      
      final int r90king = 1; //빨간말은 1부터 9까지
      int r0sang = 2;
      int r90sang = 3;
      int r0ja = 4;
      int r90ja = 5;
      int r0cha = 6;
      int r90cha = 7;
      int r0who = 8;
      int r90who = 9;
      
      final int g90king = 11; //녹색말은 11부터 19까지
      int g0sang = 12;
      int g90sang = 13;
      int g0ja = 14;
      int g90ja = 15;
      int g0cha = 16;
      int g90cha = 17;
      int g0who = 18;
      int g90who = 19;
      
      static int k_count1 = 0; //king이 상대진영에서 2턴 버티면 이기는 것을 판단하는 객체.
      static int k_count2 = 0; // k_count = 2가 되면 끝난다.
      static int myTurn = 0;  //첫차례는 player1이 먼저 시작한다
      boolean start = false; // start가 false로 시작, true가 되면 게임이 진행됨.
      
      Information information;//
      CheckStart check;//

      JFrame mainFrame; //게임 전체 프레임을 만든다.
      JPanel panel = new JPanel(); //그 안에 넣을 패널을 만듬.
   
      JPanel gameZone = new JPanel() {  //
      public void paintComponent(Graphics g) {
         g.drawImage(icon.getImage(), 0, 0, 700, 700, this);
         setOpaque(false); // background의 알파도를 0으로 만들어서 배경을 투명하게 만드는 함수, 
         super.paintComponent(g);
      }
   };

   ImageIcon icon;
   MovePieces checkmove = new MovePieces();
   static int[][] janggiBoard;
   int[] malIndex, malIndextmp;

   public Janggi() {
      mainFrame = new JFrame();
      mainFrame.setTitle("십이장기");
      mainFrame.setLayout(null);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      information = new Information();
      icon = new ImageIcon("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\게임화면\\board.png");
      check = new CheckStart();
      
      
      panel.setLayout(null);
      gameZone.setLayout(null); 
      janggiBoard = Janggipan(); 				// Janggipan = 게임 맨처음 시작 말 배치
      gameZone = Locate(janggiBoard, gameZone);
      panel.add(gameZone); 						//게임 플레이패널 추가
      panel.add(information); 					//시간 및 정보 패널 추가
      check.start();
      gameZone.setSize(700, 700); // 700x700으로 되어있는 십이장기 말판크기
      mainFrame.setContentPane(panel); 
      mainFrame.setBounds(0, 0, 1000, 690); //게임전체크기
      mainFrame.setResizable(false);
      mainFrame.setVisible(true);
   }
   
   private int[][] Janggipan() {//장기판 처음에 말을 저장함
	  	int[][] Janggipan = {
	            {r90sang, r90king, r90cha},
	            {NULL, r90ja, NULL},
	            {NULL, g90ja, NULL},
	            {g90cha, g90king, g90sang} };
		return Janggipan;
   }

   // 스레드를 이용해서 게임을 run시키는 것
   class CheckStart extends Thread 
   {
      int count = 0;
      public void run(){
         while (true){
            if(!information.IsStart&& count==0){
               janggiBoard = Janggipan();  	// 초기 보드 배열상태를 Janggipan으로 초기화
               gameZone.removeAll();		
               gameZone = Locate(janggiBoard, gameZone);// 말을 게임화면에 배치시킨다
               gameZone.repaint();			// 화면 갱신
               myTurn = 0; 					// 차례는 Player1부터 시작
               information.Player[0].setSelected(true);	//setSelected가 번갈아가며 true와 false가 되며
               information.Player[1].setSelected(false);//차례를 나타낸다.
               count++;               
            }
            else if(information.IsStart && count != 0){
            	count = 0;
               }
         }         
      }
   }
   
   //말 하나하나의 설정
   public class mal extends JButton {
      int x;
      int y;
      ImageIcon icon;

      public mal(int i, int j, String imagePath)  //JPanel Locate에서 for로 말의 정보를 하나하나씩 넣고 여기서는 위치를 정의한다. 
      {
         setSize(90, 90); //말 하나의 크기
         setLocation((i * 130 + 100), (j * 110 + 205)); //말위치
         icon = new ImageIcon(imagePath);

         addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
               // TODO Auto-generated method stub
               if (information.IsStart) { //start버튼 누르면
                  JButton btn = (JButton) e.getSource(); //클릭이 활성화된다.
                  btn.setSelected(false); 
                  x = e.getXOnScreen() - mainFrame.getX()-50; //x좌표를 구한다.
                  y = e.getYOnScreen() - mainFrame.getY()-50; //y좌표를 구한다.
                  System.out.println("클릭한 좌표의 위치는 "+x + ", " + y); // 콘솔창에 x,y좌표의 위치가 나옴
                  malIndex = getIndex(x, y);  				//getIndex로 x좌표와 y좌표의 값으로 현재 배열의 위치를 구한다.
                  System.out.println("이동할 배열 좌표 위치 = ("+malIndex[0] + "," + malIndex[1]+")"); //이동할 곳의 배열의 위치를 콘솔창에 나타낸다.
                  System.out.println("지금은 player"+(myTurn+1)+" 차례");	//콘솔창에 차례를 나타내준다.
                  System.out.println("player1의 kingcount는 "+(k_count1));//각 플레이어의 kingcount를 콘솔창에 나타내준다.
                  System.out.println("player2의 kingcount는 "+(k_count2));
                
                  if (janggiBoard[malIndextmp[0]][malIndextmp[1]] / 10 == myTurn) //자신턴인 경우에만 자신말을 움직일 수 있다.
                  {
                	  if (malIndex[0] == malIndextmp[0] && malIndextmp[1] == malIndex[1]); // 가려는 곳에 내 말이 있는경우 아무일도 일어나지 않음
    /*말이 헷갈려서 하나로 정의함. 현재인덱스 = 움직인후 인덱스 , 이전인덱스= 움직이기전 인덱스*/
                	  
                	  else if (checkmove.CheckMalMove(janggiBoard, janggiBoard[malIndextmp[0]][malIndextmp[1]], malIndextmp, malIndex))
                	  {   //checkmove를 통해 선택된 이전 인덱스와 현재인덱스를 비교해서 움직일 수 있는 곳인지 판단한다. 움직일 수 없는 곳이면 checkmove에서 false로 반환하여 다시 선택해야한다.
                		
                		 if (janggiBoard[malIndex[0]][malIndex[1]] == NULL) //현재 인덱스가 비어있는 경우
                		 { 
                        	 if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 5 && malIndex[0] == 3) // 빨간자가 상대 진영에 도착해서 뒤 후로 변할경우
                   		  	{
                        		 janggiBoard[malIndex[0]][malIndex[1]] = 9; 			//현재인덱스는 자에서 후로 바뀌며
                        		 janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL; 	//이전 인덱스는 NULL값으로 초기화. 아무것도 없는 상태
                        		 checkJang(); 	//차례를 끝마침.
                   		  	}	 
                        	 else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 15 && malIndex[0]==0) //녹색자가 상대 진영에 도착해서 뒤 후로 변할경우
                   		  	{ 
                        		 janggiBoard[malIndex[0]][malIndex[1]] = 19;			//현재인덱스는 자에서 후로 바뀌며
                        		 janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;	//이전 인덱스는 NULL값으로 초기화. 아무것도 없는 상태
                        		 checkJang();	//차례를 끝마침.
                   		  	}
                        	 else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 1 && malIndex[0] == 3) //빨간 킹이 비어있는 녹색진영에 들어가서 카운트가 올라가는 경우           	                	  
                       	  	{
                       		  	janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //일단 그곳을 먹고
                       		  	janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
                       		  	k_count1 = 1; //카운트가 1이 증가한다.
                       		  	JOptionPane.showConfirmDialog(null, "왕이 녹색진영에 들어왔습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                       		  	checkJang(); //차례를 마침.
                       	  	}
                       	  	 else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 11 && malIndex[0] == 0)      //녹색 킹이 비어있는 빨간진영에 들어가서 카운트가 올라가는 경우                   	  
                       	  	{
                       	  		 janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //그곳을 먹고
                       	  		 janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
                       	  		 k_count2 = 1; //카운트가 1이 증가하고
                       	  	JOptionPane.showConfirmDialog(null, "왕이 빨간진영에 들어왔습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                       	  		 checkJang(); //차례를 마친다.
                       	  	}
                       	  	 else {	//그냥 비어있는 공간일 경우
                           janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //현재 인덱스에 이전 인덱스값을 넣고
                           janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL; //이전인덱스는 NULL로 초기화.
                           checkJang(); //차례를 마침.
                        	 }
                        } 
                		 
                   	  else if (janggiBoard[malIndex[0]][malIndex[1]] / 10 == janggiBoard[malIndextmp[0]][malIndextmp[1]] / 10);
                   	  //이동할 곳이 같은 편이 있을 경우 아무일도 일어나지 않음.
                   	  
                     else //현재 인덱스에 상대방 말이 있어서 이전인덱스가 잡아먹는 경우
                        {
                        	if (janggiBoard[malIndex[0]][malIndex[1]] == 11 ) //현재인덱스의 값이 player2의 왕인 경우
                           { //player1의 승리로 게임 끝
                              janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]];
                              janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
                              start = false;
                              JOptionPane.showConfirmDialog
                              (null, "player1의 승리", "안내", JOptionPane.WARNING_MESSAGE);
                              information.IsStart = false;
                              information.ResetTimer();
                              information.IsStart= false;
                  
                           }
                          
                           else if (janggiBoard[malIndex[0]][malIndex[1]] == 1 )	//현재인덱스의 값이 player1의 왕인경우
                           { //player2의 승리로 게임끝
                              janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]];
                              janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;
                              start = false;
                              JOptionPane.showConfirmDialog
                              (null, "Player2의 승리", "안내", JOptionPane.WARNING_MESSAGE);
                              information.IsStart = false;
                              information.ResetTimer();
                              information.IsStart=false;
                           }

                           else
                           {
                        	  if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 5 && malIndex[0] == 3) // 빨간자가 상대진영에 있는 현재인덱스를 먹고 뒤후가 되는경우
                        		  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]); 	//먹은 말(현재인덱스값)을패널에 나타내줌.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = 9;				//현재인덱스에 뒤 후 값을 넣어줌.
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;		//이전인덱스는 NULL값이 됨.
                        		  checkJang();//차례를 마침
                        		  }
                        	  else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 15 && malIndex[0]==0)  // 녹색자가 상대진영에 있는 현재인덱스를 먹고 뒤후가 되는경우 
                        		  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]); 	//먹은 말(현재인덱스값)을패널에 나타내줌.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = 19;				//현재 인덱스의 녹색 뒤후를 넣어줌
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;		//이전 인덱스는 NULL값이 됨.
                        		  checkJang();//차례를 마침.
                        		  }
                        	  else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 1 && malIndex[0] == 3)   //빨간 킹이 녹색진영에 들어가서 카운트가 올라가는 경우
                        	  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]);	//먹은 말(현재인덱스값)을패널에 나타내줌.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //현재인덱스에 이전인덱스값을 넣어줌.
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL; //이전 인덱스는 NULL값이 됨.
                        		  k_count1 = 1; //카운트는 증가
                        		  JOptionPane.showConfirmDialog(null, "왕이 녹색진영에 들어왔습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                        		  checkJang();//차례를 마침.
                        	  }
                        	  else if(janggiBoard[malIndextmp[0]][malIndextmp[1]] == 11 && malIndex[0] == 0)   //녹색킹이 빨간진영에 들어가서 카운트가 올라가는 경우
                        	  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]);	//먹은 말(현재인덱스값)을패널에 나타내줌.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //현재인덱스에 이전인덱스값을 넣어줌.
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;	//이전 인덱스는 NULL값이 됨.
                        		  k_count2 = 1; //카운트는 증가
                        		  JOptionPane.showConfirmDialog(null, "왕이 빨간진영에 들어왔습니다.", "경고", JOptionPane.WARNING_MESSAGE);
                        		  checkJang();	//차례를 마침.
                        	  }
                        	  else  //특수한 경우가 아니고, 이전인덱스가 현재인덱스를 먹는경우
                        	  {
                        		  setPlayerEatMal(janggiBoard[malIndex[0]][malIndex[1]]); //먹은 말(현재인덱스값)을패널에 나타내줌.
                        		  janggiBoard[malIndex[0]][malIndex[1]] = janggiBoard[malIndextmp[0]][malIndextmp[1]]; //현재인덱스에 이전인덱스값을 넣어줌.
                        		  janggiBoard[malIndextmp[0]][malIndextmp[1]] = NULL;	//이전 인덱스는 NULL값이 됨.
                        		  checkJang(); //차례를 마침.
                        	  }
                           }
                        }
                     }
                  }
                  //차례가 끝나고 현재 말판의 상태를 갱신해준다.
                  gameZone.removeAll();				
                  gameZone = Locate(janggiBoard, gameZone);	
                  gameZone.repaint();					
                  System.out.println();
               }
            }
            
            @Override
            public void mousePressed(MouseEvent e) { //그냥 누르기만 했을때도 배열의 위치를 콘솔창을 통해 볼 수 있게 한다.
               // TODO Auto-generated method stub
               JButton btn = (JButton) e.getSource();
               btn.setSelected(false);
               x = e.getXOnScreen() - mainFrame.getX() - 50;//
               y = e.getYOnScreen() - mainFrame.getY() - 50;//
               malIndextmp = getIndex(x, y); // 좌표 x,y값을 getIndex를 통해 배열의 위치를 나타냄.
               System.out.println("클릭 한 위치 배열 좌표 "+ malIndextmp[0] + ", " + malIndextmp[1]);
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
                  if (x < 700 && x > 100 && y < 700 && y > 100) //말이 움직일수있는 범위
                	  btn.setLocation(x -45, y - 80); //움직이는 말을 드래그할때 위치하는 마우스의 위치
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

   void setPlayerEatMal(int killedMal) { //먹히는말이 여기로 와서 JLabel로 PlayerPanel에 나타나서 보여준다.
	   String MalImg = null;
	   //빨간색 말을 먹는경우
	   	if(myTurn == 1) {
	         if(killedMal ==1)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90king.png";
	         else if(killedMal ==3)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0sang.png";
	         else if (killedMal ==5 || killedMal ==9)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0ja.png";
	         else if(killedMal==7)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0cha.png";
	   	}
	    //초록색 말을 먹는경우
	   	else {
	         if(killedMal == 11)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90king.png";
	         else if(killedMal ==13)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0sang.png";
	         else if (killedMal ==15 || killedMal ==19)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0ja.png";
	         else if(killedMal==17)
	        	 MalImg = "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0cha.png";
	   	}	   	
	    Information.PlayerPanel[myTurn].add(new JLabel(new ImageIcon(MalImg)));
	    //먹은말을 자신의 차례의 PlayerPanel에  추가한다.
   	}
   
   public int[] getIndex(int ox, int oy) {
      int[] Index = new int[2]; //index[0]과 index[1]을 생성함
      int i = 0 , j = 0;	
      if (ox >=50 && ox <= 150)      	//클릭한 x좌표 위치의 범위에 따라 선택된 x좌표 배열이 결정
      		i = 0;					 	
      else if(ox >= 170 && ox <= 270)	// 다양한 방법들을 고민했지만 좌표값에 따른 배열판단이 가장 정확한 방법일 것 같아서
    	  	i = 1;						// 하나하나의 좌표를 구하고 좌표값에 나눠서 배열 [x],[y]의 값을  구하는 방법을 선택.
      else if(ox >= 310 && ox <= 410)
    	  	i = 2;
      else if(ox >= 440 && ox <= 540)
    	  	i = 3;
      
      if(oy >= 180 && oy <= 280) 		//클릭한 y좌표 위치의 범위에 따라 선택된 y좌표 배열이 결정
    	  	j = 0;
      else if(oy >= 300 && oy <=400)
    	  	j = 1;
      else if(oy >= 410 && oy <= 510)
    	  	j = 2;
      
      Index[0] = i;
      Index[1] = j;   
      return Index;
   }
   
   //board배열에 있는 장기말을 장기판에 이미지로 나타내준다. 
   public JPanel Locate(int[][] board, JPanel Janggi) {
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 3;j++) {
            if (board[i][j] == r90king) {
               mal r90king = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90king.png");
               r90king.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90king.png");
               Janggi.add(r90king);
            } else if (board[i][j] == r0sang) {
               mal r0sang = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0sang.png");
               r0sang.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0sang.png");
               Janggi.add(r0sang);
            } else if (board[i][j] == r90sang) {
               mal r90sang = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90sang.png");
               r90sang.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90sang.png");
               Janggi.add(r90sang);
            } else if (board[i][j] ==r0ja) {
               mal r0ja = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0ja.png");
               r0ja.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0ja.png");
               Janggi.add(r0ja);
            } else if (board[i][j] == r90ja ) {
               mal r90ja  = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90ja.png");
               r90ja.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90ja.png");
               Janggi.add(r90ja );
            } else if (board[i][j] == r0cha) {
               mal r0cha = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0cha.png");
               r0cha.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0cha.png");
               Janggi.add(r0cha);
            } else if (board[i][j] == r90cha) {
               mal r90cha = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90cha.png");
               r90cha.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90cha.png");
               Janggi.add(r90cha);
            } else if (board[i][j] == r0who) {
               mal r0who = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0who.png");
               r0who.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r0who.png");
               Janggi.add(r0who);
            } else if (board[i][j] == r90who) {
               mal r90who = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90who.png");
               r90who.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기빨간말\\r90who.png");
               Janggi.add(r90who);
            }

            else if (board[i][j] == g90king) {
               mal g90king = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90king.png");
               g90king.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90king.png");
               Janggi.add(g90king);
            } else if (board[i][j] == g0sang) {
               mal g0sang = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0sang.png");
               g0sang.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0sang.png");
               Janggi.add(g0sang);   
            }
            else if (board[i][j] == g90sang) {
               mal g90sang = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90sang.png");
               g90sang.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90sang.png");
               Janggi.add(g90sang);
            } else if (board[i][j] == g0ja) {
               mal g0ja = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0ja.png");
               g0ja.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0ja.png");
               Janggi.add(g0ja);
            } else if (board[i][j] == g90ja) {
               mal g90ja = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90ja.png");
               g90ja.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90ja.png");
               Janggi.add(g90ja);
            } else if (board[i][j] == g0cha) {
               mal g0cha = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0cha.png");
               g0cha.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0cha.png");
               Janggi.add(g0cha);
            } else if (board[i][j] == g90cha) {
               mal g90cha = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90cha.png");
               g90cha.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90cha.png");
               Janggi.add(g90cha);
            } else if (board[i][j] == g0who) {
               mal g0who = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0who.png");
               g0who.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g0who.png");
               Janggi.add(g0who);
            } else if (board[i][j] == g90who) {
                  mal g90who = new mal(i, j, "C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90who.png");
                  g90who.setName("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\십이장기초록말\\g90who.png");
                  Janggi.add(g90who);
               }
         }
      }
      return Janggi;
   }

   public void checkJang() { //말의 움직임이 끝나거나 시간이 끝나거나 등등 자신의 차례가 끝나면 턴을 바꾸는 역할을 해준다.
      if (myTurn == 0 && k_count2 != 1) { //차례가 0이고 왕카운트가 1이 아니면
         myTurn = 1; 						//차례가 1로 바뀌고
         information.setTurnIsChangeToTrue(); 
      } 
      
      //왕이 상대 진영에서 한차례 버티면 게임이 승리된다. 이를 k_count를 확인해서 승패를 결정.
      else if(myTurn == 1 && k_count1 == 1) //player2의 차례에서 player1의 왕이 상대진영에 들어가서 한차례 버틴 상태면
 	  {										//player1의 승리로 끝난다.
           start = false;					//게임 끝
           JOptionPane.showConfirmDialog(null, "Player1의 승리", "안내", JOptionPane.WARNING_MESSAGE);
           information.IsStart = false;		//
           information.ResetTimer();		//타이머 리셋
           information.IsStart=false;			
        } 
      
      else if(myTurn == 0 && k_count2 == 1)	//player1의 차례에서 player2의 왕이 상대진영에 들어가서 한차례 버틴 상태면
 	  {										//player2의 승리로 끝난다.
           start = false;
           JOptionPane.showConfirmDialog(null,
           "Player2의 승리", "안내", JOptionPane.WARNING_MESSAGE);
           information.IsStart = false;
           information.ResetTimer();
           information.IsStart=false;       
        }
      
      else { 								//모든 경우의수가 아니면 차례가 player1이고  왕카운트가 1이 아닌 경우로 판단
         myTurn = 0;						//player2의 턴으로 바뀐다.
         information.setTurnIsChangeToTrue();
      }
   }
   
   public static void changeTurn() //차례를 바꿔준다.
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