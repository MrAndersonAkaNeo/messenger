package com.ntu.messenger.data.converter;

import com.ntu.messenger.data.dto.user.UserCreateDto;
import com.ntu.messenger.data.dto.user.UserDto;
import com.ntu.messenger.data.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    User fromCreateDto(UserCreateDto userCreateDto);

    User fromDto(UserDto dto);

    Set<User> fromDtoSet(Set<UserDto> userDtoSet);

}
