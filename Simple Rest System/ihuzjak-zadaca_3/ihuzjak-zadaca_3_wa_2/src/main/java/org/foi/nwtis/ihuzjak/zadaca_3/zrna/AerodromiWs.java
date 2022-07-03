package org.foi.nwtis.ihuzjak.zadaca_3.zrna;

import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.ihuzjak.ws.aerodromi.Aerodrom;
import org.foi.nwtis.ihuzjak.ws.aerodromi.Aerodromi;
import org.foi.nwtis.ihuzjak.ws.aerodromi.Lokacija;
import org.foi.nwtis.ihuzjak.ws.aerodromi.WsAerodromi;
import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.Konfiguracija;
import org.foi.nwtis.rest.klijenti.LIQKlijent;
import org.foi.nwtis.rest.klijenti.NwtisRestIznimka;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import jakarta.xml.ws.WebServiceRef;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named("AerodromiWs")
public class AerodromiWs {

	@WebServiceRef(wsdlLocation = "http://localhost:9090/ihuzjak-zadaca_3_wa_1/aerodromi?wsdl")
	private Aerodromi service;
	
	@Inject
	ServletContext context;
	
	@Setter
	private List<Aerodrom> aerodromi = new ArrayList<>();
	@Setter
	private List<Aerodrom> praceni = new ArrayList<>();
	@Setter
	private String icao;
	//@Setter
	//private String icaoLokacija = "Zagreb";
	@Setter @Getter
	//private boolean gledajPracene = false;
	
	Lokacija l;
	
	public List<Aerodrom> getAerodromi() {
		
		this.aerodromi = this.dajSveAerodrome();
		return this.aerodromi;
	}

	public List<Aerodrom> dajSveAerodrome() {
		
		service = new Aerodromi();
		WsAerodromi wsAerodromi = service.getWsAerodromiPort();
		List<Aerodrom> lAerodromi = wsAerodromi.dajSveAerodrome();
		return lAerodromi;
	}
	
	public List<Aerodrom> getPraceni() {
		
		this.aerodromi = this.dajSvePracene();
		return this.aerodromi;
	}


	public List<Aerodrom> dajSvePracene() {
		
		service = new Aerodromi();
		WsAerodromi wsAerodromi = service.getWsAerodromiPort();
		List<Aerodrom> lAerodromi = wsAerodromi.dajAerodromeZaPratiti();
		return lAerodromi;
	}
	
	public String getIcao() {
		
		this.icao = this.dodajAerodrom();
		return this.icao;
	}
	
	public String dodajAerodrom() {
		
		service = new Aerodromi();
		WsAerodromi wsAerodromi = service.getWsAerodromiPort();
		wsAerodromi.dodajAerodromPreuzimanje(this.icao);
		return this.icao;
	}
	/*
	public String getIcaoLokacija() throws NwtisRestIznimka {
		
		this.icaoLokacija = this.dajLokaciju();
		return this.icaoLokacija;
	}
	
	public String dajLokaciju() throws NwtisRestIznimka {
		
		Konfiguracija konf = (Konfiguracija) context.getAttribute("Postavke");
		LIQKlijent lklijent = new LIQKlijent(konf.dajPostavku("LocationIQ.token"));
		org.foi.nwtis.rest.podaci.Lokacija lokacija = lklijent.getGeoLocation(this.icaoLokacija);
		
		service = new Aerodromi();
		WsAerodromi wsAerodromi = service.getWsAerodromiPort();
		
		l = new Lokacija();
		l.setLatitude(lokacija.getLatitude());
		l.setLongitude(lokacija.getLongitude());
		
		List<Aerodrom> lAerodromi = wsAerodromi.dajAerodromeZaPratiti();
		Aerodrom aerodrom = new Aerodrom();
		for (Aerodrom a : lAerodromi) {
			if (a.getIcao().compareTo(icaoLokacija) == 0) {
				aerodrom = a;
				break;
			}
		}
		
		l = aerodrom.getLokacija();
		wsAerodromi.dajNajbliziAerodrom(l, gledajPracene);
		return this.icaoLokacija;
	}*/
}
