package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tupachanga.tupachanga.dtos.MatchWithCoordinatesDto;
import org.tupachanga.tupachanga.entities.Match;
import org.tupachanga.tupachanga.entities.User;
import org.tupachanga.tupachanga.repositories.MatchesRepository;
import org.tupachanga.tupachanga.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchesService {

  private final MatchesRepository matchesRepository;

  private final UserRepository userRepository;

  private final EmailService emailService;

  public List<Match> getActiveMatches() {
    return matchesRepository.findActiveMacthes();
  }

  public List<Match> getMatchesByUserMunicipalitiesAndSports(Long id) {
    return matchesRepository.findMatchesByUserMunicipalitiesAndSports(id);
  }

  public Optional<Match> getById(Long id) {
    return matchesRepository.findById(id);
  }

  public List<Match> getByOwnerId(String email){
    return matchesRepository.findByOwner_Email(email);
  }

  @Transactional
  public void createMatch(Match match) {

    Match savedMatch = matchesRepository.save(match);

    Long municipalityId = savedMatch.getFacility().getMunicipality().getId();
    String sportName = savedMatch.getSport().getName();
    String facilityName = savedMatch.getFacility().getName();
    String eventDate = savedMatch.getEventDate().toString();
    String description = savedMatch.getDescription();

    try {
      CompletableFuture.runAsync(() -> {
        try {

          List<User> users = userRepository.findByPreferenceMunicipality(municipalityId);

          for (User user : users) {
            String subject = "TuPachanga Notification";
            String body = String.format(
                "Hola %s,\n\nSe ha programado un nuevo partido en %s:\n\n" +
                    "Deporte: %s\n" +
                    "Fecha: %s\n" +
                    "Descripción: %s\n\n" +
                    "¡Te esperamos!",
                user.getFirstName(),
                facilityName,
                sportName,
                eventDate,
                description
            );

            emailService.sendEmail(user.getEmail(), subject, body);
          }
        } catch (Exception e) {
          log.error("Error en notificación asíncrona: " + e.getMessage());
        }
      });
    } catch (Exception e) {
      log.error("Error al iniciar notificación: " + e.getMessage());
    }
  }

  public List<MatchWithCoordinatesDto> getMatchesWithCoordinates() {
    return matchesRepository.findMatchesWithCoordinates(PageRequest.of(0,20));
  }

  public void save(Match match) {
    matchesRepository.save(match);
  }

  public void delete(Long id) {
    matchesRepository.deleteById(id);
  }
}
