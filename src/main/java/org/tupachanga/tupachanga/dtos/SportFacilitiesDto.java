package org.tupachanga.tupachanga.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SportFacilitiesDto {

    private String name;
    private Double latitude;
    private Double longitude;
    private String picture;
    private String municipality;

    @Override
    public String toString() {
        return "SportFacilitiesDto{" +
                "name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", municipality='" + municipality + '\'' +
                '}';
    }
}
