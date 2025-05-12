package org.tupachanga.tupachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.Sport;

import java.util.List;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {

  @Query(value = "select * from sports", nativeQuery = true)
  List<Sport> findAll();

}
