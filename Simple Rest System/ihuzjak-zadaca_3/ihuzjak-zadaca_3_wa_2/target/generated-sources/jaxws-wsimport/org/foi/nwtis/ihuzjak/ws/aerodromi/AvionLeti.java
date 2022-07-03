
package org.foi.nwtis.ihuzjak.ws.aerodromi;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for avionLeti complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="avionLeti"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="arrivalAirportCandidatesCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="callsign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="departureAirportCandidatesCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="estArrivalAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="estArrivalAirportHorizDistance" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="estArrivalAirportVertDistance" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="estDepartureAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="estDepartureAirportHorizDistance" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="estDepartureAirportVertDistance" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="firstSeen" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="icao24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastSeen" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "avionLeti", propOrder = {
    "arrivalAirportCandidatesCount",
    "callsign",
    "departureAirportCandidatesCount",
    "estArrivalAirport",
    "estArrivalAirportHorizDistance",
    "estArrivalAirportVertDistance",
    "estDepartureAirport",
    "estDepartureAirportHorizDistance",
    "estDepartureAirportVertDistance",
    "firstSeen",
    "icao24",
    "lastSeen"
})
public class AvionLeti {

    protected int arrivalAirportCandidatesCount;
    protected String callsign;
    protected int departureAirportCandidatesCount;
    protected String estArrivalAirport;
    protected int estArrivalAirportHorizDistance;
    protected int estArrivalAirportVertDistance;
    protected String estDepartureAirport;
    protected int estDepartureAirportHorizDistance;
    protected int estDepartureAirportVertDistance;
    protected int firstSeen;
    protected String icao24;
    protected int lastSeen;

    /**
     * Gets the value of the arrivalAirportCandidatesCount property.
     * 
     */
    public int getArrivalAirportCandidatesCount() {
        return arrivalAirportCandidatesCount;
    }

    /**
     * Sets the value of the arrivalAirportCandidatesCount property.
     * 
     */
    public void setArrivalAirportCandidatesCount(int value) {
        this.arrivalAirportCandidatesCount = value;
    }

    /**
     * Gets the value of the callsign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * Sets the value of the callsign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallsign(String value) {
        this.callsign = value;
    }

    /**
     * Gets the value of the departureAirportCandidatesCount property.
     * 
     */
    public int getDepartureAirportCandidatesCount() {
        return departureAirportCandidatesCount;
    }

    /**
     * Sets the value of the departureAirportCandidatesCount property.
     * 
     */
    public void setDepartureAirportCandidatesCount(int value) {
        this.departureAirportCandidatesCount = value;
    }

    /**
     * Gets the value of the estArrivalAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstArrivalAirport() {
        return estArrivalAirport;
    }

    /**
     * Sets the value of the estArrivalAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstArrivalAirport(String value) {
        this.estArrivalAirport = value;
    }

    /**
     * Gets the value of the estArrivalAirportHorizDistance property.
     * 
     */
    public int getEstArrivalAirportHorizDistance() {
        return estArrivalAirportHorizDistance;
    }

    /**
     * Sets the value of the estArrivalAirportHorizDistance property.
     * 
     */
    public void setEstArrivalAirportHorizDistance(int value) {
        this.estArrivalAirportHorizDistance = value;
    }

    /**
     * Gets the value of the estArrivalAirportVertDistance property.
     * 
     */
    public int getEstArrivalAirportVertDistance() {
        return estArrivalAirportVertDistance;
    }

    /**
     * Sets the value of the estArrivalAirportVertDistance property.
     * 
     */
    public void setEstArrivalAirportVertDistance(int value) {
        this.estArrivalAirportVertDistance = value;
    }

    /**
     * Gets the value of the estDepartureAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstDepartureAirport() {
        return estDepartureAirport;
    }

    /**
     * Sets the value of the estDepartureAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstDepartureAirport(String value) {
        this.estDepartureAirport = value;
    }

    /**
     * Gets the value of the estDepartureAirportHorizDistance property.
     * 
     */
    public int getEstDepartureAirportHorizDistance() {
        return estDepartureAirportHorizDistance;
    }

    /**
     * Sets the value of the estDepartureAirportHorizDistance property.
     * 
     */
    public void setEstDepartureAirportHorizDistance(int value) {
        this.estDepartureAirportHorizDistance = value;
    }

    /**
     * Gets the value of the estDepartureAirportVertDistance property.
     * 
     */
    public int getEstDepartureAirportVertDistance() {
        return estDepartureAirportVertDistance;
    }

    /**
     * Sets the value of the estDepartureAirportVertDistance property.
     * 
     */
    public void setEstDepartureAirportVertDistance(int value) {
        this.estDepartureAirportVertDistance = value;
    }

    /**
     * Gets the value of the firstSeen property.
     * 
     */
    public int getFirstSeen() {
        return firstSeen;
    }

    /**
     * Sets the value of the firstSeen property.
     * 
     */
    public void setFirstSeen(int value) {
        this.firstSeen = value;
    }

    /**
     * Gets the value of the icao24 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcao24() {
        return icao24;
    }

    /**
     * Sets the value of the icao24 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcao24(String value) {
        this.icao24 = value;
    }

    /**
     * Gets the value of the lastSeen property.
     * 
     */
    public int getLastSeen() {
        return lastSeen;
    }

    /**
     * Sets the value of the lastSeen property.
     * 
     */
    public void setLastSeen(int value) {
        this.lastSeen = value;
    }

}
