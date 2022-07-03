package org.foi.nwtis.ihuzjak.zadaca_3.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.Konfiguracija;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;

public class AerodromiPraceniDAO {
	
	@Inject
	ServletContext context;
	
	String url;
	Connection con;
	PreparedStatement pstmt;
	
	Konfiguracija konfig;
	
	public AerodromiPraceniDAO(Konfiguracija konfig){
		this.konfig = konfig;
		url = konfig.dajPostavku("server.database")+konfig.dajPostavku("user.database");
	}
	
	public List<AerodromPracen> dajSvePraceneAerodrome(String stranica, String broj){

        int pomak = izracunajPomak(stranica, broj);
		String upit = "SELECT * FROM AERODROMI_PRACENI LIMIT " + broj + " OFFSET " + pomak +";";
		
		List<AerodromPracen> aerodromi = saljiNaBazu(upit);
        return aerodromi;
	}
	
	public List<AerodromPracen> dajSvePraceneAerodrome(){
		
		String upit = "SELECT * FROM AERODROMI_PRACENI;";
		List<AerodromPracen> aerodromi = saljiNaBazu(upit);
        return aerodromi;
	}

	private List<AerodromPracen> saljiNaBazu(String upit) {
		
		List<AerodromPracen> aerodromi = new ArrayList<>();
		try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver");
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
        try
        {   
        	con = DriverManager.getConnection(url, konfig.dajPostavku("user.username"),
        			konfig.dajPostavku("user.password"));
            pstmt = con.prepareStatement(upit);
            ResultSet rs = pstmt.executeQuery();
                
            while (rs.next()) {
            	AerodromPracen a = new AerodromPracen("","");
                a.setIdent(rs.getString("ident"));
                aerodromi.add(a);
            }
            pstmt.close();
            con.close();
        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            AerodromPracen a = new AerodromPracen("","");
            a.setIdent("SQLException: " + ex.getMessage());
            aerodromi.add(a);
            ex.printStackTrace();
        }
        return aerodromi;
	}
	
	private int izracunajPomak(String stranica, String broj) {
		return (Integer.parseInt(stranica) * Integer.parseInt(broj)) - Integer.parseInt(broj);
	}
	
	public void posaljiAerodrom(String icao){

		String upit = "INSERT INTO AERODROMI_PRACENI (ident, stored) VALUES ('" + icao +"', CURRENT_TIMESTAMP);";
		upisiNaBazu(upit);
	}

	private void upisiNaBazu(String upit) {
		try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver");
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
        try
        {   
        	con = DriverManager.getConnection(url, konfig.dajPostavku("user.username"),
        			konfig.dajPostavku("user.password"));
            pstmt = con.prepareStatement(upit);
            pstmt.execute();
            pstmt.close();
            con.close();
        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
	}
}