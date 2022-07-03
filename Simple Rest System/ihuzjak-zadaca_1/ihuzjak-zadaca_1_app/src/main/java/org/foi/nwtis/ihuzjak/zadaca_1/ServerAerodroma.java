package org.foi.nwtis.ihuzjak.zadaca_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.NeispravnaKonfiguracija;

import lombok.NonNull;

public class ServerAerodroma {

	int port;
	int maksCekaca;
	String SUdaljenostiAdresa;
	int SUdaljenostiPort;
	
	Socket veza = null;
	List<Aerodrom> aerodromi = new ArrayList<>();
	
	String airport = "^AIRPORT$";
	String airportIcao = "^AIRPORT ([A-Z]{4})$";
	String airportIcaoBroj = "^AIRPORT [A-Z]{4} [+-]?[0-9]+$";
	
	static public Konfiguracija konfig = null;

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("ERROR 20 >> Broj argumenata nije 1!");
			return;
		}
		ucitavanjePodataka(args[0]);
		if(konfig == null) {
			System.out.println("ERROR 29 >> Problem s konfiguracijom.");
			return;
		}		
		
		int port = Integer.parseInt(konfig.dajPostavku("port"));
		int maksCekaca = Integer.parseInt(konfig.dajPostavku("maks.cekaca"));
		int sUdaljenostiPort = Integer.parseInt(konfig.dajPostavku("server.udaljenosti.port"));
		String sUdaljenostiAdresa = konfig.dajPostavku("server.udaljenosti.adresa");
				
		ServerAerodroma sm = new ServerAerodroma(port, maksCekaca, sUdaljenostiAdresa, sUdaljenostiPort);
		if(sm.ispitajPort(port)) {
			return;
		}
		
		String NazivDatotekeMeteoPodataka = konfig.dajPostavku("datoteka.aerodroma");
		
		if(!sm.pripremiMeteo(NazivDatotekeMeteoPodataka)){
			return;
		};
		System.out.println("Broj podataka: " + sm.aerodromi.size());
		
		
		sm.obradaZahtjeva();

	}
	
	public ServerAerodroma(int port, int maksCekaca, String sUdaljenostiAdresa, int sUdaljenostiPort) {
		super();
		this.port = port;
		this.maksCekaca = maksCekaca;
		SUdaljenostiAdresa = sUdaljenostiAdresa;
		SUdaljenostiPort = sUdaljenostiPort;
	}
	
	private static void ucitavanjePodataka(String nazivDatoteke) {
		try {
			konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
		} catch (NeispravnaKonfiguracija e) {
			System.out.println("ERROR 29 >> Greška pri učitavanju datoteke!");
		}
	}
	
	private boolean ispitajPort(int port) {
		try {
			ServerSocket s = new ServerSocket(port);
			s.close();
		} catch (IOException e) {
			System.out.println("ERROR 29 >> Trazeni socket je vec zauzet!");
			return true;
		}
		return false;
	}

	private boolean pripremiMeteo(String nazivDatotekeMeteoPodataka) {
		try (BufferedReader br = new BufferedReader(new FileReader(nazivDatotekeMeteoPodataka, 
					Charset.forName("UTF-8"))))
		{
			while (true) {
				String linija = br.readLine();
				if (linija == null || linija.isEmpty()) {
					break;
				}
				String[] p = linija.split(";");
				Aerodrom a = null;
				try {
					a = new Aerodrom(p[0], p[1], p[2], p[3]);
					aerodromi.add(a);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					return false;
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("ERROR 29 >> Ne postoji datoteka zadana u konfiguraciji s nazivom: "
					+ nazivDatotekeMeteoPodataka);
			return false;
		}
		return true;
	}

	public void obradaZahtjeva() {

		try (ServerSocket ss = new ServerSocket(this.port, this.maksCekaca))
		{
			while (true) {
				DretvaSA dretvaObrade = new DretvaSA(ss.accept());
				dretvaObrade.start();
			}
		} catch (IOException ex) {
			System.out.println("ERROR 29 >> Port je već zauzet!");
			return;
		}

	}

	private void krivaKomanda(OutputStreamWriter osw, String odgovor) {
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class DretvaSA extends Thread {
		private Socket veza = null;
		
		public DretvaSA(Socket veza) {
			super();
			this.veza = veza;
		}
		
		public void run() {
			try (InputStreamReader isr = new InputStreamReader(this.veza.getInputStream(),
					Charset.forName("UTF-8"));
					OutputStreamWriter osw = new OutputStreamWriter(this.veza.getOutputStream(),
					Charset.forName("UTF-8"));) 
			{
				StringBuilder tekst = new StringBuilder();
				while (true) {
					int i = isr.read();
					if (i == -1) {
						break;
					}
					tekst.append((char) i);
				}
				this.veza.shutdownInput();
				
				//TODO prepoznati komande
				Pattern pAirport = Pattern.compile(airport);
				Pattern pAirportIcao = Pattern.compile(airportIcao);
				Pattern pAirportIcaoBroj = Pattern.compile(airportIcaoBroj);
				
				Matcher mAirport = pAirport.matcher(tekst.toString());
				Matcher mAirportIcao = pAirportIcao.matcher(tekst.toString());
				Matcher mAirportIcaoBroj = pAirportIcaoBroj.matcher(tekst.toString());
				
				if(mAirport.matches()) {
					izvrsiAirport(osw, tekst.toString());
				} else if (mAirportIcao.matches()) {
					izvrsiAirportIcao(osw, tekst.toString());
				} else if (mAirportIcaoBroj.matches()){
					izvrsiAirportIcaoBroj(osw, tekst.toString());
				} else {
					krivaKomanda(osw, "ERROR 20 Sintaksa komande nije uredu!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void izvrsiAirport(OutputStreamWriter osw, String komanda) {
		String odgovor = "OK";
		for(Aerodrom a : aerodromi) {
			odgovor += " " + a.getIcao() + ";";
		}
		if (odgovor == "OK") {
			krivaKomanda(osw, "ERROR 21 Nema traženog aerodroma!");
			return;
		}
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			System.out.println("ERROR 29 Problem sa slanjem odgovora!");
		}
		return;
	}
	
	private void izvrsiAirportIcao(OutputStreamWriter osw, String komanda) {
		String[] p = komanda.split(" ");
		String icao = p[1];
		String odgovor = null;
		
		for(Aerodrom a : aerodromi) {
			if(a.getIcao().compareTo(icao) == 0) {
				if (odgovor == null) {
					odgovor = "OK";
				}
				odgovor += " " + a.getIcao() + " \"" + a.getNaziv() + "\" " + a.getGpsGS() + " " + a.getGpsGD();
			}
		}
		if (odgovor == null) {
			krivaKomanda(osw, "ERROR 21 Nema traženog aerodroma!");
			return;
		}
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			System.out.println("ERROR 29 Problem sa slanjem odgovora!");
		}
		return;
		
	}
	
	private void izvrsiAirportIcaoBroj(OutputStreamWriter osw, String komanda) {
		String[] p = komanda.split(" ");
		String icao = p[1];
		int broj = Integer.parseInt(p[2]);
		String odgovor = null;
		
		for(Aerodrom a : aerodromi) {
			if(a.getIcao().compareTo(icao) == 0) {
				odgovor = "OK";
			}
		}
		if (odgovor != "OK") {
			krivaKomanda(osw, "ERROR 21 Nema traženog aerodroma!");
			
			try {
				osw.write(odgovor);
				osw.flush();
				osw.close();
			} catch (IOException e) {
				System.out.println("ERROR 29 Problem sa slanjem odgovora!");
			}
			return;
		}
		
		for(Aerodrom a : aerodromi) {
			String odgServera = ispitajUdaljenost(icao, a.getIcao());
			if (odgServera == "ERROR 22 >> Server ne radi!") {
				odgovor = odgServera;
				break;
			}
			String udaljenost = odgServera.split(" ")[1];
			if (Integer.parseInt(udaljenost) <= broj && Integer.parseInt(udaljenost) != 0) {
				odgovor += " " + a.getIcao() + " " + udaljenost + ";";
			}
		}
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			System.out.println("ERROR 29 Problem sa slanjem odgovora!");
		}
		return;
	}

	private String ispitajUdaljenost(String icao, @NonNull String icao2) {
		String komanda = "DISTANCE " + icao + " " + icao2;
		
		try (Socket veza = new Socket(SUdaljenostiAdresa, SUdaljenostiPort);
				InputStreamReader isr = new InputStreamReader(veza.getInputStream(), Charset.forName("UTF-8"));
				OutputStreamWriter osw = new OutputStreamWriter(veza.getOutputStream(), Charset.forName("UTF-8"));)
		{
			osw.write(komanda);
			osw.flush();
			veza.shutdownOutput();
			StringBuilder tekst = new StringBuilder();
			while (true) {
				int i = isr.read();
				if (i == -1) {
					break;
				}
				tekst.append((char) i);
			}
			veza.shutdownInput();
			veza.close();
			return tekst.toString();
		} catch (IOException e) {
			System.out.println("ERROR 22 >> Server ne radi!");
		} 
		return "ERROR 22 >> Server ne radi!";	
	}
}
