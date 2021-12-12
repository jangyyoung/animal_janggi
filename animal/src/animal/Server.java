package animal;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Server {
	ServerSocket ss=null;
	Vector<CCUser> alluser;
	Vector<CCUser> waituser;
	Vector<Room> room;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server=new Server();
		server.alluser=new Vector<>();
		server.waituser=new Vector<>();
		server.room=new Vector<>();
		try {
			server.ss=new ServerSocket(1228);
			System.out.println("[Server] ���� �غ� �Ϸ�");
			 while(true) {
				 Socket socket=server.ss.accept();
				 CCUser c= new CCUser(socket,server);
				 c.start();
			 }
		}catch(SocketException e) {
			System.out.println("[Server]���� ���� ����>"+e.toString());
		}catch(IOException e) {
			System.out.println("[Server]����� ����>"+e.toString());
		}

	}

}
class CCUser extends Thread{
	Server server;
	Socket socket;

	/* �� ��ü�� Vector�� ���� */
	Vector<CCUser> auser;	//����� ��� Ŭ���̾�Ʈ
	Vector<CCUser> wuser;	//���ǿ� �ִ� Ŭ���̾�Ʈ
	Vector<Room> room;		//������ Room
	
	Database db = new Database();
	
	/* �޽��� �ۼ����� ���� �ʵ� */
	OutputStream os;
	DataOutputStream dos;
	InputStream is;
	DataInputStream dis;
	
	String msg;			//���� �޽����� ������ �ʵ�
	String nickname;	//Ŭ���̾�Ʈ�� �г����� ������ �ʵ�
	
	Room myRoom;		//������ �� ��ü�� ������ �ʵ�
	
	/* �� �޽����� �����ϱ� ���� �±� */
	final String loginTag = "LOGIN";	//�α���
	final String joinTag = "JOIN";		//ȸ������
	final String overTag = "OVER";		//�ߺ�Ȯ��
	final String viewTag = "VIEW";		//ȸ��������ȸ
	final String changeTag = "CHANGE";	//ȸ����������
	final String rankTag = "RANK";		//������ȸ(��üȸ��)
	final String croomTag = "CROOM";	//�����
	final String vroomTag = "VROOM";	//����
	final String uroomTag = "UROOM";	//������
	final String eroomTag = "EROOM";	//������
	final String cuserTag = "CUSER";	//��������
	final String searchTag = "SEARCH";	//������ȸ(�Ѹ�)
	final String pexitTag = "PEXIT";	//���α׷�����
	final String rexitTag = "REXIT";	//������
	final String janggiTag = "JANGGI";		//����
	final String winTag = "WIN";		//�¸�
	final String loseTag = "LOSE";		//�й�
	final String recordTag = "RECORD";	//����������Ʈ
	
	CCUser(Socket _s, Server _ss) {
		this.socket = _s;
		this.server = _ss;
		
		auser = server.alluser;
		wuser = server.waituser;
		room = server.room;
	}
	public void run() {
		try {
			System.out.println("[Server]Ŭ���̾�Ʈ ���� > "+this.socket.toString());
			os=this.socket.getOutputStream();
			dos=new DataOutputStream(os);
			is=this.socket.getInputStream();
			dis=new DataInputStream(is);
			
			while(true) {
				msg=dis.readUTF();
				String[] m=msg.split("//");
				if(m[0].equals(loginTag)) {
					String mm=db.loginCheck(m[1],m[2]);
					
					if(!mm.equals("null")){
						nickname=mm;
						auser.add(this);
						wuser.add(this);
						dos.writeUTF(loginTag+"//OKAY");
					    sendWait(connectedUser());
					    if(room.size()>0) {
					     sendWait(roomInfo());
					    }
					    
					}
					else {
						dos.writeUTF(loginTag+"//FAIL");
					}
				}
			
			/* ȸ������ */
			else if(m[0].equals(joinTag)) {
				if(db.joinCheck(m[1], m[2], m[3], m[4], m[5])) {	//ȸ������ ����
					dos.writeUTF(joinTag + "//OKAY");
				}
				
				else {	//ȸ������ ����
					dos.writeUTF(joinTag + "//FAIL");
				}
			}  //ȸ������ if��
			
			/* �ߺ�Ȯ�� */
			else if(m[0].equals(overTag)) {
				if(db.overCheck(m[1], m[2])) {	//��� ����
					dos.writeUTF(overTag + "//OKAY");
				}
				
				else {	//��� �Ұ���
					dos.writeUTF(overTag + "//FAIL");
				}
			}  //�ߺ�Ȯ�� if��
			
			/* ȸ������ ��ȸ */
			else if(m[0].equals(viewTag)) {
				if(!db.viewInfo(nickname).equals("null")) {	//��ȸ ����
					dos.writeUTF(viewTag + "//" + db.viewInfo(nickname));	//�±׿� ��ȸ�� ������ ���� ����
				}
				
				else {	//��ȸ ����
					dos.writeUTF(viewTag + "//FAIL");
				}
			}  //ȸ������ ��ȸ if��
			
			/* ȸ������ ���� */
			else if(m[0].equals(changeTag)) {
				if(db.changeInfo(nickname, m[1], m[2])) {	//���� ����
					dos.writeUTF(changeTag + "//OKAY");
				}
				
				else {	//���� ����
					dos.writeUTF(changeTag + "//FAIL");
				}
			}  //ȸ������ ���� if��
			
			/* ��ü ���� ��ȸ */
			else if(m[0].equals(rankTag)) {
				if(!db.viewRank().equals("")) {	//��ȸ ����
					dos.writeUTF(rankTag + "//" + db.viewRank());	//�±׿� ��ȸ�� ������ ���� ����
				}
				
				else {	//��ȸ ����
					dos.writeUTF(rankTag + "//FAIL");
				}
			}  //��ü ���� ��ȸ if��

			/* �� ���� */
			else if(m[0].equals(croomTag)) {
				myRoom = new Room();	//���ο� Room ��ü ���� �� myRoom�� �ʱ�ȭ
				myRoom.title = m[1];	//�� ������ m[1]�� ����
				myRoom.count++;			//���� �ο��� �ϳ� �߰�
				
				room.add(myRoom);		//room �迭�� myRoom�� �߰�
				
				myRoom.ccu.add(this);	//myRoom�� �����ο��� Ŭ���̾�Ʈ �߰�
				wuser.remove(this);		//���� ���� �ο����� Ŭ���̾�Ʈ ����
				
				dos.writeUTF(croomTag + "//OKAY");
				System.out.println("[Server] "+ nickname + " : �� '" + m[1] + "' ����");
				
				sendWait(roomInfo());	//���� ���� �ο��� �� ����� ����
				sendRoom(roomUser());	//�濡 ������ �ο��� �� �ο� ����� ����
			}  //�� ���� if��
			
			/* �� ���� */
			else if(m[0].equals(eroomTag)) {
				for(int i=0; i<room.size(); i++) {	//������ ���� ������ŭ �ݺ�
					Room r = room.get(i);
					if(r.title.equals(m[1])) {	//�� ������ ����
						
						if(r.count < 2) {			//�� �ο����� 2���� ���� �� ���� ����
							myRoom = room.get(i);	//myRoom�� �� ������ �´� i��° room�� �ʱ�ȭ
							myRoom.count++;			//���� �ο��� �ϳ� �߰�
							
							wuser.remove(this);		//���� ���� �ο����� Ŭ���̾�Ʈ ����
							myRoom.ccu.add(this);	//myRoom�� ���� �ο��� Ŭ���̾�Ʈ �߰�
							
							sendWait(roomInfo());	//���� ���� �ο��� �� ����� ����
							sendRoom(roomUser());	//�濡 ������ �ο��� �� �ο� ����� ����
							
							dos.writeUTF(eroomTag + "//OKAY");
							System.out.println("[Server] " + nickname + " : �� '" + m[1] + "' ����");
						}
						
						else {	//�� �ο����� 2�� �̻��̹Ƿ� ���� ����
							dos.writeUTF(eroomTag + "//FAIL");
							System.out.println("[Server] �ο� �ʰ�. ���� �Ұ���");
						}
					}
					
					else {	//���� �� ������ ������ ���� ����
						dos.writeUTF(eroomTag + "//FAIL");
						System.out.println("[Server] " + nickname + " : �� '" + m[1] + "' ���� ����");
					}
				}
			} //�� ���� if��
			
			/* ���� ��ȸ */
			else if(m[0].equals(searchTag)) {
				String mm = db.searchRank(m[1]);
				
				if(!mm.equals("null")) {	//��ȸ ����
					dos.writeUTF(searchTag + "//" + mm);	//�±׿� ��ȸ�� ������ ���� ����
				}
				
				else {	//��ȸ ����
					dos.writeUTF(searchTag + "//FAIL");
				}
			} //���� ��ȸ if��
			
			/* ���α׷� ���� */
			else if(m[0].equals(pexitTag)) {
				auser.remove(this);		//��ü ���� �ο����� Ŭ���̾�Ʈ ����
				wuser.remove(this);		//���� ���� �ο����� Ŭ���̾�Ʈ ����
				
				sendWait(connectedUser());	//���� ���� �ο��� ��ü ���� �ο��� ����
			} //���α׷� ���� if��
			
			/* �� ���� */
			else if(m[0].equals(rexitTag)) {
				myRoom.ccu.remove(this);	//myRoom�� ���� �ο����� Ŭ���̾�Ʈ ����
				myRoom.count--;				//myRoom�� �ο��� �ϳ� ����
				wuser.add(this);			//���� ���� �ο��� Ŭ���̾�Ʈ �߰�
				
				System.out.println("[Server] " + nickname + " : �� '" + myRoom.title + "' ����");
				
				if(myRoom.count==0) {	//myRoom�� �ο����� 0�̸� myRoom�� room �迭���� ����
					room.remove(myRoom);
				}
				
				if(room.size() != 0) {	//������ room�� ������ 0�� �ƴϸ� �濡 ������ �ο��� �� �ο� ����� ����
					sendRoom(roomUser());
					
				}
				
				sendWait(roomInfo());		//���� ���� �ο��� �� ����� ����
				sendWait(connectedUser());	//���� ���� �ο��� ��ü ���� �ο��� ����
			} //�� ���� if��
			/* ���� */
			else if(m[0].equals(janggiTag)) {
				for(int i=0; i<myRoom.ccu.size(); i++) {	//myRoom�� �ο�����ŭ �ݺ�
					
					if(!myRoom.ccu.get(i).nickname.equals(nickname)) {	//�� ���� �ο� �� Ŭ���̾�Ʈ�� �ٸ� �г����� Ŭ���̾�Ʈ���Ը� ����
						myRoom.ccu.get(i).dos.writeUTF(janggiTag + "//" + m[1] + "//" + m[2] + "//" + m[3]);
					}
				}
			}  //���� if��
			
			/* �¸� �� ���� ������Ʈ */
			else if(m[0].equals(winTag)) {
				System.out.println("[Server] " + nickname + " �¸�");
				
				if(db.winRecord(nickname)) {	//���� ������Ʈ�� �����ϸ� ������Ʈ ������ ����
					dos.writeUTF(recordTag + "//OKAY");
				} else {						//���� ������Ʈ�� �����ϸ� ������Ʈ ���и� ����
					dos.writeUTF(recordTag + "//FAIL");
				}
				
				for(int i=0; i<myRoom.ccu.size(); i++) {	//myRoom�� �ο�����ŭ �ݺ�
					
					/* �� ���� �ο� �� Ŭ���̾�Ʈ�� �ٸ� �г����� Ŭ���̾�Ʈ�϶��� */
					if(!myRoom.ccu.get(i).nickname.equals(nickname)) {
						myRoom.ccu.get(i).dos.writeUTF(loseTag + "//");
						
						if(db.loseRecord(myRoom.ccu.get(i).nickname)) {	//���� ������Ʈ�� �����ϸ� ������Ʈ ������ ����
							myRoom.ccu.get(i).dos.writeUTF(recordTag + "//OKAY");
						} else {										//���� ������Ʈ�� �����ϸ� ������Ʈ ���и� ����
							myRoom.ccu.get(i).dos.writeUTF(recordTag + "//FAIL");
						}
					}
				}
			}  //�¸� �� ���� ������Ʈ if��
			
			/* �й�, ��� �� ���� ������Ʈ */
			else if(m[0].equals(loseTag)) {
				if(myRoom.count==1) {	//����� �ߴµ� �� ���� �ο��� 1���� �� ���� �̹ݿ��� ����
					dos.writeUTF(recordTag + "//NO");
				}
				
				else if(myRoom.count==2) {	//��� �� �й踦 ���� �� �� ���� �ο��� 2���� ��
					dos.writeUTF(loseTag + "//");
					
					if(db.loseRecord(nickname)) {	//���� ������Ʈ�� �����ϸ� ������Ʈ ������ ����
						dos.writeUTF(recordTag + "//OKAY");
					} else {						//���� ������Ʈ�� �����ϸ� ������Ʈ ���и� ����
						dos.writeUTF(recordTag + "//FAIL");
					}
					
					for(int i=0; i<myRoom.ccu.size(); i++) {	//myRoom�� �ο�����ŭ �ݺ�
						
						/* �� ���� �ο� �� Ŭ���̾�Ʈ�� �ٸ� �г����� Ŭ���̾�Ʈ�϶��� */
						if(!myRoom.ccu.get(i).nickname.equals(nickname)) {
							myRoom.ccu.get(i).dos.writeUTF(winTag + "//");
							
							if(db.winRecord(myRoom.ccu.get(i).nickname)) {	//���� ������Ʈ�� �����ϸ� ������Ʈ ������ ����
								myRoom.ccu.get(i).dos.writeUTF(recordTag + "//OKAY");
							} else {										//���� ������Ʈ�� �����ϸ� ������Ʈ ���и� ����
								myRoom.ccu.get(i).dos.writeUTF(recordTag + "//FAIL");
							}
						}
					}
				}
			}  //�й�, ��� �� ���� ������Ʈ if��
		}  //while��
	} catch(IOException e) {
		System.out.println("[Server] ����� ���� > " + e.toString());
	}
}  //run()
String roomInfo() {
	String msg=vroomTag+"//";
	for(int i=0;i<room.size();i++) {
		msg=msg+room.get(i).title+":"+room.get(i).count+"@";
	}
	return msg;
}
String roomUser() {
	String msg=uroomTag+"//";
	for(int i=0;i<myRoom.ccu.size();i++) {
		msg=msg+myRoom.ccu.get(i).nickname+"@";
	}
	return msg;
}
String connectedUser() {
	String msg=cuserTag+"//";
	for(int i=0;i<auser.size();i++) {
		msg=msg+auser.get(i).nickname+"@";
	}
	return msg;
}
void sendWait(String m) {
	for(int i=0;i<wuser.size();i++) {
		try {
			wuser.get(i).dos.writeUTF(m);
		}catch(IOException e) {
			wuser.remove(i--);
		}
	}
}
void sendRoom(String m) {
	for(int i=0;i<myRoom.ccu.size();i++) {
		try {
			myRoom.ccu.get(i).dos.writeUTF(m);
		}catch(IOException e) {
			myRoom.ccu.remove(i--);
		}
	}
}
}

			
		
	
