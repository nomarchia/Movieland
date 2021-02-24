package org.nomarch.movieland.entity;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private Long id;
    private String name;
}
