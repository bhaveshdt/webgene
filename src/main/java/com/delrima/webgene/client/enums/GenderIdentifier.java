package com.delrima.webgene.client.enums;

public enum GenderIdentifier {

    M("Male"), F("Female"), U("Unknown");

    private String description;

    private GenderIdentifier(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsMale() {
        return M == this;
    }
}
