package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tupachanga.tupachanga.entities.SportFacility;
import org.tupachanga.tupachanga.entities.User;
import org.tupachanga.tupachanga.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UsersService {

  private final UserRepository userRepository;

  public void save(User user) {
    userRepository.save(user);
  }

  public Optional<User> getByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Optional<User> getByUuid(UUID uuid) {
    return userRepository.findByUuid(uuid);
  }

  public List<User> getByPreferenceMunicipality(Long id) {
    return userRepository.findByPreferenceMunicipality(id);
  }
}
