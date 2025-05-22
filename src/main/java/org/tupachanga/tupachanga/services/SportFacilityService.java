package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tupachanga.tupachanga.dtos.SportFacilityDto;
import org.tupachanga.tupachanga.entities.SportFacility;
import org.tupachanga.tupachanga.repositories.SportFacilityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportFacilityService {

  private final SportFacilityRepository sportFacilityRepository;

  public List<SportFacilityDto> getDtosByMunicipalityId(Long municipalityId) {
    return sportFacilityRepository.findByMunicipality_Id(municipalityId).stream()
        .map(m -> new SportFacilityDto(m.getId(), m.getName()))
        .toList();
  }

  public SportFacility getById(Long id) {
    return sportFacilityRepository.findById(id).orElse(null);
  }
}
