package de.giuseppe.ghostnet.service;

import de.giuseppe.ghostnet.model.Geisternetz;
import de.giuseppe.ghostnet.model.Person;
import de.giuseppe.ghostnet.model.Status;
import de.giuseppe.ghostnet.repository.GeisternetzRepository;
import de.giuseppe.ghostnet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeisternetzService {

    private final GeisternetzRepository geisternetzRepository;
    private final PersonRepository personRepository;

    @Autowired
    public GeisternetzService(GeisternetzRepository geisternetzRepository,
                              PersonRepository personRepository) {
        this.geisternetzRepository = geisternetzRepository;
        this.personRepository = personRepository;
    }

    public List<Geisternetz> alleNetze() {
        return geisternetzRepository.findAll();
    }

    // Netze mit Status GEMELDET oder BERGUNG_BEVORSTEHEND
    public List<Geisternetz> nochZuBergendeNetze() {
        List<Status> statusListe = new ArrayList<>();
        statusListe.add(Status.GEMELDET);
        statusListe.add(Status.BERGUNG_BEVORSTEHEND);
        return geisternetzRepository.findByStatusIn(statusListe);
    }

    public Geisternetz findeNetz(Long id) {
        Optional<Geisternetz> optional = geisternetzRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    // User Story 1: neues Netz melden
    public void netzMelden(Double breitengrad,
                           Double laengengrad,
                           Double geschaetzteGroesseKg,
                           String meldenderName,
                           String meldenderTelefon) {
        Geisternetz netz = new Geisternetz();
        netz.setBreitengrad(breitengrad);
        netz.setLaengengrad(laengengrad);
        netz.setGeschaetzteGroesseKg(geschaetzteGroesseKg);
        netz.setStatus(Status.GEMELDET);
        netz.setErstelltAm(LocalDateTime.now());

        // Person nur anlegen, wenn ein Name angegeben wurde
        if (meldenderName != null && !meldenderName.isBlank()) {
            Person person = new Person(meldenderName, meldenderTelefon);
            personRepository.save(person);
            netz.setMeldendePerson(person);
        }

        geisternetzRepository.save(netz);
    }

    // User Story 2: Person trägt sich für die Bergung ein
    public void personFuerBergungEintragen(Long netzId, String name, String telefon) {
        Geisternetz netz = findeNetz(netzId);
        if (netz == null) {
            return;
        }
        // Eintragen nur erlaubt, wenn das Netz noch frei ist
        if (netz.getStatus() != Status.GEMELDET) {
            throw new IllegalStateException(
                    "Eintragen nicht möglich. Aktueller Status: "
                            + netz.getStatus().getAnzeigeText());
        }
        Person bergende = new Person(name, telefon);
        personRepository.save(bergende);
        netz.setBergendePerson(bergende);
        netz.setStatus(Status.BERGUNG_BEVORSTEHEND);
        geisternetzRepository.save(netz);
    }

    // User Story 4: Netz als geborgen melden
    public void alsGeborgenMelden(Long netzId) {
        Geisternetz netz = findeNetz(netzId);
        if (netz == null) {
            return;
        }
        // nur sinnvoll, wenn vorher jemand die Bergung übernommen hat
        if (netz.getStatus() != Status.BERGUNG_BEVORSTEHEND) {
            throw new IllegalStateException(
                    "Geborgen melden nicht möglich. Aktueller Status: "
                            + netz.getStatus().getAnzeigeText());
        }
        netz.setStatus(Status.GEBORGEN);
        geisternetzRepository.save(netz);
    }

    // User Story 7: Netz als verschollen melden (nicht anonym)
    public void alsVerschollenMelden(Long netzId, String name, String telefon) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name fehlt.");
        }
        if (telefon == null || telefon.isBlank()) {
            throw new IllegalArgumentException("Telefonnummer fehlt.");
        }

        Geisternetz netz = findeNetz(netzId);
        if (netz == null) {
            return;
        }
        // schon geborgen oder schon verschollen? dann abbrechen
        if (netz.getStatus() != Status.GEMELDET && netz.getStatus() != Status.BERGUNG_BEVORSTEHEND) {
            throw new IllegalStateException(
                    "Verschollen melden nicht möglich. Aktueller Status: "
                            + netz.getStatus().getAnzeigeText());
        }
        Person verschollMelder = new Person(name, telefon);
        personRepository.save(verschollMelder);

        netz.setVerschollenMeldendePerson(verschollMelder);
        netz.setStatus(Status.VERSCHOLLEN);
        geisternetzRepository.save(netz);
    }
}
