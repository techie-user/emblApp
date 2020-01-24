package org.embl.emblApp.repository;

import org.embl.emblApp.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
