package org.example;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Invoice", namespace = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2")
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice {
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String ID;

    @XmlElement(name = "IssueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String IssueDate;


    @XmlElement(name = "InvoiceLine", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private List<InvoiceLine> InvoiceLines;

    @XmlElement(name = "DocumentCurrencyCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String DocumentCurrencyCode;

    public String getID() {
        return ID;
    }


    public String getIssueDate() {
        return IssueDate;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return InvoiceLines;
    }

    public String getDocumentCurrencyCode() {
        return DocumentCurrencyCode;
    }
}