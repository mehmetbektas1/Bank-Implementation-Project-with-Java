import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bilgilerigoster implements Database1{
	private int id1;
	private Connection con = null;
	private PreparedStatement preparedStatement=null;

	private int bakiye,elektrikfaturasi,sufaturasi,dogalgazfaturasi,kredikartiborcu;
	private String iban,tckimlikno,eposta,telefonnumarasi;
	// id1 değişkeni kullanicinin veritabanındaki id sidir. 
	
	
	
	
	
	public Bilgilerigoster(int id1) {
		String url="jdbc:mysql://" + Database1.host  + ":" + Database1.port + "/" + Database1.db_ismi+"?useUnicode=true&characterEncoding=utf8";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException ex) {
		System.out.println("Driver Bulunamadı");
		}
		
		try {
			con=DriverManager.getConnection(url,Database1.kullanici_adi,Database1.parola);
			System.out.println("Bağlantı Başarılı");
		} catch (SQLException ex) {
			System.out.println("Başarısızdır bağlantı");
		}
		
		
		
		
		
		
		this.id1 = id1;
	}






	public void bg1() {
String sorgu="Select tckimlikno,eposta,telefonnumarasi,bakiye,elektrikfaturasi,sufaturasi,dogalgazfaturasi,kredikartıborcu,iban from kullanicibilgi INNER JOIN bilgiler ON kullanicibilgi.id=bilgiler.id where kullanicibilgi.id=?";
		
		try {
			preparedStatement=con.prepareStatement(sorgu);
			preparedStatement.setInt(1, id1);
		
			ResultSet rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
			 this.tckimlikno=rs.getString("tckimlikno");
			 this.eposta=rs.getString("eposta");
			 this.telefonnumarasi=rs.getString("telefonnumarasi");
			 this.iban=rs.getString("iban");
			 this.bakiye=rs.getInt("bakiye");
			 this.elektrikfaturasi=rs.getInt("elektrikfaturasi");
			 this.sufaturasi=rs.getInt("sufaturasi");
			 this.dogalgazfaturasi=rs.getInt("dogalgazfaturasi");
			 this.kredikartiborcu=rs.getInt("kredikartıborcu");
			
			 
			}
			System.out.print("TC Kimlik No:"+tckimlikno+"\nE posta:"+eposta+"\nTelefon Numarası:"+telefonnumarasi+"\nİban:"+iban);
			System.out.println("\nBakiyeniz:"+bakiye+"\nElektrik faturası borcu:"+elektrikfaturasi+"\nSu faturası borcu:"+sufaturasi+"\nDogal gaz faturası borcu:"+dogalgazfaturasi+"\nKredi Kartı borcu:"+kredikartiborcu);
		
			
		} catch (SQLException e) {


			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	
}
