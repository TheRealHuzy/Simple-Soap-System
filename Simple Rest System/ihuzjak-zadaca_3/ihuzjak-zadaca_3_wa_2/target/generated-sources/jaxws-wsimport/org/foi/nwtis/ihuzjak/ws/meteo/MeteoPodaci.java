
package org.foi.nwtis.ihuzjak.ws.meteo;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for meteoPodaci complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meteoPodaci"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cloudsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cloudsValue" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="humidityUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="humidityValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="lastUpdate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="precipitationMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="precipitationUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="precipitationValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="pressureUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pressureValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="sunRise" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="sunSet" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="temperatureMax" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="temperatureMin" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="temperatureUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="temperatureValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="visibility" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="weatherIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="weatherNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="weatherValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="windDirectionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="windDirectionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="windDirectionValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="windSpeedName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="windSpeedValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meteoPodaci", propOrder = {
    "cloudsName",
    "cloudsValue",
    "humidityUnit",
    "humidityValue",
    "lastUpdate",
    "precipitationMode",
    "precipitationUnit",
    "precipitationValue",
    "pressureUnit",
    "pressureValue",
    "sunRise",
    "sunSet",
    "temperatureMax",
    "temperatureMin",
    "temperatureUnit",
    "temperatureValue",
    "visibility",
    "weatherIcon",
    "weatherNumber",
    "weatherValue",
    "windDirectionCode",
    "windDirectionName",
    "windDirectionValue",
    "windSpeedName",
    "windSpeedValue"
})
public class MeteoPodaci {

    protected String cloudsName;
    protected int cloudsValue;
    protected String humidityUnit;
    protected Float humidityValue;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdate;
    protected String precipitationMode;
    protected String precipitationUnit;
    protected Float precipitationValue;
    protected String pressureUnit;
    protected Float pressureValue;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sunRise;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sunSet;
    protected Float temperatureMax;
    protected Float temperatureMin;
    protected String temperatureUnit;
    protected Float temperatureValue;
    protected String visibility;
    protected String weatherIcon;
    protected int weatherNumber;
    protected String weatherValue;
    protected String windDirectionCode;
    protected String windDirectionName;
    protected Float windDirectionValue;
    protected String windSpeedName;
    protected Float windSpeedValue;

    /**
     * Gets the value of the cloudsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCloudsName() {
        return cloudsName;
    }

    /**
     * Sets the value of the cloudsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCloudsName(String value) {
        this.cloudsName = value;
    }

    /**
     * Gets the value of the cloudsValue property.
     * 
     */
    public int getCloudsValue() {
        return cloudsValue;
    }

    /**
     * Sets the value of the cloudsValue property.
     * 
     */
    public void setCloudsValue(int value) {
        this.cloudsValue = value;
    }

    /**
     * Gets the value of the humidityUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHumidityUnit() {
        return humidityUnit;
    }

    /**
     * Sets the value of the humidityUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHumidityUnit(String value) {
        this.humidityUnit = value;
    }

    /**
     * Gets the value of the humidityValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getHumidityValue() {
        return humidityValue;
    }

    /**
     * Sets the value of the humidityValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setHumidityValue(Float value) {
        this.humidityValue = value;
    }

    /**
     * Gets the value of the lastUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the value of the lastUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdate(XMLGregorianCalendar value) {
        this.lastUpdate = value;
    }

    /**
     * Gets the value of the precipitationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrecipitationMode() {
        return precipitationMode;
    }

    /**
     * Sets the value of the precipitationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrecipitationMode(String value) {
        this.precipitationMode = value;
    }

    /**
     * Gets the value of the precipitationUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrecipitationUnit() {
        return precipitationUnit;
    }

    /**
     * Sets the value of the precipitationUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrecipitationUnit(String value) {
        this.precipitationUnit = value;
    }

    /**
     * Gets the value of the precipitationValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPrecipitationValue() {
        return precipitationValue;
    }

    /**
     * Sets the value of the precipitationValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPrecipitationValue(Float value) {
        this.precipitationValue = value;
    }

    /**
     * Gets the value of the pressureUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPressureUnit() {
        return pressureUnit;
    }

    /**
     * Sets the value of the pressureUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPressureUnit(String value) {
        this.pressureUnit = value;
    }

    /**
     * Gets the value of the pressureValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPressureValue() {
        return pressureValue;
    }

    /**
     * Sets the value of the pressureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPressureValue(Float value) {
        this.pressureValue = value;
    }

    /**
     * Gets the value of the sunRise property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSunRise() {
        return sunRise;
    }

    /**
     * Sets the value of the sunRise property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSunRise(XMLGregorianCalendar value) {
        this.sunRise = value;
    }

    /**
     * Gets the value of the sunSet property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSunSet() {
        return sunSet;
    }

    /**
     * Sets the value of the sunSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSunSet(XMLGregorianCalendar value) {
        this.sunSet = value;
    }

    /**
     * Gets the value of the temperatureMax property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTemperatureMax() {
        return temperatureMax;
    }

    /**
     * Sets the value of the temperatureMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTemperatureMax(Float value) {
        this.temperatureMax = value;
    }

    /**
     * Gets the value of the temperatureMin property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTemperatureMin() {
        return temperatureMin;
    }

    /**
     * Sets the value of the temperatureMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTemperatureMin(Float value) {
        this.temperatureMin = value;
    }

    /**
     * Gets the value of the temperatureUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    /**
     * Sets the value of the temperatureUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemperatureUnit(String value) {
        this.temperatureUnit = value;
    }

    /**
     * Gets the value of the temperatureValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTemperatureValue() {
        return temperatureValue;
    }

    /**
     * Sets the value of the temperatureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTemperatureValue(Float value) {
        this.temperatureValue = value;
    }

    /**
     * Gets the value of the visibility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * Sets the value of the visibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisibility(String value) {
        this.visibility = value;
    }

    /**
     * Gets the value of the weatherIcon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeatherIcon() {
        return weatherIcon;
    }

    /**
     * Sets the value of the weatherIcon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeatherIcon(String value) {
        this.weatherIcon = value;
    }

    /**
     * Gets the value of the weatherNumber property.
     * 
     */
    public int getWeatherNumber() {
        return weatherNumber;
    }

    /**
     * Sets the value of the weatherNumber property.
     * 
     */
    public void setWeatherNumber(int value) {
        this.weatherNumber = value;
    }

    /**
     * Gets the value of the weatherValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeatherValue() {
        return weatherValue;
    }

    /**
     * Sets the value of the weatherValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeatherValue(String value) {
        this.weatherValue = value;
    }

    /**
     * Gets the value of the windDirectionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWindDirectionCode() {
        return windDirectionCode;
    }

    /**
     * Sets the value of the windDirectionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWindDirectionCode(String value) {
        this.windDirectionCode = value;
    }

    /**
     * Gets the value of the windDirectionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWindDirectionName() {
        return windDirectionName;
    }

    /**
     * Sets the value of the windDirectionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWindDirectionName(String value) {
        this.windDirectionName = value;
    }

    /**
     * Gets the value of the windDirectionValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getWindDirectionValue() {
        return windDirectionValue;
    }

    /**
     * Sets the value of the windDirectionValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setWindDirectionValue(Float value) {
        this.windDirectionValue = value;
    }

    /**
     * Gets the value of the windSpeedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWindSpeedName() {
        return windSpeedName;
    }

    /**
     * Sets the value of the windSpeedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWindSpeedName(String value) {
        this.windSpeedName = value;
    }

    /**
     * Gets the value of the windSpeedValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getWindSpeedValue() {
        return windSpeedValue;
    }

    /**
     * Sets the value of the windSpeedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setWindSpeedValue(Float value) {
        this.windSpeedValue = value;
    }

}
