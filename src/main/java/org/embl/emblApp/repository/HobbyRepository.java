package org.embl.emblApp.repository;

import org.embl.emblApp.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hobby entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {

}
