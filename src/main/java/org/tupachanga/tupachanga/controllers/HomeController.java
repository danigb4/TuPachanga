package org.tupachanga.tupachanga.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tupachanga.tupachanga.dtos.MatchWithCoordinatesDto;
import org.tupachanga.tupachanga.entities.Match;
import org.tupachanga.tupachanga.entities.Sport;
import org.tupachanga.tupachanga.services.MatchesService;
import org.tupachanga.tupachanga.services.SportsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")

public class HomeController {

  private final MatchesService matchesService;

  private final SportsService sportsService;

  @GetMapping
  public String home(Model model) {

    List<Match> randomMatches = matchesService.getActiveMatches();
    List<Sport> sports = sportsService.getAll();
    List<MatchWithCoordinatesDto> matchesWithCoords = matchesService.getMatchesWithCoordinates();

    model.addAttribute("matches", randomMatches);
    model.addAttribute("sports", sports);
    model.addAttribute("matchesWithCoordinates", matchesWithCoords);

    return "index";
  }
}