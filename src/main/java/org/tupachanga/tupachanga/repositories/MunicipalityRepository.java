package org.tupachanga.tupachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.Municipality;

import java.util.List;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

  List<Municipality> findAll();

  @Query("select m from Municipality m where m.province.id = :provinceId")
  List<Municipality> findByProvince(Long provinceId);
}
