package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tupachanga.tupachanga.entities.Province;
import org.tupachanga.tupachanga.repositories.ProvincesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvincesService {

  private final ProvincesRepository provincesRepository;

  public List<Province> getAll() {

    return provincesRepository.findAll();
  }
}
