package org.foi.nwtis.ihuzjak.zadaca_3.slusaci;

import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.Konfiguracija;
import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ihuzjak.zadaca_2_lib_03_1.konfiguracije.NeispravnaKonfiguracija;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class SlusacAplikacije
 *
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {
	
    /**
     * Default constructor. 
     */
    public SlusacAplikacije() {
    }

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext context = sce.getServletContext();
		String nazivDatoteke = context.getInitParameter("konfiguracija");
		Konfiguracija konfig = null;
		String putanja = context.getRealPath("/WEB-INF") + java.io.File.separator;
		
		try {
			konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(putanja + nazivDatoteke);
			konfig.ucitajKonfiguraciju();
			context.setAttribute("Postavke", konfig);
		} catch (NeispravnaKonfiguracija e1) {
			e1.printStackTrace();
			return;
		}

		ServletContextListener.super.contextInitialized(sce);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		ServletContext context = sce.getServletContext();
		context.removeAttribute("Postavke");
		ServletContextListener.super.contextDestroyed(sce);
	}

}
