package animal;
import java.sql.*;
public class DBTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con=null;
		Statement stmt=null;
		String url="";
		String user="계정 이름";
		String passwd="계정 비밀번호";
		
		try {
			//데이터베이스와 연결
			Class.forName("");
			con=DriverManager.getConnection(url,user,passwd);
			stmt=con.createStatement();
			String createStr="CREATE TABLE member (name varchar(20) not null, nickname varchar(20) not null,"+"id varchar(20) not null, password varchar(20) not null, email varchar(40) not null,"+"win int not null, lose int not null, PRIMARY KEY (nickname, id))";
			stmt.executeUpdate(createStr);
			System.out.println("[Server] 테이블 생성 성공");
		} catch(Exception e) {
			System.out.println("[Server] 데이터베이스 연결 혹은 테이블 생성에 문제 발생>"+e.toString());
		}

	}

}
