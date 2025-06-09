package org.tupachanga.tupachanga.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class NewUserDto {

  @NotBlank(message = "Este campo no puede estar vacío")
  @Size(min = 2, max = 20, message = "El nombre debe de tener entre 2 y 20 carácteres")
  private String firstName;

  @NotBlank(message = "Este campo no puede estar vacío")
  @Size(min = 2, max = 50, message = "El nombre debe de tener entre 2 y 50 carácteres")
  private String lastName;

  @NotBlank(message = "Este campo no puede estar vacío")
  @Email(message = "El correo electrónico no tiene un formato válido")
  private String email;

  @NotBlank(message = "Este campo no puede estar vacío")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
      message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número"
  )
  private String password;

  @Pattern(regexp = "^$|^\\d{9}$", message = "El teléfono debe contener exactamente 9 dígitos")
  private String telephone;

  @NotBlank(message = "Este campo no puede estar vacío")
  @Size(max = 250, message = "La descripción no puede superar los 250 caracteres")
  private String description;

  @NotEmpty(message = "Debes seleccionar al menos un municipio")
  private List<Long> municipalities;

  @NotEmpty(message = "Debes seleccionar al menos un deporte")
  private List<Long> sports;
}
