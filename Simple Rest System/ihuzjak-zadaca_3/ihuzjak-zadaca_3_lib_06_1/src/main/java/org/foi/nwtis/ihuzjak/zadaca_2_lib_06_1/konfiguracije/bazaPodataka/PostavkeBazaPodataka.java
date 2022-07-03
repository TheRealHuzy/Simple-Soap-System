package org.foi.nwtis.ihuzjak.zadaca_2_lib_06_1.konfiguracije.bazaPodataka;

import java.util.Properties;

import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.NeispravnaKonfiguracija;



public class PostavkeBazaPodataka extends KonfiguracijaApstraktna implements KonfiguracijaBP {

	public PostavkeBazaPodataka(String nazivDatoteke) {
		super(nazivDatoteke);
	}

	public String getAdminDatabase() {
		return this.dajPostavku("admin.database");
	}

	public String getAdminPassword() {
		return this.dajPostavku("admin.password");
	}

	public String getAdminUsername() {
		return this.dajPostavku("admin.username");
	}

	public String getDriverDatabase() {
		return this.getDriverDatabase(this.getServerDatabase());
	}

	public String getDriverDatabase(String urlBazePodataka) {
		String protokol = null;
		String s[] = urlBazePodataka.split("//");
		protokol = s[0].substring(0, s[0].length() - 1);
		protokol = protokol.replace(":", ".");
		String driver = this.dajPostavku(protokol);
		return driver;
	}

	public Properties getDriversDatabase() {
		Properties odgovor = new Properties();
		Properties props = this.dajSvePostavke();
		for(Object o: props.keySet()) {
			String k = (String) o;
			if(k.startsWith("jdbc.")) {
				String v = this.dajPostavku(k);
				odgovor.setProperty(k, v);
			}
		}
		
		return odgovor;
	}

	public String getServerDatabase() {
		return this.dajPostavku("server.database");
	}

	public String getUserDatabase() {
		return this.dajPostavku("user.database");
	}

	public String getUserPassword() {
		return this.dajPostavku("user.password");
	}

	public String getUserUsername() {
		return this.dajPostavku("user.username");
	}

	public void ucitajKonfiguraciju(String nazivDatoteke) throws NeispravnaKonfiguracija {
		Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
		this.postavke = konfig.dajSvePostavke();
	}

	public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
		Konfiguracija konfig = KonfiguracijaApstraktna.dajKonfiguraciju(datoteka);
		Properties props = this.dajSvePostavke();
		for(Object o: props.keySet()) {
			String k = (String) o;
			String v = this.dajPostavku(k);
			konfig.spremiPostavku(k, v);
		}
		konfig.spremiKonfiguraciju();
	}
}