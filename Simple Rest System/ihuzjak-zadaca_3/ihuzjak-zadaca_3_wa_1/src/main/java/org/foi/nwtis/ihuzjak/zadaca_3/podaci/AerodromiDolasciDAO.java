package org.foi.nwtis.ihuzjak.zadaca_3.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.Konfiguracija;
import org.foi.nwtis.rest.podaci.AvionLeti;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;

public class AerodromiDolasciDAO {
	
	@Inject
	ServletContext context;
	
	String url;
	Connection con;
	PreparedStatement pstmt;
	
	Konfiguracija konfig;
	
	public AerodromiDolasciDAO(Konfiguracija konfig){
		
		this.konfig = konfig;
		url = konfig.dajPostavku("server.database")+konfig.dajPostavku("user.database");
	}
	
	public List<AvionLeti> dajAerodromeDolaske(String icao, Date date){
		
		long ms = date.getTime();
		System.out.println(">><>"+ms);
		String upit = "SELECT * FROM AERODROMI_DOLASCI WHERE ESTARRIVALAIRPORT='" + icao + "'"
				+ " AND LASTSEEN >=" + (ms/1000)
				+ " AND LASTSEEN <=" + ((ms + 24 * 3600 * 1000)/1000)
				+ ";";
		System.out.println(">><>"+upit);
		List<AvionLeti> aerodromi = saljiNaBazu(upit);
        return aerodromi;
	}

	private List<AvionLeti> saljiNaBazu(String upit) {
		
		List<AvionLeti> aerodromi = new ArrayList<>();
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
            	AvionLeti a = new AvionLeti();
                a.setIcao24(rs.getString("icao24"));
                
                a.setArrivalAirportCandidatesCount(rs.getInt("ArrivalAirportCandidatesCount"));
                a.setCallsign(rs.getString("callsign"));
                a.setDepartureAirportCandidatesCount(rs.getInt("departureAirportCandidatesCount"));
                a.setEstArrivalAirport(rs.getString("estArrivalAirport"));
                a.setEstArrivalAirportHorizDistance(rs.getInt("estArrivalAirportHorizDistance"));
                a.setEstArrivalAirportVertDistance(rs.getInt("estArrivalAirportVertDistance"));
                a.setEstDepartureAirport(rs.getString("estDepartureAirport"));
                a.setEstDepartureAirportHorizDistance(rs.getInt("estDepartureAirportHorizDistance"));
                a.setEstDepartureAirportVertDistance(rs.getInt("estDepartureAirportVertDistance"));
                a.setFirstSeen(rs.getInt("firstSeen"));
                a.setLastSeen(rs.getInt("lastSeen"));
                aerodromi.add(a);
            }
            pstmt.close();
            con.close();
        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            AvionLeti a = new AvionLeti();
            a.setIcao24("SQLException: " + ex.getMessage());
            aerodromi.add(a);
            ex.printStackTrace();
        }
        return aerodromi;
	}
	
	public int dajBrojZapisaDolasci(String icao) {

		String upit = "SELECT COUNT(*) FROM AERODROMI_DOLASCI WHERE ESTARRIVALAIRPORT='" + icao + "';";
		int broj = 1;
		try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver");
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
       try
       {   con = DriverManager.getConnection(url, konfig.dajPostavku("user.username"),
    		   konfig.dajPostavku("user.password"));
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
