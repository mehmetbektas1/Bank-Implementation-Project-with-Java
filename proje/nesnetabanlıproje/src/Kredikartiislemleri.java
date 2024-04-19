import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Kredikartiislemleri implements Database1 {
private int bakiye;
private int bakiye12;

private int id1;
private Connection con = null;

private PreparedStatement preparedStatement=null;
private PreparedStatement preparedStatement1=null;
private PreparedStatement preparedStatement2=null;
private PreparedStatement preparedStatement3=null;

private int kredikartiborcu;
private int odenenpara;
private int bakiyeborcu,alinanborc;
private boolean kontrol1345;
public Kredikartiislemleri(int id1) {
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
	public void borccode() {
		Scanner scan1=new Scanner(System.in);
		System.out.print("Lütfen borcunu odeyeceginiz parayi giriniz:");
		this.odenenpara=scan1.nextInt();
		// Alttaki sorgu ile kullanicinin bakiyesi alinir.
		String sorgu1="Select * from bilgiler where id=?";
		try {
			preparedStatement=con.prepareStatement(sorgu1);
			preparedStatement.setInt(1, id1);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()) {
			 this.bakiye12=rs.getInt("bakiye");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Alttaki kodlar ile bakiyeden borc ödenir ve kredi karti borcu azalır. Eger bakiye negatifse veritabanına güncelleme yapılmaz.
		String sorgu="Select * from bilgiler where id=?";
		try {
			preparedStatement1=con.prepareStatement(sorgu);
			preparedStatement1.setInt(1,id1);
			ResultSet rs=preparedStatement1.executeQuery();
			while(rs.next()) {
				this.bakiyeborcu=rs.getInt("kredikartıborcu");
				
			}
			this.bakiye12=this.bakiye12-this.odenenpara;
			this.bakiyeborcu=this.bakiyeborcu-this.odenenpara;
			System.out.println("Yeni bakiyeniz:"+this.bakiye12);
			if(this.bakiye12<0 || bakiyeborcu<0) {
				System.out.println("YETERLİ PARANIZ OLMADIĞI İÇİN VEYA GİRDİĞİNİZ PARA BORCUNUZDAN FAZLA OLDUGU İÇİN İŞLEM YAPILAMAMIŞTIR.");
				 kontrol1345=false;
			}
			else {
				kontrol1345=true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(kontrol1345) {
		String sorgu3="Update bilgiler Set bakiye=? WHERE id=?";
		try {
			preparedStatement2=con.prepareStatement(sorgu3);
			
			preparedStatement2.setInt(1,bakiye12);
			preparedStatement2.setInt(2,id1);
			preparedStatement2.executeUpdate();
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sorgu4="Update bilgiler Set kredikartıborcu=? WHERE id=?";
		try {
			preparedStatement3=con.prepareStatement(sorgu4);
			
			preparedStatement3.setInt(1,bakiyeborcu);
			preparedStatement3.setInt(2,id1);
			
			preparedStatement3.executeUpdate();
			System.out.println("İslem basariyla gerceklestirildi Odediginiz para:"+odenenpara+"\nBakiyeniz:"+bakiye12+"\nKredi karti borcunuz:"+bakiyeborcu);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		}
		
		
		
		
		
		
	}
	
public void borcal() {
	System.out.println("Alacagınız miktar 10000'i aşmamalıdır ve borcunuz olmamalıdır");
	// 
	String sorgu1="Select * from bilgiler where id=?";
	try {
		preparedStatement=con.prepareStatement(sorgu1);
preparedStatement.setInt(1, this.id1);
		
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			this.kredikartiborcu=rs.getInt("kredikartıborcu");
			this.bakiye=rs.getInt("bakiye");
		}

	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	if(kredikartiborcu==0) {
		Scanner scan1=new Scanner(System.in);
		
		System.out.print("Lütfen alacaginiz parayi giriniz:");
		this.alinanborc=scan1.nextInt();
		if(alinanborc>10000||alinanborc<=0) {
			System.out.print("Lütfen alacaginiz parayi doğru giriniz. :");
		}
		else {
			
		this.kredikartiborcu=this.kredikartiborcu+alinanborc;
		String sorgu3="Update bilgiler Set kredikartıborcu=? WHERE id=?";
		try {
			preparedStatement2=con.prepareStatement(sorgu3);
			
			preparedStatement2.setInt(1,this.kredikartiborcu);
			preparedStatement2.setInt(2,id1);
			preparedStatement2.executeUpdate();
			System.out.println("İşlem başarıyla gerçekleştirildi. Yeni kredi kartı borcunuz:"+this.kredikartiborcu);
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("İşlem gerçekleştirilemedi");
		}
		bakiye=bakiye+this.alinanborc;
		String sorgu4="Update bilgiler Set bakiye=? WHERE id=?";	
		try {
			preparedStatement3=con.prepareStatement(sorgu4);
			
			preparedStatement3.setInt(1,this.bakiye);
			preparedStatement3.setInt(2,id1);
			preparedStatement3.executeUpdate();
			System.out.println("Yeni bakiyeniz:"+bakiye);
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("İşlem gerçekleştirilemedi");
		}
		
		
			
		}
		
	}
	else {
		System.out.println("Borc almak için kredi kartı borcunuz olmamalı veya borcunuz 10000 tl yi aşmamalidir");
		
	}
	
	
	
}
	
	
	
	
public void kredikartikapat() {
	String sorgu1="Select * from bilgiler where id=?";
	try {
		preparedStatement=con.prepareStatement(sorgu1);
		preparedStatement.setInt(1, this.id1);
		
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			this.kredikartiborcu=rs.getInt("kredikartıborcu");
			
		}
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	if(kredikartiborcu==0) {
		System.out.println("Kredi kartiniz kapatiliyor....");
		String sorgu2="Update bilgiler set kredikartıborcu=null where id=?";
		try {
			preparedStatement1=con.prepareStatement(sorgu2);
			preparedStatement1.setInt(1, this.id1);
			preparedStatement1.executeUpdate();
			System.out.println("Kredi kartiniz kapatilmiştir.");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Kredi kartiniz kapatilamamiştir.");
		}
		
		
	}
	else {
		System.out.println(" Kredi kartınızdaki borç nedeniyle Kredi kartiniz kapatilamamiştir.");
		
	}
	
	
	
}
	

		
	
	
	
	

public int getBakiye() {
	return bakiye;
}
public void setBakiye(int bakiye) {
	this.bakiye = bakiye;
}
	
	
	
	
	
	
	
	
	
	
	
}
