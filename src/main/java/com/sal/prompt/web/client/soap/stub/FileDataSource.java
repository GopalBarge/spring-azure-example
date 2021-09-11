//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.10 at 11:18:18 PM IST 
//


package com.sal.prompt.web.client.soap.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FileDataSource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FileDataSource"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dynamicDataSourcePath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="temporaryDataSource" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileDataSource", propOrder = {
    "dynamicDataSourcePath",
    "temporaryDataSource"
})
public class FileDataSource {

    @XmlElement(required = true, nillable = true)
    protected String dynamicDataSourcePath;
    protected boolean temporaryDataSource;

    /**
     * Gets the value of the dynamicDataSourcePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDynamicDataSourcePath() {
        return dynamicDataSourcePath;
    }

    /**
     * Sets the value of the dynamicDataSourcePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDynamicDataSourcePath(String value) {
        this.dynamicDataSourcePath = value;
    }

    /**
     * Gets the value of the temporaryDataSource property.
     * 
     */
    public boolean isTemporaryDataSource() {
        return temporaryDataSource;
    }

    /**
     * Sets the value of the temporaryDataSource property.
     * 
     */
    public void setTemporaryDataSource(boolean value) {
        this.temporaryDataSource = value;
    }

}
