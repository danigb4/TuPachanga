package org.tupachanga.tupachanga.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.tupachanga.tupachanga.entities.Municipality;
import org.tupachanga.tupachanga.entities.Sport;
import org.tupachanga.tupachanga.entities.SportFacility;
import org.tupachanga.tupachanga.entities.enums.SkillLevel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class NewMatchDto {

  private String title;
  private String description;
  private Long idSport;
  private Long idSportFacility;
  private LocalDateTime eventDate;
  private LocalDateTime endDate;
  private BigDecimal pricePerPerson;
  private Integer maxParticipants;
  private SkillLevel skillLevel;
}
