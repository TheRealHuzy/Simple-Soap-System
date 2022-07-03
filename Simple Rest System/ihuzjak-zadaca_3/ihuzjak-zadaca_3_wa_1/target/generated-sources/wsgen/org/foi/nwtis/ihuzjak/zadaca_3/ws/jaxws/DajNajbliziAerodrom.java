
package org.foi.nwtis.ihuzjak.zadaca_3.ws.jaxws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.foi.nwtis.rest.podaci.Lokacija;

@XmlRootElement(name = "dajNajbliziAerodrom", namespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dajNajbliziAerodrom", namespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", propOrder = {
    "arg0",
    "arg1"
})
public class DajNajbliziAerodrom {

    @XmlElement(name = "arg0", namespace = "")
    private Lokacija arg0;
    @XmlElement(name = "arg1", namespace = "")
    private boolean arg1;

    /**
     * 
     * @return
     *     returns Lokacija
     */
    public Lokacija getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(Lokacija arg0) {
        this.arg0 = arg0;
    }

    /**
     * 
     * @return
     *     returns boolean
     */
    public boolean isArg1() {
        return this.arg1;
    }

    /**
     * 
     * @param arg1
     *     the value for the arg1 property
     */
    public void setArg1(boolean arg1) {
        this.arg1 = arg1;
    }

}
