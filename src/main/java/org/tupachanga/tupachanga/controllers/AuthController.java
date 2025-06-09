package org.tupachanga.tupachanga.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.tupachanga.tupachanga.dtos.MunicipalityDto;
import org.tupachanga.tupachanga.dtos.NewUserDto;
import org.tupachanga.tupachanga.entities.Municipality;
import org.tupachanga.tupachanga.entities.Province;
import org.tupachanga.tupachanga.entities.Sport;
import org.tupachanga.tupachanga.entities.User;
import org.tupachanga.tupachanga.entities.enums.Role;
import org.tupachanga.tupachanga.services.MunicipalitiesService;
import org.tupachanga.tupachanga.services.ProvincesService;
import org.tupachanga.tupachanga.services.SportsService;
import org.tupachanga.tupachanga.services.UsersService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final ProvincesService provincesService;

  private final PasswordEncoder passwordEncoder;

  private final UsersService usersService;

  private final MunicipalitiesService municipalitiesService;

  private final SportsService sportsService;

  @Value("${file.upload-dir}")
  private String uploadPathDir;

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
    List<Sport> sports = sportsService.getAll();

    model.addAttribute("newUserDto", new NewUserDto());
    model.addAttribute("provinces", provinces);
    model.addAttribute("sports", sports);

    return "register";
  }

  @PostMapping("/register")
  public String processRegister(
      @Valid @ModelAttribute("newUserDto") NewUserDto dto,
      BindingResult result,
      @RequestParam("avatarFile") MultipartFile avatarFile,
      Model model) {

    if (result.hasErrors()) {

      List<Province> provinces = provincesService.getAll();
      List<Sport> sports = sportsService.getAll();

      model.addAttribute("provinces", provinces);
      model.addAttribute("sports", sports);

      return "register";
    }

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

      List<Sport> sports =
          sportsService.getSportsByIds(dto.getSports());
      newUser.setSports(new HashSet<>(sports));

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
}
