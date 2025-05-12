package org.tupachanga.tupachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tupachanga.tupachanga.entities.JoinRequest;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

  boolean existsBySenderIdAndMatchId(Long userId, Long matchId);
}
