package org.tupachanga.tupachanga.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tupachanga.tupachanga.dtos.SportFacilitiesDto;
import org.tupachanga.tupachanga.services.EmailService;
import org.tupachanga.tupachanga.services.SportFacilityService;

import java.util.List;

@Controller
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class SportFacilitiesController {

    private final SportFacilityService sportFacilityService;
    private final EmailService emailService;

    @Value("${EMAIL_USERNAME}")
    private String supportEmail;

    @GetMapping("/show")
    public String showFacilities(Model model) {

        List<SportFacilitiesDto> sf = sportFacilityService.getAll();
        model.addAttribute("sportFacilities", sf);
        return "show-sportfacilities";
    }

    @PostMapping("/suggest")
    public String suggest(
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {

        String subject = "Nueva sugerencia de instalación deportiva";
        String body = "Un usuario ha sugerido lo siguiente:\n\n" + message;

        emailService.sendEmail(supportEmail, subject, body);

        redirectAttributes.addFlashAttribute("success", "Sugerencia enviada, la revisaremos cuanto antes para añadirla a nuestra web");
        return "redirect:/facilities/show";
    }
}
