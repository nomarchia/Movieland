package org.nomarch.movieland.mapper;

import org.mapstruct.Mapper;
import org.nomarch.movieland.dto.ReviewDto;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.request.ReviewRequest;

@Mapper(componentModel = "spring")
public interface ReviewDtoMapper {
    ReviewDto reviewToDto(Review review);

    Review dtoToReview(ReviewRequest reviewRequest);
}
