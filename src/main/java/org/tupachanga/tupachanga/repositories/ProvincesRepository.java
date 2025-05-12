package org.tupachanga.tupachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.Province;

import java.util.List;

@Repository
public interface ProvincesRepository extends JpaRepository<Province, Long> {

  List<Province> findAll();

}
