package de.giuseppe.ghostnet.form;

import jakarta.validation.constraints.NotBlank;

// Formular-Objekt für "Für Bergung eintragen". Beide Felder Pflicht.
public class BergenForm {

    @NotBlank(message = "Bitte gib Deinen Namen an.")
    private String name;

    @NotBlank(message = "Bitte gib Deine Telefonnummer an.")
    private String telefonnummer;

    public BergenForm() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTelefonnummer() { return telefonnummer; }
    public void setTelefonnummer(String telefonnummer) { this.telefonnummer = telefonnummer; }
}
