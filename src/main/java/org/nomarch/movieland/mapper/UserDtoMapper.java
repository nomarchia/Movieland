package org.nomarch.movieland.mapper;

import org.mapstruct.Mapper;
import org.nomarch.movieland.dto.UserDto;
import org.nomarch.movieland.entity.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto userToDto(User user);
}
