
package org.foi.nwtis.ihuzjak.zadaca_3.ws.jaxws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.foi.nwtis.podaci.Aerodrom;

@XmlRootElement(name = "dajNajbliziAerodromResponse", namespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dajNajbliziAerodromResponse", namespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/")
public class DajNajbliziAerodromResponse {

    @XmlElement(name = "return", namespace = "")
    private Aerodrom _return;

    /**
     * 
     * @return
     *     returns Aerodrom
     */
    public Aerodrom getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Aerodrom _return) {
        this._return = _return;
    }

}
