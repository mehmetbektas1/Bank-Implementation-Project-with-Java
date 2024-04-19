import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class İslemler implements Database1 {
	private Connection con = null;
  
    private PreparedStatement preparedStatement=null;
    private PreparedStatement preparedStatement1=null;
    private PreparedStatement preparedStatement2=null;
    private PreparedStatement preparedStatement3=null;
    private PreparedStatement preparedStatement4=null;
    private PreparedStatement preparedStatement5=null;
    private String ibankontrol;
    private boolean kontrol134;
    private boolean kontrol214;
    private String sifre;
    private String tckimlikno;
    public int id1;
    private int gidenpara;
    private int bakiye1;
    private int bakiye;
    private int fatura;
    private int bakiye2;
private boolean kontrol;

	

public İslemler() {
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

	
	
	
	
	
	
}

public boolean girisyap() {
	
	System.out.println("Lütfen tckimlikno giriniz");
	Scanner scan=new Scanner(System.in);
	this.tckimlikno=scan.nextLine();
	System.out.println("Lütfen sifre giriniz:");
	this.sifre=scan.nextLine();
	String sorgu1="Select * from kullanicibilgi where tckimlikno=? and sifre=?";
	
	try {
		preparedStatement=con.prepareStatement(sorgu1);
		preparedStatement.setString(1, this.tckimlikno);
		preparedStatement.setString(2, this.sifre);
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			this.id1=rs.getInt("id");
		}	
	} catch (SQLException e) {
		System.out.println(this.id1);
		e.printStackTrace();
	}
	
	String sorgu="Select * from kullanicibilgi where tckimlikno=? and sifre=?";
	try {
		preparedStatement1=con.prepareStatement(sorgu);
	
		preparedStatement1.setString(1, this.tckimlikno);
		preparedStatement1.setString(2, this.sifre);
	
		ResultSet rs=preparedStatement1.executeQuery();
		
		if(rs.next()==true) {
			this.kontrol=true;
		}
		else {
			System.out.println("Sifre veya tckimlikno yanlis lütfen tekrar giriniz");
			 this.kontrol=false;
		}
		
		return kontrol;
		
	} catch (SQLException e) {
		
		e.printStackTrace();
		return false;
	}
}

	
public void paragonder() {
	Scanner scan1=new Scanner(System.in);
	System.out.print("Lütfen gondereceginiz kişinin ibanini giriniz:");
	String iban1=scan1.nextLine();
	System.out.print("Lütfen gondereceginiz parayi giriniz:");
this.gidenpara=scan1.nextInt();
	

// alttaki kodlar ibanin varligini kontrol eder.
String sorgu5="Select * from bilgiler where iban=?";
try {
	preparedStatement4=con.prepareStatement(sorgu5);
	preparedStatement4.setString(1, iban1);
	ResultSet rs=preparedStatement4.executeQuery();
	
	if(rs.next()) {
		this.kontrol214=true;
	}
	else {
		this.kontrol214=false;
	}
} catch (SQLException e) {
	
	e.printStackTrace();
}
// alttaki kodlar girilen ibanin kendi ibanina esit olup olmadıgını kontrol eder
String sorgu6="Select * from bilgiler where id=?";	
try {
	preparedStatement5=con.prepareStatement(sorgu6);
	preparedStatement5.setInt(1, id1);
	ResultSet rs=preparedStatement5.executeQuery();
	while(rs.next()) {
		this.ibankontrol=rs.getString("iban");
	}
} catch (SQLException e) {
	
	e.printStackTrace();
}
	
	
	if(this.kontrol214   &&  !(ibankontrol.equals(iban1))) {
	String sorgu1="Select * from bilgiler where iban=?";
	try {
		preparedStatement=con.prepareStatement(sorgu1);
		preparedStatement.setString(1, iban1);
		ResultSet rs=preparedStatement.executeQuery();
		//bakiye1 alicinin sahip oldugu para miktaridir
		while(rs.next()) {
		 this.bakiye1=rs.getInt("bakiye");
		 this.bakiye1 = this.bakiye1+this.gidenpara;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	String sorgu="Select * from bilgiler where id=?";
	try {
		preparedStatement1=con.prepareStatement(sorgu);
		preparedStatement1.setInt(1,id1);
		//bakiye göndericinin sahip oldugu para miktaridir. gidenpara gönderilen para miktaridir
		ResultSet rs=preparedStatement1.executeQuery();
		while(rs.next()) {
			this.bakiye=rs.getInt("bakiye");
			
		}
		this.bakiye=this.bakiye-this.gidenpara;
		System.out.println("Yeni bakiyeniz:"+this.bakiye);
		if(bakiye<0 || gidenpara<0) {
			System.out.println("YETERLİ PARANIZ OLMADIĞI İÇİN İŞLEM YAPILAMAMIŞTIR.");
			 kontrol134=false;
		}
		else {
			kontrol134=true;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(kontrol134) {
	String sorgu3="Update bilgiler Set bakiye=? WHERE id=?";
	try {
		preparedStatement2=con.prepareStatement(sorgu3);
		
		preparedStatement2.setInt(1,bakiye);
		preparedStatement2.setInt(2,id1);
		preparedStatement2.executeUpdate();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String sorgu4="Update bilgiler Set bakiye=? WHERE iban=?";
	try {
		preparedStatement3=con.prepareStatement(sorgu4);
		
		preparedStatement3.setInt(1,this.bakiye1);
		preparedStatement3.setString(2,iban1);
		preparedStatement3.executeUpdate();
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	}
	}	
	else if(iban1.equals(ibankontrol)) {
		System.out.println("Kendi iban adresinizi yazamazsınız");
	}
	else {
		System.out.println("Bilinmeyen iban adresi");
	}
}






// elektrik faturasi ödeme;
public void eef() {
	String sorgu="Select * from bilgiler where id=?";
	try {
		preparedStatement=con.prepareStatement(sorgu);
		preparedStatement.setInt(1,id1);
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			this.fatura=rs.getInt("elektrikfaturasi");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	if(fatura==0) {
		System.out.println("Fatura borcunuz bulunmamaktadır");
	}
	else {
		try {
			preparedStatement1=con.prepareStatement(sorgu);
			preparedStatement1.setInt(1,id1);
			ResultSet rs=preparedStatement1.executeQuery();
			while(rs.next()) {
				this.bakiye2=rs.getInt("bakiye");
			}
			if(fatura>bakiye2) {
				System.out.println("Faturanızı odeyecek kadar paraniz bulunmamaktadır");
			}
			else {
				System.out.println("Faturanız ödeniyor.... Faturaniz:"+fatura);
				bakiye2=bakiye2-fatura;
				String sorgu2="Update bilgiler SET elektrikfaturasi=0, bakiye=? WHERE id=?";
				preparedStatement2=con.prepareStatement(sorgu2);
				
				preparedStatement2.setInt(1,bakiye2);
				preparedStatement2.setInt(2,id1);
				preparedStatement2.executeUpdate();
				System.out.println("Faturanız ödendi. Güncel bakiyeniz:"+bakiye2);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
	// su faturasi ödeme. fatura değişkeni kullanicinin fatura borcu miktaridir.
public void sfo() {
	String sorgu="Select * from bilgiler where id=?";
	try {
		preparedStatement=con.prepareStatement(sorgu);
		preparedStatement.setInt(1,id1);
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			this.fatura=rs.getInt("sufaturasi");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	if(fatura==0) {
		System.out.println("Fatura borcunuz bulunmamaktadır");
	}
	else {
		try {
			preparedStatement1=con.prepareStatement(sorgu);
			preparedStatement1.setInt(1,id1);
			ResultSet rs=preparedStatement1.executeQuery();
			while(rs.next()) {
				this.bakiye2=rs.getInt("bakiye");
			}
			if(fatura>bakiye2) {
				System.out.println("Faturanızı odeyecek kadar paraniz bulunmamaktadır");
			}
			else {
				System.out.println("Faturanız ödeniyor.... Faturaniz:"+fatura);
				bakiye2=bakiye2-fatura;
				String sorgu2="Update bilgiler SET sufaturasi=0, bakiye=? WHERE id=?";
				preparedStatement2=con.prepareStatement(sorgu2);
				
				preparedStatement2.setInt(1,bakiye2);
				preparedStatement2.setInt(2,id1);
				preparedStatement2.executeUpdate();
				System.out.println("Faturanız ödendi. Güncel bakiyeniz:"+bakiye2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
//dogalgaz faturasi ödeme fonksiyonudur. fatura değişkeni kullanicinin fatura borcu miktaridir.
public void dfo() {
	String sorgu="Select * from bilgiler where id=?";
	try {
		preparedStatement=con.prepareStatement(sorgu);
		preparedStatement.setInt(1,id1);
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			this.fatura=rs.getInt("dogalgazfaturasi");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	if(fatura==0) {
		System.out.println("Fatura borcunuz bulunmamaktadır");
	}
	else {
		try {
			preparedStatement1=con.prepareStatement(sorgu);
			preparedStatement1.setInt(1,id1);
			ResultSet rs=preparedStatement1.executeQuery();
			while(rs.next()) {
				this.bakiye2=rs.getInt("bakiye");
			}
			if(fatura>bakiye2) {
				System.out.println("Faturanızı odeyecek kadar paraniz bulunmamaktadır");
			}
			else {
				System.out.println("Faturanız ödeniyor.... Faturaniz:"+fatura);
				bakiye2=bakiye2-fatura;
				String sorgu2="Update bilgiler SET dogalgazfaturasi=0, bakiye=? WHERE id=?";
				preparedStatement2=con.prepareStatement(sorgu2);
				
				preparedStatement2.setInt(1,bakiye2);
				preparedStatement2.setInt(2,id1);
				preparedStatement2.executeUpdate();
				System.out.println("Faturanız ödendi. Güncel bakiyeniz:"+bakiye2);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}





public String getSifre() {
	return sifre;
}

public void setSifre(String sifre) {
	this.sifre = sifre;
}

public String getTckimlikno() {
	return tckimlikno;
}

public void setTckimlikno(String tckimlikno) {
	this.tckimlikno = tckimlikno;
}


// Kullanicinin islemini yaptıkttan sonra devam edip etmeyeceğini belirler.

public boolean devametme1() {
	Scanner scan=new Scanner(System.in);

	while(true) {
		System.out.print("Lütfen geçerli bir işlem girin. Tekrar işlem yapmak istiyormusunuz ? Lütfen Evet veya Hayir giriniz:");
		String secim1=scan.nextLine();
	if(secim1.equals("Evet")) {
		return true;
		
	}
	else if(secim1.equals("Hayir")) {
		System.out.print("Sistemden çıkılıyor....");
		return false;
		
	}
	else {
		System.out.print("Bilinmeyen işlem lütfen tekrar giriniz");
	}
}
}





}






	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

