package com.example.Event_Manager.models.user.mapper;

import com.example.Event_Manager.models.user.User;
import com.example.Event_Manager.models.user.dto.request.CreateUserDTO;
import com.example.Event_Manager.models.user.dto.request.UpdateUserDTO;
import com.example.Event_Manager.models.user.dto.response.UserDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "favorite", ignore = true)
    @Mapping(target = "interestedUsers", ignore = true)
    User toEntity(CreateUserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User user, UpdateUserDTO dto);
}