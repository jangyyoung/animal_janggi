package animal;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel; 
    
@SuppressWarnings("serial")
public class Mainpage extends JFrame{ 
	Image img1 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\게임화면\\bg-1.png");
	Image img2 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\게임화면\\규칙.png");
	JPanel panel; //패널 1 - 게임 첫 번쨰 화면 구현용
	JPanel panel2; //패널 2 - 게임 두 번쨰 화면 구현용
	JButton main_button1; //십이장기 버튼 구현용
	JButton main_button2; //플레이 버튼 구현용
	public Mainpage() 
	{ 
		panel = new JPanel() 
		{ 
			public void paintComponent(Graphics g) 
			{ 
				g.drawImage(img1, 0, 0, 1000, 690, this); 
			} 
		};
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel); 
	
		setBounds(0, 0, 1000, 690);
		setSize(1000, 690); //초기 사이즈 		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel); 
	
		setBounds(0, 0, 1000, 690);
		setSize(1000, 690); //초기 사이즈 정의
		
		panel.setLayout(null); //panel.setLayout이 절대관리자를 의미하는데, 만약에 ()이 괄호 안에 null이 있을 경우에 절대 관리자가 실행됨.따라서 null을 적어놓은 상태에서  65번 줄의 main_button1.setBounds(100, 500, 700, 300); 숫자를 바꿔서 위치를 결정하면 됨.

		ImageIcon normal_janggi_button = new ImageIcon("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\버튼종류\\십이장기버튼1.png"); //이미지들 정의
		ImageIcon press_janggi_button = new ImageIcon("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\버튼종류\\십이장기버튼2.png"); //마우스 클릭될 때의 이미지
      
		ImageIcon normal_play_button = new ImageIcon("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\버튼종류\\플레이버튼1.png"); //이미지들 정의
		ImageIcon press_play_button = new ImageIcon("C:\\Users\\aenor\\OneDrive\\문서\\GitHub\\GeniusJanggi\\images\\버튼종류\\플레이버튼2.png"); //마우스 클릭될 떄의 이미지
		
		JButton main_button1 = new JButton(normal_janggi_button); // 장기 버튼 이미지를 main_button1에 추가
		main_button1.setPressedIcon(press_janggi_button); //main_button1이 눌릴때 이미지 추가
		
		JButton main_button2 = new JButton(normal_play_button); //플레이 버튼 이미지를 main_button2에 추가
		main_button2.setPressedIcon(press_play_button); //main_button2가 눌릴 때 이미지 바뀜
		
	    //버튼 투명화
		main_button1.setBorderPainted(false);
		main_button1.setFocusPainted(false);
		main_button1.setContentAreaFilled(false);

		main_button2.setBorderPainted(false);
		main_button2.setFocusPainted(false);
		main_button2.setContentAreaFilled(false);
		
		//패널에 버튼 추가
		panel.add(main_button1); 
		main_button1.addActionListener(new ActionListener(){ //마우스로 십이장기 버튼 클릭하면
        public void actionPerformed(ActionEvent e){
                panel.setVisible(false); //panel 1이 안보이게 됩니다.
                panel2 = new JPanel() //panel 2를 여기서 생성하고
        		{ 
        			public void paintComponent(Graphics g) 
        			{ 
        				g.drawImage(img2, 0, 0, 1000, 690 , this); 
        				//panel 2에 게임룰 이미지를 넣습니다.
        			} 
        		};
        		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		add(panel2); 
        		panel2.add(main_button2); //panel 2에 플레이 버튼을 추가합니다
        		setBackground(Color.BLACK);//검정배경 만들기
        		setSize(1000, 690); //초기 사이즈 	         		
        		setVisible(true); //시야에 보이게 합니다
        		main_button2.addActionListener(new ActionListener(){
        			//panel 2에 있는 플레이 버튼이 클릭되면
        	        public void actionPerformed(ActionEvent e){
        	                panel2.setVisible(false); //panel 2가 안보이게 됩니다
        	                new Janggi(); //Janggi로 넘어가서 화면이 바뀌게 됩니다.
        	        }
        	        
        		}
        	);	
        }
    });
		setVisible(true); 		
		main_button1.setBounds(380, 480, 220, 100); //십이장기 버튼 위치, 폭, 넓이 지정
		// b.setBounds(x좌표, y좌표, 가로길이, 세로길이); //장기버튼 위치
	}
	public static void main(String[] args) 
	{ 
		Mainpage G = new Mainpage();
		 G.setTitle("더 지니어스 - 프로토타입");
		 
	} 
}