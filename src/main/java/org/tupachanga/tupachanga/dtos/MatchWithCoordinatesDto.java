package org.tupachanga.tupachanga.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tupachanga.tupachanga.entities.Match;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class MatchWithCoordinatesDto {

  private Long matchId;
  private String title;
  private LocalDateTime eventDate;
  private String icon;
  private Double latitude;
  private Double longitude;
  private String municipalityName;

  public MatchWithCoordinatesDto(Match match, BigDecimal latitude, BigDecimal longitude, String icon, String municipalityName) {
    this.matchId = match.getId();
    this.title = match.getTitle();
    this.eventDate = match.getEventDate();
    this.latitude = latitude.doubleValue();
    this.longitude = longitude.doubleValue();
    this.icon = icon;
    this.municipalityName = municipalityName;
  }
}
