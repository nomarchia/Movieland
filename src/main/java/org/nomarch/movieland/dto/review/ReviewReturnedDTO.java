package org.nomarch.movieland.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nomarch.movieland.dto.user.UserReturnedDTO;
import org.nomarch.movieland.entity.User;

@Setter
@Getter
@NoArgsConstructor
public class ReviewReturnedDTO {
    private Long id;
    private UserReturnedDTO user;
    private String text;
}
