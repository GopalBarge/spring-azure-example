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
 * <p>Java class for BIPDataSource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BIPDataSource"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="JDBCDataSource" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}JDBCDataSource"/&gt;
 *         &lt;element name="fileDataSource" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}FileDataSource"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BIPDataSource", propOrder = {
    "jdbcDataSource",
    "fileDataSource"
})
public class BIPDataSource {

    @XmlElement(name = "JDBCDataSource", required = true, nillable = true)
    protected JDBCDataSource jdbcDataSource;
    @XmlElement(required = true, nillable = true)
    protected FileDataSource fileDataSource;

    /**
     * Gets the value of the jdbcDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link JDBCDataSource }
     *     
     */
    public JDBCDataSource getJDBCDataSource() {
        return jdbcDataSource;
    }

    /**
     * Sets the value of the jdbcDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link JDBCDataSource }
     *     
     */
    public void setJDBCDataSource(JDBCDataSource value) {
        this.jdbcDataSource = value;
    }

    /**
     * Gets the value of the fileDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link FileDataSource }
     *     
     */
    public FileDataSource getFileDataSource() {
        return fileDataSource;
    }

    /**
     * Sets the value of the fileDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileDataSource }
     *     
     */
    public void setFileDataSource(FileDataSource value) {
        this.fileDataSource = value;
    }

}
