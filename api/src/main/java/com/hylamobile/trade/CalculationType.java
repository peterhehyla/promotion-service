package com.hylamobile.trade;

public enum CalculationType {
    PERCENTAGE("label.percentage"), ADDITION("label.addition"), FLAT("label.fixed"), MINIMUM("label.minimum"),
    FLOOR("label.floor"), MAXIMUM("label.maximum");

    private String displayKey;


    /**
     *
     */
    private CalculationType(String description) {
        this.displayKey = description;
    }

    public String getDisplayKey() {
        return displayKey;
    }

}
