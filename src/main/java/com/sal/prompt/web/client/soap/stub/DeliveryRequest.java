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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeliveryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeliveryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="documentData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="dynamicDataSource" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}BIPDataSource"/&gt;
 *         &lt;element name="emailOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}EMailDeliveryOption"/&gt;
 *         &lt;element name="faxOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}FaxDeliveryOption"/&gt;
 *         &lt;element name="ftpOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}FTPDeliveryOption"/&gt;
 *         &lt;element name="localOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}LocalDeliveryOption"/&gt;
 *         &lt;element name="printOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}PrintDeliveryOption"/&gt;
 *         &lt;element name="webDAVOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}WebDAVDeliveryOption"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryRequest", propOrder = {
    "contentType",
    "documentData",
    "dynamicDataSource",
    "emailOption",
    "faxOption",
    "ftpOption",
    "localOption",
    "printOption",
    "webDAVOption"
})
public class DeliveryRequest {

    @XmlElement(required = true, nillable = true)
    protected String contentType;
    @XmlElement(required = true, nillable = true)
    protected byte[] documentData;
    @XmlElement(required = true, nillable = true)
    protected BIPDataSource dynamicDataSource;
    @XmlElement(required = true, nillable = true)
    protected EMailDeliveryOption emailOption;
    @XmlElement(required = true, nillable = true)
    protected FaxDeliveryOption faxOption;
    @XmlElement(required = true, nillable = true)
    protected FTPDeliveryOption ftpOption;
    @XmlElement(required = true, nillable = true)
    protected LocalDeliveryOption localOption;
    @XmlElement(required = true, nillable = true)
    protected PrintDeliveryOption printOption;
    @XmlElement(required = true, nillable = true)
    protected WebDAVDeliveryOption webDAVOption;

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the documentData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumentData() {
        return documentData;
    }

    /**
     * Sets the value of the documentData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumentData(byte[] value) {
        this.documentData = value;
    }

    /**
     * Gets the value of the dynamicDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link BIPDataSource }
     *     
     */
    public BIPDataSource getDynamicDataSource() {
        return dynamicDataSource;
    }

    /**
     * Sets the value of the dynamicDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link BIPDataSource }
     *     
     */
    public void setDynamicDataSource(BIPDataSource value) {
        this.dynamicDataSource = value;
    }

    /**
     * Gets the value of the emailOption property.
     * 
     * @return
     *     possible object is
     *     {@link EMailDeliveryOption }
     *     
     */
    public EMailDeliveryOption getEmailOption() {
        return emailOption;
    }

    /**
     * Sets the value of the emailOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link EMailDeliveryOption }
     *     
     */
    public void setEmailOption(EMailDeliveryOption value) {
        this.emailOption = value;
    }

    /**
     * Gets the value of the faxOption property.
     * 
     * @return
     *     possible object is
     *     {@link FaxDeliveryOption }
     *     
     */
    public FaxDeliveryOption getFaxOption() {
        return faxOption;
    }

    /**
     * Sets the value of the faxOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaxDeliveryOption }
     *     
     */
    public void setFaxOption(FaxDeliveryOption value) {
        this.faxOption = value;
    }

    /**
     * Gets the value of the ftpOption property.
     * 
     * @return
     *     possible object is
     *     {@link FTPDeliveryOption }
     *     
     */
    public FTPDeliveryOption getFtpOption() {
        return ftpOption;
    }

    /**
     * Sets the value of the ftpOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link FTPDeliveryOption }
     *     
     */
    public void setFtpOption(FTPDeliveryOption value) {
        this.ftpOption = value;
    }

    /**
     * Gets the value of the localOption property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDeliveryOption }
     *     
     */
    public LocalDeliveryOption getLocalOption() {
        return localOption;
    }

    /**
     * Sets the value of the localOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDeliveryOption }
     *     
     */
    public void setLocalOption(LocalDeliveryOption value) {
        this.localOption = value;
    }

    /**
     * Gets the value of the printOption property.
     * 
     * @return
     *     possible object is
     *     {@link PrintDeliveryOption }
     *     
     */
    public PrintDeliveryOption getPrintOption() {
        return printOption;
    }

    /**
     * Sets the value of the printOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrintDeliveryOption }
     *     
     */
    public void setPrintOption(PrintDeliveryOption value) {
        this.printOption = value;
    }

    /**
     * Gets the value of the webDAVOption property.
     * 
     * @return
     *     possible object is
     *     {@link WebDAVDeliveryOption }
     *     
     */
    public WebDAVDeliveryOption getWebDAVOption() {
        return webDAVOption;
    }

    /**
     * Sets the value of the webDAVOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebDAVDeliveryOption }
     *     
     */
    public void setWebDAVOption(WebDAVDeliveryOption value) {
        this.webDAVOption = value;
    }

}
