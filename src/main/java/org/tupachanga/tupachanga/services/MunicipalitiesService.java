package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tupachanga.tupachanga.dtos.MunicipalityDto;
import org.tupachanga.tupachanga.entities.Municipality;
import org.tupachanga.tupachanga.repositories.MunicipalityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MunicipalitiesService {

  private final MunicipalityRepository municipalityRepository;

  public List<Municipality> getAll() {
    return municipalityRepository.findAll();
  }

  public List<Municipality> getByProvince(Long provinceId) {
    return municipalityRepository.findByProvince(provinceId);
  }

  public List<Municipality> getMunicipalitiesByIds(List<Long> municipalityIds) {
    return municipalityRepository.findAllById(municipalityIds);
  }

  public List<MunicipalityDto> getDtosByProvince(Long provinceId) {
    return municipalityRepository.findByProvince(provinceId).stream()
        .map(m -> new MunicipalityDto(m.getId(), m.getName()))
        .toList();
  }
}
