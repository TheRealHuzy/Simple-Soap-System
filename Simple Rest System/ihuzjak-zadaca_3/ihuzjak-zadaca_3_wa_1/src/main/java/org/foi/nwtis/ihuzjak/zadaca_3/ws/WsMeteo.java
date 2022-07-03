package org.foi.nwtis.ihuzjak.zadaca_3.ws;

import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.zadaca_3.podaci.AirportDAO;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Airport;
import org.foi.nwtis.rest.klijenti.OWMKlijent;
import org.foi.nwtis.rest.podaci.Lokacija;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.servlet.ServletContext;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

@WebService(serviceName = "meteo")
public class WsMeteo {
	
	@Resource
	private WebServiceContext wsContext;
	
	@WebMethod
	public MeteoPodaci dajMeteo(String icao) {
		
		Konfiguracija konf = dajKonfig();
		AirportDAO aDAO = new AirportDAO(konf);
		List<Airport> airporti = aDAO.dajSveAerodrome();
		List<Aerodrom> aerodromi = pretvoriAirportUAerodrom(airporti);
		
		Aerodrom aerodrom = new Aerodrom();
		for(Aerodrom a : aerodromi) {
			if(a.getIcao().equals(icao)) {
				aerodrom = a;
				break;
			}
		}
		return izvrsiMeteo(konf, aerodrom);
	}
	
	private MeteoPodaci izvrsiMeteo(Konfiguracija konf, Aerodrom a) {
		
		OWMKlijent owmKlijent = new OWMKlijent(konf.dajPostavku("OpenWeatherMap.apikey"));
		MeteoPodaci meteo = new MeteoPodaci();
		
		try {
			meteo = owmKlijent.getRealTimeWeather(a.getLokacija().getLatitude(), a.getLokacija().getLongitude());
		} catch (Exception e) {
			System.out.println("Greška u razgovoru sa OWM klijentom");
		}
		return meteo;
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
}
