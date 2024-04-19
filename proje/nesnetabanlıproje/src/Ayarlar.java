import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Ayarlar implements Database1 {
private int id1;
private Connection con = null;
private PreparedStatement preparedStatement=null;

private PreparedStatement preparedStatement2=null;

private String gercekeposta;
private String gerceknumara;
// gerceknumara ve gercekeposta veritabanından alınır ancak eposta ve numara değişkenlerini kullanici girer


	
	public Ayarlar(int id1) {
		String url="jdbc:mysql://" + Database1.host  + ":" + Database1.port + "/" + Database1.db_ismi+"?useUnicode=true&characterEncoding=utf8";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
		} catch(ClassNotFoundException ex) {
		
		}
		try {
			con=DriverManager.getConnection(url,Database1.kullanici_adi,Database1.parola);
			
			
		} catch (SQLException ex) {
		
		}
		
		this.id1=id1;
		
		
		}
public void sifredegisterme() {

	Scanner scan=new Scanner(System.in);
		System.out.print("Lütfen epostanızı girin:");
		String eposta=scan.nextLine();
		
		System.out.print("Lütfen telefon numaranızı girin:");
		String numara=scan.nextLine();
		
		
	String sorgu="Select * from kullanicibilgi where eposta=? and telefonnumarasi=?";
		try {
			
			preparedStatement=con.prepareStatement(sorgu);
			preparedStatement.setString(1, eposta);
			preparedStatement.setString(2, numara);
			
			ResultSet rs=preparedStatement.executeQuery();
			
			while(rs.next()) {
				this.id1=rs.getInt("id");
			 this.gercekeposta=rs.getString("eposta");
			 this.gerceknumara=rs.getString("telefonnumarasi");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(eposta.equals(this.gercekeposta) && numara.equals(this.gerceknumara)) {
			System.out.print("Yeni sifrenizi giriniz:");
			String yenisifre=scan.nextLine();
			if(yenisifre.length()==6) {
			String sorgu3="Update kullanicibilgi Set sifre=? WHERE id=?";
			try {
				preparedStatement2=con.prepareStatement(sorgu3);
				
				preparedStatement2.setString(1,yenisifre);
				preparedStatement2.setInt(2,id1);
				preparedStatement2.executeUpdate();
				System.out.println("Şifreniz başarıyla değiştirildi");
				
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("Şifreniz değiştirilemedi");
			}
			
			}
			else {
				System.out.println("Sifrenizin uzunluğu 6 haneli olmalidir");
			}
		}
		else {
			System.out.println("Yanliş eposta veya telefon numarasi ");
		}
		
		
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
}
