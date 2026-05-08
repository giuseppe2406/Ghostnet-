package de.giuseppe.ghostnet.model;

// Status eines Geisternetzes.
public enum Status {

    GEMELDET("Gemeldet"),
    BERGUNG_BEVORSTEHEND("Bergung bevorstehend"),
    GEBORGEN("Geborgen"),
    VERSCHOLLEN("Verschollen");

    private final String anzeigeText;

    Status(String anzeigeText) {
        this.anzeigeText = anzeigeText;
    }

    public String getAnzeigeText() {
        return anzeigeText;
    }
}
