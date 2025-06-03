package org.tupachanga.tupachanga.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tupachanga.tupachanga.dtos.MatchUpdateDto;
import org.tupachanga.tupachanga.dtos.NewMatchDto;
import org.tupachanga.tupachanga.dtos.SportFacilityDto;
import org.tupachanga.tupachanga.entities.Match;
import org.tupachanga.tupachanga.entities.Sport;
import org.tupachanga.tupachanga.entities.SportFacility;
import org.tupachanga.tupachanga.entities.User;
import org.tupachanga.tupachanga.entities.enums.SkillLevel;
import org.tupachanga.tupachanga.services.MatchesService;
import org.tupachanga.tupachanga.services.SportFacilityService;
import org.tupachanga.tupachanga.services.SportsService;
import org.tupachanga.tupachanga.services.UsersService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

  private final UsersService usersService;

  private final SportFacilityService sportFacilityService;

  private final SportsService sportsService;

  private final MatchesService matchesService;

  @GetMapping("/new-match")
  public String showCreateMatch(
      Model model,
      Principal principal
  ) {

    Optional<User> user = usersService.getByEmail(principal.getName());

    model.addAttribute("user", user.get());
    model.addAttribute("newMatchDto", new NewMatchDto());
    model.addAttribute("skillLevel", SkillLevel.values());

    return "new-match";
  }

  @GetMapping("/sport-facilities/by-municipality")
  @ResponseBody
  public List<SportFacilityDto> getSportFacilitiesByMunicipality(
      @RequestParam Long idMunicipality
  ) {

    return sportFacilityService.getDtosByMunicipalityId(idMunicipality);
  }

  @PostMapping("/match-creation")
  public String matchCreation(
      @ModelAttribute NewMatchDto dto,
      RedirectAttributes redirectAttributes,
      Principal principal
  ) {

    try {

      Match match = new Match();

      match.setTitle(dto.getTitle());
      match.setDescription(dto.getDescription());

      Sport sport = sportsService.getById(dto.getIdSport());
      match.setSport(sport);

      SportFacility sportFacility = sportFacilityService.getById(dto.getIdSportFacility());
      match.setFacility(sportFacility);

      match.setEventDate(dto.getEventDate());
      match.setEndDate(dto.getEndDate());
      match.setPricePerPerson(dto.getPricePerPerson());
      match.setMaxParticipants(dto.getMaxParticipants());
      match.setSkillLevel(dto.getSkillLevel());

      Optional<User> owner = usersService.getByEmail(principal.getName());
      match.setOwner(owner.get());

      matchesService.createMatch(match);

      return "redirect:/match/user-list";

    } catch (Exception e) {

      redirectAttributes.addFlashAttribute("error", "Error al crear el evento" + e.getMessage());
      return "redirect:/match/new-match";
    }
  }

  @GetMapping("/user-list")
  public String userMatchesList(
      Model model,
      Principal principal
  ) {

    model.addAttribute("matches", matchesService.getByOwnerId(principal.getName()));
    model.addAttribute("matchUpdateDto", new MatchUpdateDto());
    model.addAttribute("skillLevel", SkillLevel.values());

    return "user-matches-list";
  }

  @PostMapping("/update")
  public String updateMatch(
      @ModelAttribute MatchUpdateDto dto,
      RedirectAttributes redirectAttributes
  ) {

    try {

      Match currentMatch = matchesService.getById(dto.getId()).get();

      if (dto.getTitle() != null) {
        currentMatch.setTitle(dto.getTitle());
      }

      if (dto.getDescription() != null) {
        currentMatch.setDescription(dto.getDescription());
      }

      if (dto.getEventDate() != null) {
        currentMatch.setEventDate(dto.getEventDate());
      }

      if (dto.getEndDate() != null) {
        currentMatch.setEndDate(dto.getEndDate());
      }

      if (dto.getPricePerPerson() != null) {
        currentMatch.setPricePerPerson(dto.getPricePerPerson());
      }

      if (dto.getSkillLevel() != null) {
        currentMatch.setSkillLevel(dto.getSkillLevel());
      }

      if (dto.getMaxParticipants() != null) {
        currentMatch.setMaxParticipants(dto.getMaxParticipants());
      }

      matchesService.save(currentMatch);

      redirectAttributes.addFlashAttribute("success", "Evento actualizado correctamente");
      return "redirect:/match/user-list";

    } catch (Exception e) {

      redirectAttributes.addFlashAttribute("error",
          "Error al actualizar evento.");
      return "redirect:/match/user-list";
    }
  }

  @PostMapping("/delete")
  public String delete(
      @RequestParam Long id,
      RedirectAttributes redirectAttributes
  ) {

    try {

      matchesService.delete(id);
      redirectAttributes.addFlashAttribute("success", "Evento eliminado correctamente");

      return "redirect:/match/user-list";

    } catch (Exception e) {

      redirectAttributes.addFlashAttribute("error",
          "Error al actualizar evento.");
      return "redirect:/match/user-list";
    }
  }
}
