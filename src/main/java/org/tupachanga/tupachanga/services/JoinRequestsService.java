package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tupachanga.tupachanga.entities.JoinRequest;
import org.tupachanga.tupachanga.entities.enums.RequestStatus;
import org.tupachanga.tupachanga.repositories.JoinRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinRequestsService {

  private final JoinRequestRepository joinRequestRepository;

  public void save(JoinRequest joinRequest) {
    joinRequestRepository.save(joinRequest);
  }

  public boolean alreadyExistsById(Long userId, Long matchId) {
    return joinRequestRepository.existsBySenderIdAndMatchId(userId, matchId);
  }

  public List<JoinRequest> getAllByEmail(String email) {
    return joinRequestRepository.findByEmail(email, RequestStatus.PENDING);
  }

  public Optional<JoinRequest> getById(Long id) {
    return joinRequestRepository.findById(id);
  }
}
