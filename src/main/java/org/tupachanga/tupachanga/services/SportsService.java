package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tupachanga.tupachanga.entities.Sport;
import org.tupachanga.tupachanga.repositories.SportRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportsService {

  private final SportRepository sportRepository;

  public List<Sport> getAll() {
    return sportRepository.findAll();
  }

  public List<Sport> getSportsByIds(List<Long> sportIds) {
    return sportRepository.findAllById(sportIds);
  }

  public Sport getById(Long id) {
    return sportRepository.findById(id).orElse(null);
  }
}
