
package org.foi.nwtis.ihuzjak.ws.meteo;

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
@WebService(name = "WsMeteo", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WsMeteo {


    /**
     * 
     * @param arg0
     * @return
     *     returns org.foi.nwtis.ihuzjak.ws.meteo.MeteoPodaci
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajMeteo", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.meteo.DajMeteo")
    @ResponseWrapper(localName = "dajMeteoResponse", targetNamespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", className = "org.foi.nwtis.ihuzjak.ws.meteo.DajMeteoResponse")
    @Action(input = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsMeteo/dajMeteoRequest", output = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/WsMeteo/dajMeteoResponse")
    public MeteoPodaci dajMeteo(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
