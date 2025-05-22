package org.tupachanga.tupachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.Match;

import java.util.List;

@Repository
public interface MatchesRepository extends JpaRepository<Match, Long> {

  @Query(value = "select m from Match m where m.visible = true and m.endDate >= CURRENT_TIMESTAMP order by random() limit 5")
  List<Match> findActiveMacthes();

  @Query(value = "select m from Match m join m.facility f "
                                     + "join f.municipality mu "
                                     + "join mu.users u "
                                     + "join u.sports us "
                                     + "where u.id = :userId  and m.owner.id != :userId and m.sport.id = us.id")
  List<Match> findMatchesByUserMunicipalitiesAndSports(@Param("userId") Long userId);
}
