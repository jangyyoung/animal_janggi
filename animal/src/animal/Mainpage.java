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
	Image img1 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\����ȭ��\\bg-1.png");
	Image img2 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\����ȭ��\\��Ģ.png");
	JPanel panel; //�г� 1 - ���� ù ���� ȭ�� ������
	JPanel panel2; //�г� 2 - ���� �� ���� ȭ�� ������
	JButton main_button1; //������� ��ư ������
	JButton main_button2; //�÷��� ��ư ������
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
		setSize(1000, 690); //�ʱ� ������ 		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel); 
	
		setBounds(0, 0, 1000, 690);
		setSize(1000, 690); //�ʱ� ������ ����
		
		panel.setLayout(null); //panel.setLayout�� ��������ڸ� �ǹ��ϴµ�, ���࿡ ()�� ��ȣ �ȿ� null�� ���� ��쿡 ���� �����ڰ� �����.���� null�� ������� ���¿���  65�� ���� main_button1.setBounds(100, 500, 700, 300); ���ڸ� �ٲ㼭 ��ġ�� �����ϸ� ��.

		ImageIcon normal_janggi_button = new ImageIcon("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��ư����\\��������ư1.png"); //�̹����� ����
		ImageIcon press_janggi_button = new ImageIcon("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��ư����\\��������ư2.png"); //���콺 Ŭ���� ���� �̹���
      
		ImageIcon normal_play_button = new ImageIcon("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��ư����\\�÷��̹�ư1.png"); //�̹����� ����
		ImageIcon press_play_button = new ImageIcon("C:\\Users\\aenor\\OneDrive\\����\\GitHub\\GeniusJanggi\\images\\��ư����\\�÷��̹�ư2.png"); //���콺 Ŭ���� ���� �̹���
		
		JButton main_button1 = new JButton(normal_janggi_button); // ��� ��ư �̹����� main_button1�� �߰�
		main_button1.setPressedIcon(press_janggi_button); //main_button1�� ������ �̹��� �߰�
		
		JButton main_button2 = new JButton(normal_play_button); //�÷��� ��ư �̹����� main_button2�� �߰�
		main_button2.setPressedIcon(press_play_button); //main_button2�� ���� �� �̹��� �ٲ�
		
	    //��ư ����ȭ
		main_button1.setBorderPainted(false);
		main_button1.setFocusPainted(false);
		main_button1.setContentAreaFilled(false);

		main_button2.setBorderPainted(false);
		main_button2.setFocusPainted(false);
		main_button2.setContentAreaFilled(false);
		
		//�гο� ��ư �߰�
		panel.add(main_button1); 
		main_button1.addActionListener(new ActionListener(){ //���콺�� ������� ��ư Ŭ���ϸ�
        public void actionPerformed(ActionEvent e){
                panel.setVisible(false); //panel 1�� �Ⱥ��̰� �˴ϴ�.
                panel2 = new JPanel() //panel 2�� ���⼭ �����ϰ�
        		{ 
        			public void paintComponent(Graphics g) 
        			{ 
        				g.drawImage(img2, 0, 0, 1000, 690 , this); 
        				//panel 2�� ���ӷ� �̹����� �ֽ��ϴ�.
        			} 
        		};
        		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		add(panel2); 
        		panel2.add(main_button2); //panel 2�� �÷��� ��ư�� �߰��մϴ�
        		setBackground(Color.BLACK);//������� �����
        		setSize(1000, 690); //�ʱ� ������ 	         		
        		setVisible(true); //�þ߿� ���̰� �մϴ�
        		main_button2.addActionListener(new ActionListener(){
        			//panel 2�� �ִ� �÷��� ��ư�� Ŭ���Ǹ�
        	        public void actionPerformed(ActionEvent e){
        	                panel2.setVisible(false); //panel 2�� �Ⱥ��̰� �˴ϴ�
        	                new Janggi(); //Janggi�� �Ѿ�� ȭ���� �ٲ�� �˴ϴ�.
        	        }
        	        
        		}
        	);	
        }
    });
		setVisible(true); 		
		main_button1.setBounds(380, 480, 220, 100); //������� ��ư ��ġ, ��, ���� ����
		// b.setBounds(x��ǥ, y��ǥ, ���α���, ���α���); //����ư ��ġ
	}
	public static void main(String[] args) 
	{ 
		Mainpage G = new Mainpage();
		 G.setTitle("�� ���Ͼ - ������Ÿ��");
		 
	} 
}