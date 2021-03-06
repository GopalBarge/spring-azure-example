//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.11 at 11:40:35 PM IST 
//


package com.sal.prompt.web.client.soap.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="reportName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="folderAbsolutePathURL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="templateFileName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="templateData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="XLIFFFileName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="XLIFFData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="updateFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reportName",
    "folderAbsolutePathURL",
    "templateFileName",
    "templateData",
    "xliffFileName",
    "xliffData",
    "updateFlag"
})
@XmlRootElement(name = "createReport")
public class CreateReport {

    @XmlElement(required = true)
    protected String reportName;
    @XmlElement(required = true)
    protected String folderAbsolutePathURL;
    @XmlElement(required = true)
    protected String templateFileName;
    @XmlElement(required = true)
    protected byte[] templateData;
    @XmlElement(name = "XLIFFFileName", required = true)
    protected String xliffFileName;
    @XmlElement(name = "XLIFFData", required = true)
    protected byte[] xliffData;
    protected boolean updateFlag;

    /**
     * Gets the value of the reportName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * Sets the value of the reportName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportName(String value) {
        this.reportName = value;
    }

    /**
     * Gets the value of the folderAbsolutePathURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolderAbsolutePathURL() {
        return folderAbsolutePathURL;
    }

    /**
     * Sets the value of the folderAbsolutePathURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolderAbsolutePathURL(String value) {
        this.folderAbsolutePathURL = value;
    }

    /**
     * Gets the value of the templateFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateFileName() {
        return templateFileName;
    }

    /**
     * Sets the value of the templateFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateFileName(String value) {
        this.templateFileName = value;
    }

    /**
     * Gets the value of the templateData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getTemplateData() {
        return templateData;
    }

    /**
     * Sets the value of the templateData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setTemplateData(byte[] value) {
        this.templateData = value;
    }

    /**
     * Gets the value of the xliffFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXLIFFFileName() {
        return xliffFileName;
    }

    /**
     * Sets the value of the xliffFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXLIFFFileName(String value) {
        this.xliffFileName = value;
    }

    /**
     * Gets the value of the xliffData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXLIFFData() {
        return xliffData;
    }

    /**
     * Sets the value of the xliffData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXLIFFData(byte[] value) {
        this.xliffData = value;
    }

    /**
     * Gets the value of the updateFlag property.
     * 
     */
    public boolean isUpdateFlag() {
        return updateFlag;
    }

    /**
     * Sets the value of the updateFlag property.
     * 
     */
    public void setUpdateFlag(boolean value) {
        this.updateFlag = value;
    }

}
