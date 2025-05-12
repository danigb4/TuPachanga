package org.tupachanga.tupachanga.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "municipalities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Municipality {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "normalized_name", nullable = false)
  private String normalizedName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "province_id", nullable = false)
  private Province province;

  @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<SportFacility> sportFacilities;

  @ManyToMany(mappedBy = "municipalities")
  private Set<User> users;

}
