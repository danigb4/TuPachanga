package org.tupachanga.tupachanga.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sport_facilities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SportFacility {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  private String street;

  private String description;

  @Column(nullable = false, precision = 9, scale = 6)
  private BigDecimal latitude;

  @Column(nullable = false, precision = 9, scale = 6)
  private BigDecimal longitude;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<FacilityPicture> pictures;

  @OneToMany(mappedBy = "facility")
  private Set<Match> matches;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "municipality_id", nullable = false)
  private Municipality municipality;
}
