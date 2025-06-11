package org.tupachanga.tupachanga.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tupachanga.tupachanga.dtos.MatchWithCoordinatesDto;
import org.tupachanga.tupachanga.dtos.UpdateUserProfileDto;
import org.tupachanga.tupachanga.entities.JoinRequest;
import org.tupachanga.tupachanga.entities.Match;
import org.tupachanga.tupachanga.entities.Municipality;
import org.tupachanga.tupachanga.entities.Sport;
import org.tupachanga.tupachanga.entities.User;
import org.tupachanga.tupachanga.entities.enums.RequestStatus;
import org.tupachanga.tupachanga.services.JoinRequestsService;
import org.tupachanga.tupachanga.services.MatchesService;
import org.tupachanga.tupachanga.services.MunicipalitiesService;
import org.tupachanga.tupachanga.services.ProvincesService;
import org.tupachanga.tupachanga.services.SportsService;
import org.tupachanga.tupachanga.services.UsersService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UsersService usersService;

  private final MatchesService matchesService;

  private final JoinRequestsService joinRequestsService;

  private final MunicipalitiesService municipalitiesService;

  private final SportsService sportsService;

  private final ProvincesService provincesService;

  @Value("${file.upload-dir}")
  private String uploadPathDir;

  @GetMapping("/personalized-index")
  public String showPersonalizedIndex(Model model, Principal principal) {

    Optional<User> user = usersService.getByEmail(principal.getName());

    if (user.isPresent()) {

      List<Match> macthes = matchesService.getMatchesByUserMunicipalitiesAndSports(
          user.get().getId());

      List<MatchWithCoordinatesDto> matchesWithCoords = matchesService.getMatchesWithCoordinatesByUserMunicipalitiesAndSports(user.get()
          .getId());

      model.addAttribute("matches", macthes);
      model.addAttribute("user", user.get());
      model.addAttribute("matchesWithCoords", matchesWithCoords);
    }

    return "personalized-index";
  }

  @PostMapping("/contact-owner")
  public String contactOwner(
      @RequestParam Long matchId,
      @RequestParam String message,
      Principal principal,
      RedirectAttributes redirectAttributes) {

    Optional<Match> matchOpt = matchesService.getById(matchId);

    if (matchOpt.isEmpty()) {

      redirectAttributes.addFlashAttribute("error", "El evento no existe");
      return "redirect:/user/personalized-index";
    }

    Match match = matchOpt.get();

    User sender = usersService.getByEmail(principal.getName()).get();

    boolean alreadyExists = joinRequestsService.alreadyExistsById(sender.getId(), match.getId());

    if (alreadyExists) {

      redirectAttributes.addFlashAttribute("error",
          "Ya has enviado una solicitud para este evento.");
      return "redirect:/user/personalized-index";
    }

    JoinRequest newJoinRequest = new JoinRequest();
    newJoinRequest.setMatch(match);
    newJoinRequest.setSender(sender);
    newJoinRequest.setReciever(match.getOwner());
    newJoinRequest.setMessage(message);
    newJoinRequest.setStatus(RequestStatus.PENDING);

    joinRequestsService.save(newJoinRequest);

    return "redirect:/user/personalized-index?success";
  }

  @GetMapping("/profile")
  public String showProfile(
      Principal principal,
      Model model) {

    User user = usersService.getByEmail(principal.getName()).get();

    model.addAttribute("user", user);
    model.addAttribute("updateUserProfileDto", new UpdateUserProfileDto(user));
    model.addAttribute("sports", sportsService.getAll());
    model.addAttribute("provinces", provincesService.getAll());
    model.addAttribute("municipalities", municipalitiesService.getAll());

    return "user-profile";
  }

  @PostMapping("/update-profile")
  public String updateProfile(
      @ModelAttribute UpdateUserProfileDto dto,
      @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
      Principal principal,
      RedirectAttributes redirectAttributes) {

    try {

      User currentUser = usersService.getByEmail(principal.getName()).orElseThrow();

      if (dto.getFirstName() != null) {
        currentUser.setFirstName(dto.getFirstName());
      }
      if (dto.getLastName() != null) {
        currentUser.setLastName(dto.getLastName());
      }
      if (dto.getTelephone() != null) {
        currentUser.setTelephone(dto.getTelephone());
      }
      if (dto.getDescription() != null) {
        currentUser.setDescription(dto.getDescription());
      }

      if (avatarFile != null && !avatarFile.isEmpty()) {

        String avatarFileName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();

        Path uploadPath = Paths.get(uploadPathDir);
        if (!Files.exists(uploadPath)) {
          Files.createDirectories(uploadPath);
        }

        if (!"default-avatar.png".equals(currentUser.getAvatar())) {
          Path oldFilePath = uploadPath.resolve(currentUser.getAvatar());
          Files.deleteIfExists(oldFilePath);
        }

        Path filePath = uploadPath.resolve(avatarFileName);
        Files.copy(avatarFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        currentUser.setAvatar(avatarFileName);
      }

      if (dto.getMunicipalities() != null) {
        Set<Municipality> municipalities = new HashSet<>(
            municipalitiesService.getMunicipalitiesByIds(
                dto.getMunicipalities()));
        currentUser.setMunicipalities(municipalities);
      }

      if (dto.getSports() != null) {
        Set<Sport> sports = new HashSet<>(sportsService.getSportsByIds(dto.getSports()));
        currentUser.setSports(sports);
      }

      usersService.save(currentUser);

      redirectAttributes.addFlashAttribute("succes", "Perfil actualizado correctamente");
      return "redirect:/user/profile?success";

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error",
          "Error al actualizar perfil.");
      return "redirect:/user/profile";
    }
  }

  @GetMapping("/{uuid}")
  public String getUserProfile(
      @PathVariable UUID uuid,
      Model model) {

    User user = usersService.getByUuid(uuid).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND));

    model.addAttribute("user", user);

    return "user-profile-uuid";
  }

  @GetMapping("/request")
  public String showRequest(
      Principal principal,
      Model model
  ) {

    model.addAttribute("joinRequest", joinRequestsService.getAllByEmail(principal.getName()));

    return "show-joinRequest";
  }
}
