package org.nomarch.movieland.entity;

import lombok.*;
import org.nomarch.movieland.dto.ReturnedEntity;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Genre extends ReturnedEntity {
    private Long id;
    private String name;
}
