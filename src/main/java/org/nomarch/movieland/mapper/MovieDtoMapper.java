package org.nomarch.movieland.mapper;

import org.mapstruct.Mapper;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.request.SaveMovieRequest;

@Mapper(componentModel = "spring")
public interface MovieDtoMapper {
    Movie dtoToMovie(SaveMovieRequest dto);
    FullMovieDto movieToDto(Movie entity);
}
