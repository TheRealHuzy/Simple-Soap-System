package org.foi.nwtis.ihuzjak.zadaca_1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.NeispravnaKonfiguracija;

public class ServerUdaljenosti {

	int port;
	int maksCekaca;
	String SAerodromaAdresa;
	int SAerodromaPort;
	
	List<Aerodrom> aerodromi = new ArrayList<>();
	
	String distanceIcaoIcao = "^DISTANCE ([A-Z]{4}) ([A-Z]{4})$";
	String distanceClear = "^DISTANCE CLEAR$";
	
	private static final DecimalFormat df = new DecimalFormat("0");
	
	static public Konfiguracija konfig = null;

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("ERROR 30 >> Broj argumenata nije 1!");
			return;
		}
		ucitavanjePodataka(args[0]);
		if(konfig == null) {
			System.out.println("ERROR 39 >> Problem s konfiguracijom.");
			return;
		}		
		
		int port = Integer.parseInt(konfig.dajPostavku("port"));
		int maksCekaca = Integer.parseInt(konfig.dajPostavku("maks.cekaca"));
		int sUdaljenostiPort = Integer.parseInt(konfig.dajPostavku("server.aerodroma.port"));
		String sUdaljenostiAdresa = konfig.dajPostavku("server.aerodroma.adresa");
				
		ServerUdaljenosti sm = new ServerUdaljenosti(port, maksCekaca, sUdaljenostiAdresa, sUdaljenostiPort);
		if(sm.ispitajPort(port)) {
			return;
		}
				
		sm.obradaZahtjeva();

	}
	
	public ServerUdaljenosti(int port, int maksCekaca, String sUdaljenostiAdresa, int sUdaljenostiPort) {
		super();
		this.port = port;
		this.maksCekaca = maksCekaca;
		SAerodromaAdresa = sUdaljenostiAdresa;
		SAerodromaPort = sUdaljenostiPort;
	}
	
	private static void ucitavanjePodataka(String nazivDatoteke) {
		try {
			konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
		} catch (NeispravnaKonfiguracija e) {
			System.out.println("ERROR 39 >> Greška pri učitavanju datoteke!");
		}
	}
	
	private boolean ispitajPort(int port) {
		try {
			ServerSocket s = new ServerSocket(port);
			s.close();
		} catch (IOException e) {
			System.out.println("ERROR 39 >> Trazeni socket je vec zauzet!");
			return true;
		}
		return false;
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
				Pattern pDistanceIcaoIcao = Pattern.compile(distanceIcaoIcao);
				Pattern pDistanceClear = Pattern.compile(distanceClear);
				
				Matcher mDistanceIcaoIcao = pDistanceIcaoIcao.matcher(tekst.toString());
				Matcher mDistanceClear = pDistanceClear.matcher(tekst.toString());
				
				if(mDistanceIcaoIcao.matches()) {
					izvrsiDistanceIcaoIcao(osw, tekst.toString());
				} else if (mDistanceClear.matches()) {
					izvrsiDistanceClear(osw, tekst.toString());
				} else {
					krivaKomanda(osw, "ERROR 30 Sintaksa komande nije uredu!");
				}
			} catch (IOException e) {
				System.out.println("ERROR 39 >> Port je već zauzet!");
			}
		}
	}

	private void izvrsiDistanceIcaoIcao(OutputStreamWriter osw, String komanda) {
		String[] p = komanda.split(" ");
		String icao = p[1];
		String icao2 = p[2];
		Aerodrom a1 = null;
		Aerodrom a2 = null;
		String udaljenost = null;
		String odgovor = null;
		
		for(Aerodrom a : aerodromi) {
			if(a.getIcao().compareTo(icao) == 0) {
				a1 = a;
				break;
			}
		}
		for(Aerodrom a : aerodromi) {
			if(a.getIcao().compareTo(icao2) == 0) {
				a2 = a;
				break;
			}
		}
		if(a1 == null) {
			a1 = dajAerodrom(icao);
		}
		if(a2 == null) {
			a2 = dajAerodrom(icao2);
		}
		if(a1 == null) {
			krivaKomanda(osw, "ERROR 21 Nema traženog aerodroma!");
			return;
		} else {
			aerodromi.add(a1);
		}
		if(a2 == null) {
			krivaKomanda(osw, "ERROR 21 Nema traženog aerodroma!");
			return;
		} else {
			aerodromi.add(a2);
		}
		udaljenost = izracunajUdaljenost(a1, a2);
		odgovor = "OK " + udaljenost;
		
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			System.out.println("ERROR 29 Problem sa slanjem odgovora!");
		}
		return;
	}
	
	private Aerodrom dajAerodrom(String icao) {
		String komanda = "AIRPORT " + icao;
		Aerodrom a = null;
		try (Socket veza = new Socket(SAerodromaAdresa, SAerodromaPort);
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
			a = kreirajAerodrom(tekst.toString());
			return a;
		} catch (SocketException e) {
			System.out.println("ERROR 29 >> Neuspješno stvaranje socketa");
		} catch (IOException ex) {
			System.out.println("ERROR 29 >> Port je već zauzet!");
		}
		return a;
	}
	
	private Aerodrom kreirajAerodrom(String komanda) {
		
		String p[] = komanda.split(" ");
		String icao = p[1];
		
		int indeks = komanda.indexOf('\"');
		int indeks2 = komanda.indexOf('\"', indeks + 1);
		
		String naziv = komanda.substring(indeks + 1, indeks2);
		String ostatak = komanda.substring(indeks2 + 2, komanda.length() - 1);
		
		String o[] = ostatak.split(" ");
		String GpsGS = o[0];
		String GpsGD = o[1];
		return new Aerodrom(icao, naziv, GpsGS, GpsGD);
	}

	private String izracunajUdaljenost(Aerodrom a1, Aerodrom a2) {
		
		double a1GD = Double.parseDouble(a1.getGpsGD());
		double a1GS = Double.parseDouble(a1.getGpsGS());
		double a2GD = Double.parseDouble(a2.getGpsGD());
		double a2GS = Double.parseDouble(a2.getGpsGS());
		
		double udaljenostD = Math.toRadians(a2GD - a1GD);
		double udaljenostS = Math.toRadians(a2GS - a1GS);
		
		double izracun = Math.pow(Math.sin(udaljenostS / 2), 2)
				+ Math.cos(Math.toRadians(a1GS)) * Math.cos(Math.toRadians(a2GS))
				* Math.pow(Math.sin(udaljenostD / 2), 2);
		
		double udaljenost = (2 * Math.atan2(Math.sqrt(izracun), Math.sqrt(1 - izracun))) * 6371;
		return df.format(udaljenost);
	}

	private void izvrsiDistanceClear(OutputStreamWriter osw, String komanda) {
		aerodromi.clear();
		try {
			osw.write("OK");
			osw.flush();
			osw.close();
		} catch (IOException e) {
			System.out.println("ERROR 39 Problem sa slanjem odgovora!");
		}
		return;
	}
}
