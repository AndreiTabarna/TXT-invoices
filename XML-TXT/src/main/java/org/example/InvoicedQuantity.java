package org.example;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class InvoicedQuantity {
    @XmlValue
    private String value;

    @XmlAttribute(name = "unitCodeListID")
    private String unitCodeListID;

    @XmlAttribute(name = "unitCode")
    private String unitCode;

    // Getters for the fields...

    public String getValue() {
        return value;
    }

    public String getUnitCodeListID() {
        return unitCodeListID;
    }

    public String getUnitCode() {
        return unitCode;
    }
}
