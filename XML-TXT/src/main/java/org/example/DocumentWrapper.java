package org.example;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentWrapper {
    @XmlElement(name = "Invoice", namespace = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2")
    private List<Invoice> invoices;

    // Add getters and setters
    public List<Invoice> getInvoices() {
        return invoices;
    }


}