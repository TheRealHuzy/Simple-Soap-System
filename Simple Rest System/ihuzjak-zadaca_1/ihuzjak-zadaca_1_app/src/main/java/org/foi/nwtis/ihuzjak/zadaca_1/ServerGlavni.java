package org.foi.nwtis.ihuzjak.zadaca_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.NeispravnaKonfiguracija;

public class ServerGlavni {

	int port;
	int maksCekaca;
	Socket veza = null;
	int aktivneDretve = 0;
	List<Korisnik> korisnici = new ArrayList<>();
	List<DretvaZahtjeva> dretve = new ArrayList<DretvaZahtjeva>();
	DretvaZahtjeva prekidajucaDretva;
	CacheServera cs = new CacheServera();
	boolean jednoDretveni = false;
	
	static public Konfiguracija konfig = null;
	static SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static void main(String[] args) {
		
		//TODO zamijeniti
		if(args.length != 1) {
			System.out.println("ERROR 40 >> Broj argumenata nije 1!");
			return;
		}
		ucitavanjePodataka(args[0]);
		if(konfig == null) {
			System.out.println("ERROR 49 >> Problem s konfiguracijom.");
			return;
		}		
		
		int port = Integer.parseInt(konfig.dajPostavku("port"));
		int maksCekaca = Integer.parseInt(konfig.dajPostavku("maks.cekaca"));
		String NazivDatotekeMeteoKorisnika = konfig.dajPostavku("datoteka.korisnika");
		
		ServerGlavni sm = new ServerGlavni(port, maksCekaca);
		
		if(sm.ispitajPort(port)) {
			return;
		}
		if(!sm.pripremiKorisnici(NazivDatotekeMeteoKorisnika)){
			return;
		};
		System.out.println("Broj podataka: " + sm.korisnici.size());
		
		sm.obradaZahtjeva();

	}
	
	private boolean pripremiKorisnici(String NazivDatotekeKorisnika) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(NazivDatotekeKorisnika, Charset.forName("UTF-8")));
			while (true) {
				String linija = br.readLine();
				if (linija == null || linija.isEmpty()) {
					break;
				}
				String[] p = linija.split(";");
				Korisnik k;
				k = new Korisnik(p[0], p[1], (p[2]), p[3]);
				korisnici.add(k);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("ERROR 49 >> Ne postoji konfiguracijska datoteka.");
			return false;
		}
		return true;
	}

	private static void ucitavanjePodataka(String nazivDatoteke) {
		try {
			konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
		} catch (NeispravnaKonfiguracija e) {
			e.printStackTrace();
		}
	}
	
	public ServerGlavni(int port, int maksCekaca) {
		super();
		this.port = port;
		this.maksCekaca = maksCekaca;
	}
	
	private boolean ispitajPort(int port) {
		try {
			ServerSocket s = new ServerSocket(port);
			s.close();
		} catch (IOException e) {
			System.out.println("ERROR 49 >> Trazeni socket je vec zauzet!");
			return true;
		}
		return false;
	}

	public synchronized void obradaZahtjeva() {

		try (ServerSocket ss = new ServerSocket(this.port, this.maksCekaca)) {
			while (true) {
				if (jednoDretveni) {
					System.out.println("Uso u jednodretveni >> " + jednoDretveni);
					for (DretvaZahtjeva dz : dretve) {
						if (dz != prekidajucaDretva) {
							try {
								dz.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					synchronized (prekidajucaDretva) {
						prekidajucaDretva.notify();
					}
					System.out.println("Cekam dretvu da izvrsi >> " + jednoDretveni);
					try {
						wait();

					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Dretva je izvrsena >> " + jednoDretveni);
				}
				System.out.println("Izaso iz jednodretveni >> " + jednoDretveni);
				System.out.println("Čekam korisnika."); // TODO kasnije obrisati
				this.veza = ss.accept();
				
				DretvaZahtjeva dretvaZahtjeva = new DretvaZahtjeva(this, veza, konfig, aktivneDretve);
				dretve.add(dretvaZahtjeva);
				dretvaZahtjeva.start();
				aktivneDretve++;
				
				try {
					wait();

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		} catch (IOException ex) {
			Logger.getLogger(ServerGlavni.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
	/*
	private void krivaKomanda(OutputStreamWriter osw, String odgovor) {
				try {
					osw.write(odgovor);
					osw.flush();
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}*/
}
