package org.foi.nwtis.ihuzjak.zadaca_3.dretve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.zadaca_3.wsock.Info;

public class Osvjezivac extends Thread {

	boolean kraj = false;
	int vrijemeSpavanja;
	
	Konfiguracija konf;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	public Osvjezivac(Konfiguracija konf) {
		
		this.konf = konf;
	}
	
	@Override
	public synchronized void start() {
		
		vrijemeSpavanja = Integer.parseInt(konf.dajPostavku("ciklus.spavanje"));
		super.start();
	}

	@Override
	public void run() {
		
		while(!kraj) {
			Timestamp tVrijemePocetka = new Timestamp(System.currentTimeMillis());
			String vrijeme = sdf.format(tVrijemePocetka).toString();
			int brojAerodroma = dajBrojPracenihZapisa();
			
			Info.informiraj(vrijeme + "," + brojAerodroma);
			
			try {
				Thread.sleep(vrijemeSpavanja*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void interrupt() {
		
		kraj = true;
		super.interrupt();
	}
	
	public int dajBrojPracenihZapisa() {
		
		Connection con;
		PreparedStatement pstmt;
		String url = konf.dajPostavku("server.database") + konf.dajPostavku("user.database");

		String upit = "SELECT COUNT(*) FROM AERODROMI_PRACENI;";
		int broj = 1;
		try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver");
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
       try
       {   con = DriverManager.getConnection(url, konf.dajPostavku("user.username"),
    		   konf.dajPostavku("user.password"));
           pstmt = con.prepareStatement(upit);
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()) {
        	   broj = rs.getInt(1);
           }
           pstmt.close();
           con.close();
       } catch(SQLException ex) {
           System.err.println("SQLException: " + ex.getMessage());
           ex.printStackTrace();
       }
		return broj;
	}

}
