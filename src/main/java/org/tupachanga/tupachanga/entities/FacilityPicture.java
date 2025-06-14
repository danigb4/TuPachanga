package org.tupachanga.tupachanga.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "facility_pictures")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacilityPicture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String filename;

  private String alt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "facility_id", nullable = false)
  private SportFacility facility;

}
