package de.giuseppe.ghostnet;

import de.giuseppe.ghostnet.model.Geisternetz;
import de.giuseppe.ghostnet.model.Person;
import de.giuseppe.ghostnet.model.Status;
import de.giuseppe.ghostnet.repository.GeisternetzRepository;
import de.giuseppe.ghostnet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Legt beim ersten Start ein paar Beispieldaten an.
@Component
public class Beispieldaten implements CommandLineRunner {

    private final GeisternetzRepository geisternetzRepository;
    private final PersonRepository personRepository;

    @Autowired
    public Beispieldaten(GeisternetzRepository geisternetzRepository,
                         PersonRepository personRepository) {
        this.geisternetzRepository = geisternetzRepository;
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) {
        if (geisternetzRepository.count() > 0) {
            return;
        }

        Person p1 = new Person("Anna Mustermann", "0151 1111111");
        personRepository.save(p1);

        Person p2 = new Person("Bernd Schmidt", "0151 2222222");
        personRepository.save(p2);

        Person p3 = new Person("Clara Müller", "0151 3333333");
        personRepository.save(p3);

        Person p4 = new Person("Daniel Weber", "0151 4444444");
        personRepository.save(p4);

        // Netz 1: anonym gemeldet
        Geisternetz netz1 = new Geisternetz();
        netz1.setBreitengrad(54.3233);
        netz1.setLaengengrad(10.1228);
        netz1.setGeschaetzteGroesseKg(25.0);
        netz1.setStatus(Status.GEMELDET);
        netz1.setErstelltAm(LocalDateTime.now().minusDays(3));
        geisternetzRepository.save(netz1);

        // Netz 2: gemeldet von Anna
        Geisternetz netz2 = new Geisternetz();
        netz2.setBreitengrad(54.5189);
        netz2.setLaengengrad(13.6447);
        netz2.setGeschaetzteGroesseKg(40.0);
        netz2.setStatus(Status.GEMELDET);
        netz2.setMeldendePerson(p1);
        netz2.setErstelltAm(LocalDateTime.now().minusDays(2));
        geisternetzRepository.save(netz2);

        // Netz 3: Bergung bevorstehend durch Bernd
        Geisternetz netz3 = new Geisternetz();
        netz3.setBreitengrad(53.8654);
        netz3.setLaengengrad(8.7016);
        netz3.setGeschaetzteGroesseKg(60.0);
        netz3.setStatus(Status.BERGUNG_BEVORSTEHEND);
        netz3.setMeldendePerson(p1);
        netz3.setBergendePerson(p2);
        netz3.setErstelltAm(LocalDateTime.now().minusDays(1));
        geisternetzRepository.save(netz3);

        // Netz 4: schon geborgen von Clara
        Geisternetz netz4 = new Geisternetz();
        netz4.setBreitengrad(54.0865);
        netz4.setLaengengrad(12.1454);
        netz4.setGeschaetzteGroesseKg(15.0);
        netz4.setStatus(Status.GEBORGEN);
        netz4.setBergendePerson(p3);
        netz4.setErstelltAm(LocalDateTime.now().minusDays(5));
        geisternetzRepository.save(netz4);

        // Netz 5: als verschollen gemeldet von Daniel
        Geisternetz netz5 = new Geisternetz();
        netz5.setBreitengrad(54.7833);
        netz5.setLaengengrad(9.4333);
        netz5.setGeschaetzteGroesseKg(30.0);
        netz5.setStatus(Status.VERSCHOLLEN);
        netz5.setVerschollenMeldendePerson(p4);
        netz5.setErstelltAm(LocalDateTime.now().minusDays(7));
        geisternetzRepository.save(netz5);
    }
}
