package org.tupachanga.tupachanga.repositories;

import org.hibernate.mapping.Join;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.JoinRequest;
import org.tupachanga.tupachanga.entities.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

  boolean existsBySenderIdAndMatchId(Long userId, Long matchId);

  @Query(value = "select j from JoinRequest j join j.match m "
                                    + "where j.reciever.email = :email and "
                                    + "m.maxParticipants > 0 and "
                                    + "j.status = :status")
  List<JoinRequest> findByEmail(@Param("email") String email,  @Param("status") RequestStatus status);

  @Query(value = "select j from JoinRequest j join j.match m "
                                    + "where j.id = :id")
  Optional<JoinRequest> findById(@Param("id") Long id);
}
