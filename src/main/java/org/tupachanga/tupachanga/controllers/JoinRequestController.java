package org.tupachanga.tupachanga.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tupachanga.tupachanga.entities.JoinRequest;
import org.tupachanga.tupachanga.entities.Match;
import org.tupachanga.tupachanga.entities.User;
import org.tupachanga.tupachanga.entities.enums.RequestStatus;
import org.tupachanga.tupachanga.services.JoinRequestsService;
import org.tupachanga.tupachanga.services.MatchesService;
import org.tupachanga.tupachanga.services.UsersService;

import java.util.Optional;

@Controller
@RequestMapping("/request")
@RequiredArgsConstructor
public class JoinRequestController {


  private final JoinRequestsService joinRequestsService;

  private final MatchesService matchesService;

  private final UsersService usersService;

  @PostMapping("/accept/{id}")
  public String acceptJoinRequest(
      @PathVariable Long id,
      RedirectAttributes redirectAttributes
  ) {

    try {

      Optional<JoinRequest> request = joinRequestsService.getById(id);

      if (request.isPresent()) {

        Optional<Match> match = matchesService.getById(request.get().getMatch().getId());

        if (match.isPresent()) {
          User user = request.get().getSender();
          user.getMatchesJoined().add(match.get());
          usersService.save(user);

          request.get().setStatus(RequestStatus.ACCEPTED);
          joinRequestsService.save(request.get());
        }
      }
      redirectAttributes.addFlashAttribute("success" , "¡Solicitud aceptada con éxito!");
      return "redirect:/user/request";

    }catch (Exception e) {

      redirectAttributes.addFlashAttribute("error" , "¡Solicitud denegada con éxito!");
      return "redirect:/user/request";
    }
  }

  @PostMapping("/reject/{id}")
  public String rejecttJoinRequest(
      @PathVariable Long id
  ) {

    Optional<JoinRequest> request = joinRequestsService.getById(id);

    if (request.isPresent()) {
      JoinRequest joinRequest = request.get();
      joinRequest.setStatus(RequestStatus.REJECTED);
      joinRequestsService.save(joinRequest);
    }

    return "redirect:/user/request";
  }
}
