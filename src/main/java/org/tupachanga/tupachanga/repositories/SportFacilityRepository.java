package org.tupachanga.tupachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.SportFacility;

import java.util.List;

@Repository
public interface SportFacilityRepository extends JpaRepository<SportFacility, Long> {

  @Query(value = "select s from SportFacility s join s.pictures p join s.municipality m")
  List<SportFacility> findAll();
  List<SportFacility> findByMunicipality_Id(Long municipalityId);
}
