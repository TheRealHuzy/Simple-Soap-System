package org.foi.nwtis.ihuzjak.zadaca_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ihuzjak.vjezba_03.konfiguracije.NeispravnaKonfiguracija;

public class ServerMeteo {

	int port;
	int maksCekaca;
	Socket veza = null;
	List<AerodromMeteo> aerodromiMeteo = new ArrayList<>();
	String meteoIcao = "^METEO ([A-Z]{4})$";
	String meteoIcaoDatum = "^METEO ([A-Z]{4}) (\\d{4}-\\d{2}-\\d{2})$";
	String tempTempTemp = "^TEMP [+-]?([0-9]+[\\,\\.]?[0-9]*|[\\,\\.][0-9]+)"
			+ " [+-]?([0-9]+[\\,\\.]?[0-9]*|[\\,\\.][0-9]+)$";
	String tempTempTempDatum = "^TEMP [+-]?([0-9]+[\\,\\.]?[0-9]*|[\\,\\.][0-9]+) "
			+ "[+-]?([0-9]+[\\,\\.]?[0-9]*|[\\,\\.][0-9]+) ?(\\d{4}-\\d{2}-\\d{2})$";
	
	static public Konfiguracija konfig = null;
	static SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	static SimpleDateFormat datumFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final DecimalFormat df = new DecimalFormat("0.0");

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("ERROR 10 >> Broj argumenata nije 1!");
			return;
		}
		ucitavanjePodataka(args[0]);
		if(konfig == null) {
			System.out.println("ERROR 19 >> Problem s konfiguracijom.");
			return;
		}		
		
		int port = Integer.parseInt(konfig.dajPostavku("port"));
		int maksCekaca = Integer.parseInt(konfig.dajPostavku("maks.cekaca"));
		
		ServerMeteo sm = new ServerMeteo(port, maksCekaca);
		if(sm.ispitajPort(port)) {
			return;
		}
		String NazivDatotekeMeteoPodataka = konfig.dajPostavku("datoteka.meteo");
		
		if(!sm.pripremiMeteo(NazivDatotekeMeteoPodataka)){
			return;
		};
		System.out.println("Broj podataka: " + sm.aerodromiMeteo.size());
		sm.obradaZahtjeva();

	}
	
	private boolean ispitajPort(int port) {
		try {
			ServerSocket s = new ServerSocket(port);
			s.close();
		} catch (IOException e) {
			System.out.println("ERROR 19 >> Trazeni socket je vec zauzet!");
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
				AerodromMeteo am;
				try {
					am = new AerodromMeteo(p[0], Double.parseDouble(p[1]), Double.parseDouble(p[3]),
							Double.parseDouble(p[2]), p[4], isoFormat.parse(p[4]).getTime());
					aerodromiMeteo.add(am);
				} catch (NumberFormatException | ParseException e) {
					e.printStackTrace();
					return false;
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("ERROR 19 >> Ne postoji datoteka zadana u konfiguraciji s nazivom: "
					+ nazivDatotekeMeteoPodataka);
			return false;
		}
		return true;
	}

	private static void ucitavanjePodataka(String nazivDatoteke) {
		try {
			konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
		} catch (NeispravnaKonfiguracija e) {
			System.out.println("ERROR 19 >> Greška pri učitavanju datoteke.");
		}
	}

	public ServerMeteo(int port, int maksCekaca) {
		super();
		this.port = port;
		this.maksCekaca = maksCekaca;
	}

	public void obradaZahtjeva() {

		try (ServerSocket ss = new ServerSocket(this.port, this.maksCekaca))
		{
			while (true) {
				System.out.println("Čekam korisnika."); // TODO kasnije obrisati
				this.veza = ss.accept();

				try (InputStreamReader isr = new InputStreamReader(this.veza.getInputStream(),
						Charset.forName("UTF-8"));
						OutputStreamWriter osw = new OutputStreamWriter(this.veza.getOutputStream(),
								Charset.forName("UTF-8"));) {

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
					Pattern pMeteoIcao = Pattern.compile(meteoIcao);
					Pattern pMeteoIcaoDatum = Pattern.compile(meteoIcaoDatum);
					Pattern pTempTempTemp = Pattern.compile(tempTempTemp);
					Pattern pTempTempTempDatum = Pattern.compile(tempTempTempDatum);
					
					Matcher mMeteoIcao = pMeteoIcao.matcher(tekst.toString());
					Matcher mMeteoIcaoDatum = pMeteoIcaoDatum.matcher(tekst.toString());
					Matcher mTempTempTemp = pTempTempTemp.matcher(tekst.toString());
					Matcher mTempTempTempDatum = pTempTempTempDatum.matcher(tekst.toString());
					
					if(mMeteoIcao.matches()) {
						izvrsiMeteoIcao(osw, tekst.toString());
					} else if (mMeteoIcaoDatum.matches()) {
						izvrsiMeteoIcaoDatum(osw, tekst.toString());
					} else if (mTempTempTemp.matches()){
						izvrsiTempTempTemp(osw, tekst.toString());
					} else if (mTempTempTempDatum.matches()) {
						izvrsiTempTempTempDatum(osw, tekst.toString());
					} else {
						krivaKomanda(osw, "ERROR 10 >> Sintaksa komande nije uredu");
					}

					
				} catch (SocketException e) {
					System.out.println("ERROR 19 >> Neuspješno stvaranje socketa");
				}
			}

		} catch (IOException ex) {
			//Logger.getLogger(ServerMeteo.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("ERROR 19 >> Port je već zauzet!");
			return;
		}

	}

	private void izvrsiMeteoIcao(OutputStreamWriter osw, String komanda) {
		String[] p = komanda.split(" ");
		String icao = p[1];
		String odgovor = null;
		AerodromMeteo nAerodrom = null;
		
		for(AerodromMeteo am : this.aerodromiMeteo) {
			
			if(am.getIcao().compareTo(icao) == 0) {
				if (nAerodrom == null) {
					nAerodrom = am;
				} else if (nAerodrom.getTime() < am.getTime()) {
					nAerodrom = am;
				}
			}
		}
		if (nAerodrom == null) {
			krivaKomanda(osw, "ERROR 11 Nema traženog aerodroma!");
			return;
		}
		
		odgovor = "OK " + df.format(nAerodrom.getTemp()) + " " + nAerodrom.getVlaga() + " " + nAerodrom.getTlak() +
				" " + nAerodrom.getVrijeme() + ";";
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	private void izvrsiMeteoIcaoDatum(OutputStreamWriter osw, String komanda) {
		String[] p = komanda.split(" ");
		String icao = p[1];
		long datum = 0;
		
		try {
			datum = datumFormat.parse(p[2]).getTime();
		} catch (ParseException e1) {
			System.out.println("ERROR 19 >> Neispravan datum!");
		}
		String odgovor = null;
		
		for(AerodromMeteo am : this.aerodromiMeteo) {
			if(am.getIcao().compareTo(icao) == 0 && am.getTime() > datum && am.getTime() < (long)(datum + 86400000)) {
				if (odgovor == null) {
					odgovor = "OK";
				}
				 odgovor += " " + df.format(am.getTemp()) + " " + am.getVlaga() + " " + am.getTlak()+
					" " + am.getVrijeme() + ";";
			}
		}
		if (odgovor == null) {
			krivaKomanda(osw, "ERROR 11 Nema traženog aerodroma ili podataka na zadani datum!");
			return;
		}
		
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	private void izvrsiTempTempTemp(OutputStreamWriter osw, String komanda) {
		komanda = komanda.replace(',', '.');
		String[] p = komanda.split(" ");
		double temp1 = Double.parseDouble(p[1]);
		double temp2 = Double.parseDouble(p[2]);

		String odgovor = null;
		
		for(AerodromMeteo am : this.aerodromiMeteo) {
			
			if(am.getTemp() >= temp1 && am.getTemp() <= temp2) {
				if (odgovor == null) {
					odgovor = "OK";
				}
				 odgovor += " " + am.getIcao() + " " + df.format(am.getTemp()) + " " + am.getVlaga() + " " + am.getTlak() +
					" " + am.getVrijeme() + ";";
			}
		}
		if (odgovor == null) {
			krivaKomanda(osw, "ERROR 11 Nema temperature u traženom rasponu!");
			return;
		}
		
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	private void izvrsiTempTempTempDatum(OutputStreamWriter osw, String komanda) {
		komanda = komanda.replace(',', '.');
		String[] p = komanda.split(" ");
		double temp1 = Double.parseDouble(p[1]);
		double temp2 = Double.parseDouble(p[2]);
		long datum = 0;
		
		try {
			datum = datumFormat.parse(p[3]).getTime();
		} catch (ParseException e1) {
			System.out.println("ERROR 19 >> Neispravan datum!");
		}
		String odgovor = null;
		
		for(AerodromMeteo am : this.aerodromiMeteo) {
			
			if(am.getTemp() > temp1 && am.getTemp() < temp2 && am.getTime() > datum && am.getTime() < datum + 86400000) {
				if (odgovor == null) {
					odgovor = "OK";
				}
				 odgovor += " " + am.getIcao() + " " + df.format(am.getTemp()) + " " + am.getVlaga() + " " + am.getTlak() +
					" " + am.getVrijeme()+ ";";
			}
		}
		if (odgovor == null) {
			krivaKomanda(osw, "ERROR 11 Nema temperature u traženom rasponu ili tog datuma!");
			return;
		}
		
		try {
			osw.write(odgovor);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;	
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
}
