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
 * <p>Java class for TemplateFormatLabelValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TemplateFormatLabelValue"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="templateFormatLabel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="templateFormatValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TemplateFormatLabelValue", propOrder = {
    "templateFormatLabel",
    "templateFormatValue"
})
public class TemplateFormatLabelValue {

    @XmlElement(required = true, nillable = true)
    protected String templateFormatLabel;
    @XmlElement(required = true, nillable = true)
    protected String templateFormatValue;

    /**
     * Gets the value of the templateFormatLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateFormatLabel() {
        return templateFormatLabel;
    }

    /**
     * Sets the value of the templateFormatLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateFormatLabel(String value) {
        this.templateFormatLabel = value;
    }

    /**
     * Gets the value of the templateFormatValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateFormatValue() {
        return templateFormatValue;
    }

    /**
     * Sets the value of the templateFormatValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateFormatValue(String value) {
        this.templateFormatValue = value;
    }

}
