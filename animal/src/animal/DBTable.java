package animal;
import java.sql.*;
public class DBTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con=null;
		Statement stmt=null;
		String url="";
		String user="���� �̸�";
		String passwd="���� ��й�ȣ";
		
		try {
			//�����ͺ��̽��� ����
			Class.forName("");
			con=DriverManager.getConnection(url,user,passwd);
			stmt=con.createStatement();
			String createStr="CREATE TABLE member (name varchar(20) not null, nickname varchar(20) not null,"+"id varchar(20) not null, password varchar(20) not null, email varchar(40) not null,"+"win int not null, lose int not null, PRIMARY KEY (nickname, id))";
			stmt.executeUpdate(createStr);
			System.out.println("[Server] ���̺� ���� ����");
		} catch(Exception e) {
			System.out.println("[Server] �����ͺ��̽� ���� Ȥ�� ���̺� ������ ���� �߻�>"+e.toString());
		}

	}

}
