package org.tupachanga.tupachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(@Param("email") String email);

  Optional<User> findByUuid(UUID uuid);

  @Query("SELECT DISTINCT u FROM User u " +
      "JOIN u.municipalities m " +
      "JOIN Match ma ON ma.facility.municipality.id = m.id " +
      "WHERE ma.id = :matchId")
  List<User> findByPreferenceMunicipality(@Param("matchId") Long matchId);
}
