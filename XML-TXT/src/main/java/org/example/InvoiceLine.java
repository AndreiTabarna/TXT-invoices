package org.example;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "InvoiceLine", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceLine {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String ID;

    @XmlElement(name = "LineExtensionAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String LineExtensionAmount;

    @XmlElement(name = "Item", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Item item;

    @XmlElement(name = "Price", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private String Price;

    @XmlElement(name = "TaxableAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String TaxableAmount;

    @XmlElement(name = "InvoicedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private InvoicedQuantity invoicedQuantity;

    @XmlAttribute(name = "currencyID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String CurrencyID;

    @XmlElement(name = "TaxAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String TaxAmount;

    public Item getItem() {
        return item;
    }

    public InvoicedQuantity getInvoicedQuantity() {
        return invoicedQuantity;
    }

    public String getID() {
        return ID;
    }

    public String getLineExtensionAmount() {
        return LineExtensionAmount;
    }

    public String getPrice() {
        return Price;
    }

    public String getTaxableAmount() {
        return TaxableAmount;
    }

    public String getTaxAmount() {
        return TaxAmount;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }
}
