package com.example.example.utils.mapper;

import com.example.example.model.Pokemon;
import com.example.example.model.User;
import com.example.example.repository.model.PokemonEntity;
import com.example.example.repository.model.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<User> userEntitiesToUsers(List<UserEntity> userEntities);

    User userEntityToUser(UserEntity entity);

    UserEntity userToUserEntity(User user);
    
    Pokemon pokemonEntityToPokemon(PokemonEntity pokemonEntity);

}
