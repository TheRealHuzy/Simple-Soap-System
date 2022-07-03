
package org.foi.nwtis.ihuzjak.ws.aerodromi;

import java.util.List;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.Action;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 3.0.0
 * Generated source version: 3.0
 * 
 */
@WebService(name = "WsAerodromi", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WsAerodromi {


    /**
     * 
     * @return
     *     returns java.util.List<org.foi.nwtis.ihuzjak.ws.aerodromi.Aerodrom>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajSveAerodrome", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajSveAerodrome")
    @ResponseWrapper(localName = "dajSveAerodromeResponse", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajSveAerodromeResponse")
    @Action(input = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajSveAerodromeRequest", output = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajSveAerodromeResponse")
    public List<Aerodrom> dajSveAerodrome();

    /**
     * 
     * @return
     *     returns java.util.List<org.foi.nwtis.ihuzjak.ws.aerodromi.Aerodrom>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajAerodromeZaPratiti", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajAerodromeZaPratiti")
    @ResponseWrapper(localName = "dajAerodromeZaPratitiResponse", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajAerodromeZaPratitiResponse")
    @Action(input = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajAerodromeZaPratitiRequest", output = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajAerodromeZaPratitiResponse")
    public List<Aerodrom> dajAerodromeZaPratiti();

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "dodajAerodromPreuzimanje", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DodajAerodromPreuzimanje")
    @ResponseWrapper(localName = "dodajAerodromPreuzimanjeResponse", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DodajAerodromPreuzimanjeResponse")
    @Action(input = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dodajAerodromPreuzimanjeRequest", output = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dodajAerodromPreuzimanjeResponse")
    public void dodajAerodromPreuzimanje(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns org.foi.nwtis.ihuzjak.ws.aerodromi.Aerodrom
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajNajbliziAerodrom", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajNajbliziAerodrom")
    @ResponseWrapper(localName = "dajNajbliziAerodromResponse", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajNajbliziAerodromResponse")
    @Action(input = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajNajbliziAerodromRequest", output = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajNajbliziAerodromResponse")
    public Aerodrom dajNajbliziAerodrom(
        @WebParam(name = "arg0", targetNamespace = "")
        Lokacija arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        boolean arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<org.foi.nwtis.ihuzjak.ws.aerodromi.AvionLeti>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajDolaskeAerodoma", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajDolaskeAerodoma")
    @ResponseWrapper(localName = "dajDolaskeAerodomaResponse", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajDolaskeAerodomaResponse")
    @Action(input = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajDolaskeAerodomaRequest", output = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajDolaskeAerodomaResponse")
    public List<AvionLeti> dajDolaskeAerodoma(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<org.foi.nwtis.ihuzjak.ws.aerodromi.AvionLeti>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajPolaskeAerodoma", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajPolaskeAerodoma")
    @ResponseWrapper(localName = "dajPolaskeAerodomaResponse", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.aerodromi.DajPolaskeAerodomaResponse")
    @Action(input = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajPolaskeAerodomaRequest", output = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsAerodromi/dajPolaskeAerodomaResponse")
    public List<AvionLeti> dajPolaskeAerodoma(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

}