package com.ntu.messenger.data.converter;

import com.ntu.messenger.data.dto.user.UserCreateDto;
import com.ntu.messenger.data.dto.user.UserDto;
import com.ntu.messenger.data.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    User map(UserCreateDto userCreateDto);

    UserDto map(User user);

    List<UserDto> map(Set<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateFromDto(UserCreateDto updateDto, @MappingTarget User user);
}
