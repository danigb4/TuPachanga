package org.tupachanga.tupachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.SportFacility;

import java.util.List;

@Repository
public interface SportFacilityRepository extends JpaRepository<SportFacility, Long> {

  List<SportFacility> findByMunicipality_Id(Long municipalityId);
}
