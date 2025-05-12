package org.tupachanga.tupachanga.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tupachanga.tupachanga.dtos.MunicipalityDto;
import org.tupachanga.tupachanga.dtos.NewUserDto;
import org.tupachanga.tupachanga.entities.JoinRequest;
import org.tupachanga.tupachanga.entities.Match;
import org.tupachanga.tupachanga.entities.Municipality;
import org.tupachanga.tupachanga.entities.Province;
import org.tupachanga.tupachanga.entities.Sport;
import org.tupachanga.tupachanga.entities.User;
import org.tupachanga.tupachanga.entities.enums.RequestStatus;
import org.tupachanga.tupachanga.entities.enums.Role;
import org.tupachanga.tupachanga.services.JoinRequestsService;
import org.tupachanga.tupachanga.services.MatchesService;
import org.tupachanga.tupachanga.services.MunicipalitiesService;
import org.tupachanga.tupachanga.services.ProvincesService;
import org.tupachanga.tupachanga.services.SportsService;
import org.tupachanga.tupachanga.services.UsersService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")

public class HomeController {

  private final MatchesService matchesService;

  private final SportsService sportsService;

  private final MunicipalitiesService municipalitiesService;

  private final ProvincesService provincesService;

  private final UsersService usersService;

  private final JoinRequestsService joinRequestsService;

  private final PasswordEncoder passwordEncoder;

  @Value("${file.upload-dir}")
  private String uploadPathDir;

  @GetMapping
  public String home(Model model) {

    List<Match> randomMatches = matchesService.getActiveMatches();
    List<Sport> sports = sportsService.getAll();

    model.addAttribute("matches", randomMatches);
    model.addAttribute("sports", sports);

    return "index";
  }

  @GetMapping("/login")
  public String showLogin(@RequestParam(value = "error", required = false) String error,
      Model model) {

    if (error != null) {
      model.addAttribute("loginError", true);
    }

    return "login";
  }

  @GetMapping("/register")
  public String showRegister(Model model) {

    List<Province> provinces = provincesService.getAll();

    model.addAttribute("newUserDto", new NewUserDto());
    model.addAttribute("provinces", provinces);

    return "register";
  }

  @PostMapping("/register")
  public String processRegister(
      @ModelAttribute NewUserDto dto,
      @RequestParam("avatarFile") MultipartFile avatarFile) {

    try {

      String avatarFileName;

      if (!avatarFile.isEmpty()) {

        avatarFileName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();

        Path uploadPath = Paths.get(uploadPathDir);
        if (!Files.exists(uploadPath)) {
          Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(avatarFileName);
        Files.copy(avatarFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      } else {

        avatarFileName = "default-avatar.png";
      }

      User newUser = new User();
      newUser.setFirstName(dto.getFirstName());
      newUser.setLastName(dto.getLastName());
      newUser.setEmail(dto.getEmail());
      newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
      newUser.setTelephone(dto.getTelephone());
      newUser.setDescription(dto.getDescription());
      newUser.setAvatar(avatarFileName);
      newUser.setRole(Role.USER);

      List<Municipality> municipalities =
          municipalitiesService.getMunicipalitiesByIds(dto.getMunicipalities());
      newUser.setMunicipalities(new HashSet<>(municipalities));

      usersService.save(newUser);

      return "redirect:/login";

    } catch (IOException e) {

      return "redirect:/error";
    }
  }

  @GetMapping("/municipalities/by-province")
  @ResponseBody
  public List<MunicipalityDto> getMunicipalitiesByProvince(
      @RequestParam Long provinceId) {

    return municipalitiesService.getDtosByProvince(provinceId);
  }

  @GetMapping("/personalized-index")
  public String showPersonalizedIndex(Model model, Principal principal) {

    Optional<User> user = usersService.getByEmail(principal.getName());

    if (user.isPresent()) {

      List<Match> macthes = matchesService.getMatchesByUserMunicipalities(user.get().getId());
      model.addAttribute("matches", macthes);
      model.addAttribute("user", user.get());
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
      return "redirect:/personalized-index";
    }

    Match match = matchOpt.get();

    User sender = usersService.getByEmail(principal.getName()).get();

    boolean alreadyExists = joinRequestsService.alreadyExistsById(sender.getId(), match.getId());

    if (alreadyExists) {

      redirectAttributes.addFlashAttribute("error",
          "Ya has enviado una solicitud para este evento.");
      return "redirect:/personalized-index";
    }

    JoinRequest newJoinRequest = new JoinRequest();
    newJoinRequest.setMatch(match);
    newJoinRequest.setSender(sender);
    newJoinRequest.setMessage(message);
    newJoinRequest.setStatus(RequestStatus.PENDING);

    joinRequestsService.save(newJoinRequest);

    return "redirect:/personalized-index?success";
  }
}

