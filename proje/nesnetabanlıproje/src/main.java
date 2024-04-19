
import java.util.Scanner;
public class main {

	public static void main(String[] args) {
		int id;
		boolean devametme=true;
		System.out.println("Banka uygulamasına hoşgeldiniz. Lütfen sisteme giriş yapınız. Eğer kayıtlı değilseniz lütfen en yakın şubede kayıt yaptırınız");
		int secim;
		İslemler islem1=new İslemler();
		int hataligiris=0;
	// Aşağıdaki while da girisyap fonksiyonu çalısmaktadır. eger kullanici 3 kez hata yapmissa sifre yenilemesi gerekir	
	while(true) {
		
		if(islem1.girisyap()) {
			 id=islem1.id1;
			break;
		}
		else if(hataligiris>=3) {
		System.out.println("Sifrenizi yenilemek istiyormusunuz");
	
		boolean kontrol9=islem1.devametme1();
		if(kontrol9) {
			Ayarlar ayar5=new Ayarlar(10);
			ayar5.sifredegisterme();
			
		}
		}
		
		hataligiris++;
	
		
		
		
		
		
	}
	while(devametme) {
	System.out.print("Sisteme hosgeldiniz lütfen menü seçiniz \n 1-Para Transferi \n 2-Kredi karti islemleri \n 3-Ayarlar\n 4-Bilgilerimi göster \n 5-Uygulamadan çık \n İşlem Seçiniz:");	
		Scanner scan=new Scanner(System.in);
		int islem=scan.nextInt();
	switch(islem){
	case 1:
		System.out.print("Lütfen paratransferi islemi seciniz.\n1-Para gönderme \n2-Faturaodeme \n3-Geri dön\nİşlem Seçiniz:");
		secim=scan.nextInt();
		if(secim==1) {
		islem1.paragonder();
		devametme=islem1.devametme1();
		}
		else if(secim==2) {
			System.out.print("Lütfen fatura ödeme seçeneği seçiniz \n1-Elektrik faturası ödeme \n2-Su faturası ödeme \n3-Dogalgaz faturası ödeme \nİşlemSeçiniz:");
			secim=scan.nextInt();
			if(secim==1) {
			islem1.eef();
			devametme=islem1.devametme1();
			
			}
			else if(secim==2) {
				islem1.sfo();
				devametme=islem1.devametme1();
			}
			else if(secim==3) {
				islem1.dfo();
				devametme=islem1.devametme1();
			}
			else {
				devametme=islem1.devametme1();
			}
		}
		else if(secim==3) {
			System.out.print("Geri dönülmüştür ");
		}
		else {
			System.out.print("Lütfen doğru bir seçim yapınız. ");
		}
		
		break;
	case 2:
		System.out.print("Lütfen kredi kartı işlemi seçin \n1-Kredi kartı borcunu öde \n2-Kredi kartımı iptal et \n3-nakit al\n4-Geri dön\nİşlem Seçiniz:");
		secim=scan.nextInt();
		Kredikartiislemleri kki=new Kredikartiislemleri(id);
		if(secim==1) {
	       kki.borccode();
	       devametme=islem1.devametme1();
			
		}
		else if(secim==2) {
			kki.kredikartikapat();
			devametme=islem1.devametme1();
		}
		else if(secim==3) {
			kki.borcal();
			devametme=islem1.devametme1();
		}
		else if(secim==4) {
			System.out.print("Geri dönülmüştür ");
			
		}
		else {
			System.out.println("Seçenek bulunamamıştır.Otomatik olarak geri dönülmüştür.");
		}
		
		break;
	case 3:
		System.out.print("Lütfen Ayarlar seçin \n1-Şifremi değiştir \n2-Geri dön \nİşlem seçiniz:");
		secim=scan.nextInt();
	    Ayarlar ayar=new Ayarlar(id);
		if(secim==1) {
			ayar.sifredegisterme();
			devametme=islem1.devametme1();
		}
		else if(secim==2) {
			
		}
		else {
			System.out.println("Seçenek bulunamamıştır. Geri dönülmüştür...");
		}
		
		break;
	case 4:
		Bilgilerigoster bg=new Bilgilerigoster(id);
		bg.bg1();
		devametme=islem1.devametme1();
		
		break;
		
	case 5:
		System.out.println("Sistemden başarıyla çıkıldı");
		devametme=false;
		
		break;
	default:
		System.out.println("Geçersiz seçenek....");
	    devametme=islem1.devametme1();
		
		
		
		
		
		
		
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
		
		
		
		

	}
	

}
