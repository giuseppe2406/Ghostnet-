package de.giuseppe.ghostnet.repository;

import de.giuseppe.ghostnet.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
