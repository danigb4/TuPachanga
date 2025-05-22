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

  public Match save(Match match) {
    return matchesRepository.save(match);
  }
}
