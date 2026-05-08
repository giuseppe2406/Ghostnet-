package de.giuseppe.ghostnet.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Formular-Objekt für "Netz melden". Nicht in der DB.
public class GeisternetzForm {

    @NotNull(message = "Bitte gib einen Breitengrad an.")
    @DecimalMin(value = "-90.0", message = "Breitengrad muss zwischen -90 und 90 liegen.")
    @DecimalMax(value = "90.0", message = "Breitengrad muss zwischen -90 und 90 liegen.")
    private Double breitengrad;

    @NotNull(message = "Bitte gib einen Längengrad an.")
    @DecimalMin(value = "-180.0", message = "Längengrad muss zwischen -180 und 180 liegen.")
    @DecimalMax(value = "180.0", message = "Längengrad muss zwischen -180 und 180 liegen.")
    private Double laengengrad;

    @NotNull(message = "Bitte gib eine geschätzte Größe an.")
    @Positive(message = "Die Größe muss größer als 0 sein.")
    private Double geschaetzteGroesseKg;

    // optional, anonyme Meldung möglich
    private String name;
    private String telefonnummer;

    public GeisternetzForm() {
    }

    // Regel: Name und Telefonnummer beide leer oder beide ausgefüllt
    @AssertTrue(message = "Wenn Du Name angibst, gib bitte auch Telefonnummer an (oder lass beide leer).")
    public boolean isKontaktDatenVollstaendig() {
        boolean nameLeer = (name == null || name.isBlank());
        boolean telefonLeer = (telefonnummer == null || telefonnummer.isBlank());
        return nameLeer == telefonLeer;
    }

    public Double getBreitengrad() { return breitengrad; }
    public void setBreitengrad(Double breitengrad) { this.breitengrad = breitengrad; }

    public Double getLaengengrad() { return laengengrad; }
    public void setLaengengrad(Double laengengrad) { this.laengengrad = laengengrad; }

    public Double getGeschaetzteGroesseKg() { return geschaetzteGroesseKg; }
    public void setGeschaetzteGroesseKg(Double geschaetzteGroesseKg) { this.geschaetzteGroesseKg = geschaetzteGroesseKg; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTelefonnummer() { return telefonnummer; }
    public void setTelefonnummer(String telefonnummer) { this.telefonnummer = telefonnummer; }
}
