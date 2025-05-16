package org.tupachanga.tupachanga.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.tupachanga.tupachanga.entities.Municipality;
import org.tupachanga.tupachanga.entities.Sport;
import org.tupachanga.tupachanga.entities.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Setter
public class UpdateUserProfileDto {

  private String firstName;

  private String lastName;

  private String telephone;

  private String description;

  private List<Long> municipalities;

  private List<Long> sports;

  public UpdateUserProfileDto(User user) {
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.telephone = user.getTelephone();
    this.description = user.getDescription();
    this.sports =user.getSports().stream()
        .map(Sport::getId)
        .collect(Collectors.toList());
    this.municipalities =user.getMunicipalities().stream()
        .map(Municipality::getId)
        .collect(Collectors.toList());
  }
}
