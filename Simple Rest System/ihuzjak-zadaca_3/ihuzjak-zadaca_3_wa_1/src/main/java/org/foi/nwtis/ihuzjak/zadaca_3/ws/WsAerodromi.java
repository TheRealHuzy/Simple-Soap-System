package org.foi.nwtis.ihuzjak.zadaca_3.ws;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.zadaca_3.podaci.AerodromPracen;
import org.foi.nwtis.ihuzjak.zadaca_3.podaci.AerodromiDolasciDAO;
import org.foi.nwtis.ihuzjak.zadaca_3.podaci.AerodromiPolasciDAO;
import org.foi.nwtis.ihuzjak.zadaca_3.podaci.AerodromiPraceniDAO;
import org.foi.nwtis.ihuzjak.zadaca_3.podaci.AirportDAO;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Airport;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.rest.podaci.Lokacija;

import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.servlet.ServletContext;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

@WebService(serviceName = "aerodromi")
public class WsAerodromi {
	
	@Resource
	private WebServiceContext wsContext;
	
	private static final DecimalFormat df = new DecimalFormat("0");
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	@WebMethod
	public List<Aerodrom> dajSveAerodrome() {
		
		Konfiguracija konf = dajKonfig();
		AirportDAO aDAO = new AirportDAO(konf);
		List<Airport> airporti = aDAO.dajSveAerodrome();
		return pretvoriAirportUAerodrom(airporti);
	}
	
	 private Konfiguracija dajKonfig() {
		 
		 ServletContext context = (ServletContext) wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
		 Konfiguracija konf = (Konfiguracija) context.getAttribute("Postavke");
		 return konf;
	 }
	
	private List<Aerodrom> pretvoriAirportUAerodrom(List<Airport> airporti){
		
		List<Aerodrom> aerodromi = new ArrayList<>();
		for (Airport a : airporti) {
			Aerodrom aerodrom = new Aerodrom();
			aerodrom.setNaziv(a.getName());
			aerodrom.setIcao(a.getIdent());
			aerodrom.setDrzava(a.getIso_country());
			aerodrom.setLokacija(dajLokaciju(a));
			aerodromi.add(aerodrom);
		}
		return aerodromi;
	}
	
	private Lokacija dajLokaciju(Airport a) {
		
		Lokacija l = new Lokacija();
		String[] s = a.getCoordinates().split(",");
		l.setLongitude(s[0].trim());
		l.setLatitude(s[1].trim());
		return l;
	}
	
	@WebMethod
	public List<Aerodrom> dajAerodromeZaPratiti() {
		
		Konfiguracija konf = dajKonfig();
		AerodromiPraceniDAO apDAO = new AerodromiPraceniDAO(konf);
		List<AerodromPracen> apraceni = apDAO.dajSvePraceneAerodrome();
		List<Airport> airporti = dajPodatkeZaPracene(apraceni, konf);
		return pretvoriAirportUAerodrom(airporti);
	}
	
	private List<Airport> dajPodatkeZaPracene(List<AerodromPracen> apraceni, Konfiguracija konf){
		
		AirportDAO aDAO = new AirportDAO(konf);
		List<Airport> airporti = new ArrayList<>();
		for (AerodromPracen ap : apraceni) {
			Airport a = aDAO.dajAerodrom(ap.getIdent()).get(0);
			airporti.add(a);
		}
		return airporti;
	}
	
	@WebMethod
	public void dodajAerodromPreuzimanje(String icao) {
		
		Konfiguracija konf = dajKonfig();
		AerodromiPraceniDAO apDAO = new AerodromiPraceniDAO(konf);
		apDAO.posaljiAerodrom(icao);
	}
	
	@WebMethod
	public Aerodrom dajNajbliziAerodrom(Lokacija l, boolean gledajPratece) {
		if (!gledajPratece) return najbliziAerodrom(l);
		return najbliziPraceniAerodrom(l);
	}
	
	private Aerodrom najbliziAerodrom(Lokacija l) {
		
		Konfiguracija konf = dajKonfig();
		AirportDAO aDAO = new AirportDAO(konf);
		List<Airport> airporti = aDAO.dajSveAerodrome();
		List<Aerodrom> aerodromi = pretvoriAirportUAerodrom(airporti);
		
		Aerodrom lokacijaAerodrom = stvoriAerodromOdLokacije(l);
		Aerodrom najbliziAerodrom = new Aerodrom();
		double udaljenost = Double.POSITIVE_INFINITY;
		
		for (Aerodrom a : aerodromi) {
			String izracun = izracunajUdaljenost(lokacijaAerodrom, a);
			double dIzracun = Double.parseDouble(izracun);
			if (dIzracun < udaljenost) {
				najbliziAerodrom = a;
				udaljenost = dIzracun;
			}
		}
		return najbliziAerodrom;
	}
	
	private Aerodrom stvoriAerodromOdLokacije(Lokacija l) {
		Aerodrom a = new Aerodrom();
		a.setLokacija(l);
		return a;
	}

	private String izracunajUdaljenost(Aerodrom a1, Aerodrom a2) {
		
		double a1GD = Double.parseDouble(a1.getLokacija().getLongitude());
		double a1GS = Double.parseDouble(a1.getLokacija().getLatitude());
		double a2GD = Double.parseDouble(a2.getLokacija().getLongitude());
		double a2GS = Double.parseDouble(a2.getLokacija().getLatitude());
		
		double udaljenostD = Math.toRadians(a2GD - a1GD);
		double udaljenostS = Math.toRadians(a2GS - a1GS);
		
		double izracun = Math.pow(Math.sin(udaljenostS / 2), 2)
				+ Math.cos(Math.toRadians(a1GS)) * Math.cos(Math.toRadians(a2GS))
				* Math.pow(Math.sin(udaljenostD / 2), 2);
		
		double udaljenost = (2 * Math.atan2(Math.sqrt(izracun), Math.sqrt(1 - izracun))) * 6371;
		return df.format(udaljenost);
	}

	private Aerodrom najbliziPraceniAerodrom(Lokacija l) {
		
		Konfiguracija konf = dajKonfig();
		AerodromiPraceniDAO apDAO = new AerodromiPraceniDAO(konf);
		List<AerodromPracen> apraceni = apDAO.dajSvePraceneAerodrome();
		List<Airport> airporti = dajPodatkeZaPracene(apraceni, konf);
		List<Aerodrom> aerodromi = pretvoriAirportUAerodrom(airporti);
		
		Aerodrom lokacijaAerodrom = stvoriAerodromOdLokacije(l);
		Aerodrom najbliziPraceniAerodrom = new Aerodrom();
		double udaljenost = Double.POSITIVE_INFINITY;
		
		for (Aerodrom a : aerodromi) {
			String izracun = izracunajUdaljenost(lokacijaAerodrom, a);
			double dIzracun = Double.parseDouble(izracun);
			if (dIzracun < udaljenost) {
				najbliziPraceniAerodrom = a;
				udaljenost = dIzracun;
			}
		}
		return najbliziPraceniAerodrom;
	}
	
	@WebMethod
	public List<AvionLeti> dajDolaskeAerodoma(String icao, String datum) {
		
		Konfiguracija konf = dajKonfig();
		AerodromiDolasciDAO adDAO = new AerodromiDolasciDAO(konf);
		Date date = new Date();
		
		try {
			date = sdf.parse(datum);
		} catch (ParseException e) {
			System.out.println("Greška kod parsiranja datuma");
		}
		
		return adDAO.dajAerodromeDolaske(icao, date);
	}
	
	@WebMethod
	public List<AvionLeti> dajPolaskeAerodoma(String icao, String datum) {
		
		Konfiguracija konf = dajKonfig();
		AerodromiPolasciDAO adDAO = new AerodromiPolasciDAO(konf);
		Date date = new Date();
		
		try {
			date = sdf.parse(datum);
		} catch (ParseException e) {
			System.out.println("Greška kod parsiranja datuma");
		}
		
		return adDAO.dajAerodromePolaske(icao, date);
	}
}
