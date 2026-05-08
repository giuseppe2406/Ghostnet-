package de.giuseppe.ghostnet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Geisternetz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double breitengrad;
    private Double laengengrad;
    private Double geschaetzteGroesseKg;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "meldende_person_id")
    private Person meldendePerson;

    @ManyToOne
    @JoinColumn(name = "bergende_person_id")
    private Person bergendePerson;

    @ManyToOne
    @JoinColumn(name = "verschollen_meldende_person_id")
    private Person verschollenMeldendePerson;

    private LocalDateTime erstelltAm;

    public Geisternetz() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBreitengrad() {
        return breitengrad;
    }

    public void setBreitengrad(Double breitengrad) {
        this.breitengrad = breitengrad;
    }

    public Double getLaengengrad() {
        return laengengrad;
    }

    public void setLaengengrad(Double laengengrad) {
        this.laengengrad = laengengrad;
    }

    public Double getGeschaetzteGroesseKg() {
        return geschaetzteGroesseKg;
    }

    public void setGeschaetzteGroesseKg(Double geschaetzteGroesseKg) {
        this.geschaetzteGroesseKg = geschaetzteGroesseKg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Person getMeldendePerson() {
        return meldendePerson;
    }

    public void setMeldendePerson(Person meldendePerson) {
        this.meldendePerson = meldendePerson;
    }

    public Person getBergendePerson() {
        return bergendePerson;
    }

    public void setBergendePerson(Person bergendePerson) {
        this.bergendePerson = bergendePerson;
    }

    public Person getVerschollenMeldendePerson() {
        return verschollenMeldendePerson;
    }

    public void setVerschollenMeldendePerson(Person verschollenMeldendePerson) {
        this.verschollenMeldendePerson = verschollenMeldendePerson;
    }

    public LocalDateTime getErstelltAm() {
        return erstelltAm;
    }

    public void setErstelltAm(LocalDateTime erstelltAm) {
        this.erstelltAm = erstelltAm;
    }
}
