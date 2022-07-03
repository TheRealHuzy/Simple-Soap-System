
package org.foi.nwtis.ihuzjak.ws.meteo;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.foi.nwtis.ihuzjak.ws.meteo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DajMeteo_QNAME = new QName("http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", "dajMeteo");
    private final static QName _DajMeteoResponse_QNAME = new QName("http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", "dajMeteoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.foi.nwtis.ihuzjak.ws.meteo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DajMeteo }
     * 
     */
    public DajMeteo createDajMeteo() {
        return new DajMeteo();
    }

    /**
     * Create an instance of {@link DajMeteoResponse }
     * 
     */
    public DajMeteoResponse createDajMeteoResponse() {
        return new DajMeteoResponse();
    }

    /**
     * Create an instance of {@link MeteoPodaci }
     * 
     */
    public MeteoPodaci createMeteoPodaci() {
        return new MeteoPodaci();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajMeteo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DajMeteo }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", name = "dajMeteo")
    public JAXBElement<DajMeteo> createDajMeteo(DajMeteo value) {
        return new JAXBElement<DajMeteo>(_DajMeteo_QNAME, DajMeteo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajMeteoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DajMeteoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.zadaca_3.ihuzjak.nwtis.foi.org/", name = "dajMeteoResponse")
    public JAXBElement<DajMeteoResponse> createDajMeteoResponse(DajMeteoResponse value) {
        return new JAXBElement<DajMeteoResponse>(_DajMeteoResponse_QNAME, DajMeteoResponse.class, null, value);
    }

}
