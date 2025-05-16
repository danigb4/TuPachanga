package org.tupachanga.tupachanga.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class NewUserDto {

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private String telephone;

  private String description;

  private List<Long> municipalities;

  private List<Long> sports;
}
