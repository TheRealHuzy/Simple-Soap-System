package org.foi.nwtis.ihuzjak.zadaca_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.Konfiguracija;

public class DretvaZahtjeva extends Thread {

	static boolean test = true;
	
	ServerGlavni serverGlavni = null;
	Konfiguracija konfig = null;
	Socket veza = null;
	int indeksDretve;
	List<Korisnik> korisnici = new ArrayList<>();
	File datoteka, privremenaDatoteka;
	String imeDatoteke, imePrivremeneDatoteke = "Stat.txt";
	
	String SAerodromaAdresa;
	int SAerodromaPort;
	String SMeteoAdresa;
	int SMeteoPort;
	String SUdaljenostiAdresa;
	int SUdaljenostiPort;
	
	static SimpleDateFormat isoFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
	
	String imeLozinka = "^(USER \\w+ PASSWORD \\w+) (.*)$";
	
	String airportIcao = "^AIRPORT ([A-Z]{4})$";
	String airportIcaoBroj = "^AIRPORT [A-Z]{4} [+-]?[0-9]+$";
	
	String meteoIcao = "^METEO ([A-Z]{4})$";
	String meteoIcaoDatum = "^METEO ([A-Z]{4}) (\\d{4}-\\d{2}-\\d{2})$";
	String tempTempTemp = "^TEMP [+-]?([0-9]+[\\,\\.]?[0-9]*|[\\,\\.][0-9]+)"
			+ " [+-]?([0-9]+[\\,\\.]?[0-9]*|[\\,\\.][0-9]+)$";
	String tempTempTempDatum = "^TEMP [+-]?([0-9]+[\\,\\.]?[0-9]*|[\\,\\.][0-9]+) "
			+ "[+-]?([0-9]+[\\,\\.]?[0-9]*|[\\,\\.][0-9]+) ?(\\d{4}-\\d{2}-\\d{2})$";
	
	String distanceIcaoIcao= "^DISTANCE ([A-Z]{4}) ([A-Z]{4})$";
	String distanceClear = "^DISTANCE CLEAR$";
	
	String cacheBackup = "^CACHE BACKUP$";
	String cacheRestore = "^CACHE RESTORE$";
	String cacheClear = "^CACHE CLEAR$";
	String cacheStat = "^CACHE STAT$";
	
	Matcher mMeteoIcao;
	Matcher mMeteoIcaoDatum;
	Matcher mTempTempTemp;
	Matcher mTempTempTempDatum;
	
	Matcher mAirportIcao;
	Matcher mAirportIcaoBroj;
	
	Matcher mDistanceIcaoIcao;
	Matcher mDistanceClear;
	
	Matcher mCacheBackup;
	Matcher mCacheRestore;
	Matcher mCacheClear;
	Matcher mCacheStat;
		
	//TODO pogledati za naziv dretve
	public DretvaZahtjeva(ServerGlavni serverGlavni, Socket veza, Konfiguracija konfig, int indeksDretve) {
		super();
		this.serverGlavni = serverGlavni;
		this.konfig = konfig;
		this.veza = veza;
		this.indeksDretve = indeksDretve;
		this.korisnici = serverGlavni.korisnici;
	}

	@Override
	public synchronized void start() {
		super.start();
		
		this.SAerodromaAdresa = konfig.dajPostavku("server.aerodroma.adresa");
		this.SAerodromaPort = Integer.parseInt(konfig.dajPostavku("server.aerodroma.port"));
		this.SMeteoAdresa = konfig.dajPostavku("server.meteo.adresa");
		this.SMeteoPort = Integer.parseInt(konfig.dajPostavku("server.meteo.port"));
		this.SUdaljenostiAdresa = konfig.dajPostavku("server.udaljenosti.adresa");
		this.SUdaljenostiPort = Integer.parseInt(konfig.dajPostavku("server.udaljenosti.port"));
		this.imeDatoteke = konfig.dajPostavku("datoteka.meduspremnika");
	}

	@Override
	public synchronized void run() {
		
		Thread.currentThread().setName("ihuzjak_" + indeksDretve);
		//izbrisi ovo <<<<<<--------------------------------------------------
		if (test) {
			dodajGluposti();
			test = false;
		}
		
		try (InputStreamReader isr = new InputStreamReader(this.veza.getInputStream(), Charset.forName("UTF-8"));
				OutputStreamWriter osw = new OutputStreamWriter(this.veza.getOutputStream(),
						Charset.forName("UTF-8"));) {
			StringBuilder tekst = new StringBuilder();
			while (true) {
				int i = 0;
				try {
					i = isr.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (i == -1) {
					break;
				}
				tekst.append((char) i);
			}
			System.out.println(tekst.toString()); // TODO kasnije obrisati
			try {
				this.veza.shutdownInput();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Pattern pImeLozinka = Pattern.compile(imeLozinka);
			Matcher mImeLozinka = pImeLozinka.matcher(tekst.toString());
			boolean dobarLogin = false;
			
			if (mImeLozinka.matches()) {
				String imeLozinka = mImeLozinka.group(1).toString();
				String ime = imeLozinka.split(" ")[1];
				String lozinka = imeLozinka.split(" ")[3];
				
				for(Korisnik k: korisnici) {
					if (k.getIme().compareTo(ime) == 0 && k.getLozinka().compareTo(lozinka) == 0) {
						dobarLogin = true;
						break;
					}
				}
			} else {
				krivaKomanda(osw, "ERROR 40 Sintaksa komande nije uredu!");
			}
			if (!dobarLogin) {
				krivaKomanda(osw, "ERROR 41 Netočno korsiničko ime i/ili lozinka!");
			} else {
				
				String komanda = mImeLozinka.group(2).toString();
				String odgovor = provjeriMemoriju(komanda);
				
				if(odgovor.compareTo("") != 0){
					try {
						osw.write(odgovor);
						osw.flush();
						osw.close();
					} catch (IOException e) {
						System.out.println("ERROR 49 Problem sa slanjem odgovora!");
					}
					return;
				}
				Pattern pMeteoIcao = Pattern.compile(meteoIcao);
				Pattern pMeteoIcaoDatum = Pattern.compile(meteoIcaoDatum);
				Pattern pTempTempTemp = Pattern.compile(tempTempTemp);
				Pattern pTempTempTempDatum = Pattern.compile(tempTempTempDatum);
				
				mMeteoIcao = pMeteoIcao.matcher(komanda.toString());
				mMeteoIcaoDatum = pMeteoIcaoDatum.matcher(komanda.toString());
				mTempTempTemp = pTempTempTemp.matcher(komanda.toString());
				mTempTempTempDatum = pTempTempTempDatum.matcher(komanda.toString());
				
				Pattern pAirportIcao = Pattern.compile(airportIcao);
				Pattern pAirportIcaoBroj = Pattern.compile(airportIcaoBroj);
				
				mAirportIcao = pAirportIcao.matcher(komanda.toString());
				mAirportIcaoBroj = pAirportIcaoBroj.matcher(komanda.toString());
				
				Pattern pDistanceIcaoIcao = Pattern.compile(distanceIcaoIcao);
				Pattern pDistanceClear = Pattern.compile(distanceClear);
				
				mDistanceIcaoIcao = pDistanceIcaoIcao.matcher(komanda.toString());
				mDistanceClear = pDistanceClear.matcher(komanda.toString());
				
				Pattern pCacheBackup = Pattern.compile(cacheBackup);
				Pattern pCacheRestore = Pattern.compile(cacheRestore);
				Pattern pCacheClear = Pattern.compile(cacheClear);
				Pattern pCacheStat = Pattern.compile(cacheStat);
				
				mCacheBackup = pCacheBackup.matcher(komanda.toString());
				mCacheRestore = pCacheRestore.matcher(komanda.toString());
				mCacheClear = pCacheClear.matcher(komanda.toString());
				mCacheStat = pCacheStat.matcher(komanda.toString());
				
				if(mTempTempTemp.matches() || mTempTempTempDatum.matches()) {
					odgovor = pozoviServer(osw, komanda.toString(), SMeteoAdresa, SMeteoPort);
					synchronized (serverGlavni) {
						serverGlavni.notify();
					}
				} else if (mMeteoIcao.matches() || mMeteoIcaoDatum.matches()) {
					String icao = komanda.split(" ")[1];
					String komanda2 = "AIRPORT " + icao;
					odgovor = pozoviServer(osw, komanda2.toString(), SAerodromaAdresa, SAerodromaPort);
					if (odgovor.startsWith("OK")) {
						odgovor = pozoviServer(osw, komanda.toString(), SMeteoAdresa, SMeteoPort);
					}
					synchronized (serverGlavni) {
						serverGlavni.notify();
					}
				} else if (mAirportIcao.matches() || mAirportIcaoBroj.matches()) {
					odgovor = pozoviServer(osw, komanda.toString(), SAerodromaAdresa, SAerodromaPort);
					synchronized (serverGlavni) {
						serverGlavni.notify();
					}
				} else if (mDistanceIcaoIcao.matches() || mDistanceClear.matches()){
					odgovor = pozoviServer(osw, komanda.toString(), SUdaljenostiAdresa, SUdaljenostiPort);
					synchronized (serverGlavni) {
						serverGlavni.notify();
					}
				} else if (mCacheBackup.matches() || mCacheRestore.matches() || mCacheClear.matches() || mCacheStat.matches()){
					serverGlavni.jednoDretveni = true;
					serverGlavni.prekidajucaDretva = this;
					synchronized (serverGlavni) {
						serverGlavni.notify();
					}
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					odgovor = obradiCacheNaredbu();
					serverGlavni.jednoDretveni = false;
					synchronized (serverGlavni) {
						serverGlavni.notify();
					}
				} else {
					krivaKomanda(osw, "ERROR 40 >> Sintaksa komande nije uredu");
					return;
				}
				
				if(provjeriUnosUMemoriju()){
					spremiUMemoriju(komanda, odgovor);
				}
				
				try {
					osw.write(odgovor);
					osw.flush();
					osw.close();
				} catch (IOException e) {
					System.out.println("ERROR 49 Problem sa slanjem odgovora!");
				}
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean provjeriUnosUMemoriju() {
		if(mAirportIcao.matches() || mAirportIcaoBroj.matches() || mDistanceIcaoIcao.matches()) {
			return true;
		}
		return false;
	}

	private String pozoviServer(OutputStreamWriter osw2, String komanda, String adresa, int port) {
		try (Socket veza = new Socket(adresa, port);
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
			if (port == SMeteoPort) {
				krivaKomanda(osw2, "ERROR 42 >> Greška pri spajanju na ServerMeteo!");
			} else if (port == SAerodromaPort) {
				krivaKomanda(osw2, "ERROR 43 >> Greška pri spajanju na ServerAerodroma!");
			} else if (port == SUdaljenostiPort) {
				krivaKomanda(osw2, "ERROR 44 >> Greška pri spajanju na ServerAerodroma!");
			} else {
				krivaKomanda(osw2, "ERROR 49 >> Nepoznat port za spajanje!");
			}
		}
		return "ERROR 99";	
	}
	
	private String obradiCacheNaredbu() {
		/*
		try {
			sleep(20000);            ---------<--------maknuti cijeli tryCatch
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String odgovor = "";
		if (mCacheBackup.matches()) {
			pisiUSerijalizinuDatoteku();
			odgovor = "OK";
		} else if (mCacheRestore.matches()) {
			ucitajSerijalizinuDatoteku();
			odgovor = "OK";
		} else if (mCacheClear.matches()) {
			odgovor = ocistiMemoriju();
			odgovor = "OK";
		} else if (mCacheStat.matches()) {
			sortirajMemoriju();
			odgovor = ispisiStatistiku();
			//upisiUDatoteku(odgovor);
		}
		return odgovor;
	}

	private void krivaKomanda(OutputStreamWriter osw, String odgovor) {
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void interrupt() {
		super.interrupt();
	}
	
	public String posaljiKomandu(String adresa, int port, String komanda) {
		try (Socket veza = new Socket(adresa, port);
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
		} catch (SocketException e) {
			ispis(e.getMessage());
		} catch (IOException ex) {
			ispis(ex.getMessage());
		}
		return null;
	}

	private void ispis(String message) {
		System.out.println(message);
	}
	
	private String provjeriMemoriju(String komanda) {
		
		for (Memorija m : serverGlavni.cs.memorija) {
			if (m.komanda.compareTo(komanda) == 0) {
				Date date = new Date();
				m.zadnjeVrijeme = isoFormat.format(new Timestamp(date.getTime()));
				m.brojPoziva++;
				return m.odgovor;
			}
		}
		return "";
	}
	
	private void spremiUMemoriju(String komanda, String odgovor) {
		
		Date date = new Date();
		String datumVrijeme = isoFormat.format(new Timestamp(date.getTime()));
		Memorija m = new Memorija(komanda, odgovor, 1, datumVrijeme);
		serverGlavni.cs.memorija.add(m);
		return;
	}
	
	private void sortirajMemoriju() {
		Collections.sort(serverGlavni.cs.memorija, Collections.reverseOrder());
	}
	
	private void dodajGluposti() {
		Date date = new Date();
		String datumVrijeme = isoFormat.format(new Timestamp(date.getTime()));
		Memorija m = new Memorija("AE ICAO", "OK", 1, datumVrijeme);
		serverGlavni.cs.memorija.add(m);
		m = new Memorija("TEMP 4,0 5,0 2021-01-07", "OK", 2, datumVrijeme);
		serverGlavni.cs.memorija.add(m);
		m = new Memorija("METEO LDSP 2021-01-07", "OK", 1, datumVrijeme);
		serverGlavni.cs.memorija.add(m);
		m = new Memorija("AE ICAO", "OK", 4, datumVrijeme);
		serverGlavni.cs.memorija.add(m);
		m = new Memorija("AE ICAO", "OK", 7, datumVrijeme);
		serverGlavni.cs.memorija.add(m);
	}
	
	private String ocistiMemoriju() {
		serverGlavni.cs.memorija.clear();
		return "OK";
	}
	
	private String ispisiStatistiku() {
		String odgovor = "OK ";
		String odgovor2 = "";
		try {
			odgovor2 += String.format("%-30s%5s%30s", "Predmet komande", "Broj korištenja", "Zadnje vrijeme" + "\\n\n");
			for (Memorija m : serverGlavni.cs.memorija) {
				odgovor2 += stvoriStatIspis(m);
			}
			odgovor += prebrojiZnakove(odgovor2) +";\\n\n";
		} catch (Exception e) {
			odgovor = "ERROR 49 >> Problem sa privremenom datotekom!";
			return odgovor;
		}
		return odgovor + odgovor2;
		
	}
	private int prebrojiZnakove(String odgovor) {
		
		return odgovor.length();
	}
	
	private String stvoriStatIspis(Memorija m) {
		
		String brojPoziva = String.valueOf(m.brojPoziva);
		String linija = String.format("%-30s%15s%30s", m.komanda, brojPoziva, m.zadnjeVrijeme + "\\n\n");
		return linija;
	}
	
	private void upisiUDatoteku(String odgovor) {
		datoteka = new File(imeDatoteke);
		File privremenaDatoteka = new File(imePrivremeneDatoteke);
		try (FileWriter fw = new FileWriter(privremenaDatoteka);)
		{
			if (!privremenaDatoteka.exists()) {
				privremenaDatoteka.createNewFile();
			}
			System.out.println("Stvorio sam datoteku!");
			fw.write(odgovor);
		} catch (IOException e) {
			e.printStackTrace();
			//System.out.println("ERROR 49 >> Pogreška u pisanju u datoteku!");
		}
	}
	
	private void ucitajSerijalizinuDatoteku(){
		datoteka = new File(imeDatoteke);
		try (FileInputStream fis = new FileInputStream(datoteka);
				ObjectInputStream ois = new ObjectInputStream(fis);)
		{
			CacheServera cs = (CacheServera) ois.readObject();
			serverGlavni.cs = cs;
		}
		catch (Exception e) {
			e.printStackTrace();
			//System.out.println("ERROR 49 >> Greška u citanju datoteke!");
		}
		return;
	}
	
	private void pisiUSerijalizinuDatoteku(){
		datoteka = new File(imeDatoteke);
		try (FileOutputStream fos = new FileOutputStream(datoteka);
				ObjectOutputStream oos = new ObjectOutputStream(fos);)
		{
			oos.writeObject(serverGlavni.cs);
		}
		catch (Exception e) {
			e.printStackTrace();
			//System.out.println("ERROR 49 >> Greška u pisanju u datoteku!");
		}
		return;
	}
}


