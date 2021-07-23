package com.airfrance.AirFranceTest.mapper;

import com.airfrance.AirFranceTest.dto.UserDto;
import com.airfrance.AirFranceTest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDto, User> {



    @Mapping(source = "user.login", target = "login")
    UserDto toDto(User user);

    @Mapping(source = "login", target = "user.login")
    User toEntity(UserDto userDto);

}