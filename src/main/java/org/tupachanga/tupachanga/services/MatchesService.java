package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tupachanga.tupachanga.entities.Match;
import org.tupachanga.tupachanga.repositories.MatchesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchesService {

  private final MatchesRepository matchesRepository;

  public List<Match> getActiveMatches() {
    return matchesRepository.findActiveMacthes();
  }

  public List<Match> getMatchesByUserMunicipalitiesAndSports(Long id) {
    return matchesRepository.findMatchesByUserMunicipalitiesAndSports(id);
  }

  public Optional<Match> getById(Long id) {
    return matchesRepository.findById(id);
  }

// --- SIMULA UN TRIGGER -> AÑADE COMO PARTICIPANTE EN EL EVENTO AL DUEÑO DEL POST ----
/*public Match createMatch(Match match, Long ownerId) {
  User owner = userRepository.findById(ownerId)
      .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

  match.setOwner(owner);
  match.getParticipants().add(owner); // Aquí se añade al dueño como participante

  match.setCreatedAt(LocalDateTime.now());
  match.setUpdatedAt(LocalDateTime.now());

  return matchRepository.save(match); // Se guarda todo, incluida la relación
}*/
}
