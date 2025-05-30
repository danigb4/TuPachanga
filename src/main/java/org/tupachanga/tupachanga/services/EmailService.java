package org.tupachanga.tupachanga.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  public void sendEmail(String to, String subject, String text) {

    SimpleMailMessage email = new SimpleMailMessage();

    email.setTo(to);
    email.setSubject(subject);
    email.setText(text);
    mailSender.send(email);
  }
}
