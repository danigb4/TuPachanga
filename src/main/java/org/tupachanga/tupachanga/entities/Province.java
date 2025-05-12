package org.tupachanga.tupachanga.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "provinces")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Province {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(name = "normalized_name", nullable = false)
  private String normalizedName;

  @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Municipality> municipalities;
}
