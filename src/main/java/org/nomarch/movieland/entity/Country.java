package org.nomarch.movieland.entity;

import lombok.*;
import org.nomarch.movieland.dto.ReturnedEntity;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Country extends ReturnedEntity {
    private Long id;
    private String name;
}
