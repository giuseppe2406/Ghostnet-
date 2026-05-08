package de.giuseppe.ghostnet.repository;

import de.giuseppe.ghostnet.model.Geisternetz;
import de.giuseppe.ghostnet.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GeisternetzRepository extends JpaRepository<Geisternetz, Long> {

    // alle Netze, deren Status in der Liste enthalten ist
    List<Geisternetz> findByStatusIn(List<Status> statusListe);
}
