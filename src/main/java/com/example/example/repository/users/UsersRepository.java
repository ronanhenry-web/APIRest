package com.example.example.repository.users;

import com.example.example.repository.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    /**
     * @param id must not be {@literal null}.
     * @return Find by user id
     */
    Optional<UserEntity> findById(Long id);

    /**
     * @param userId must not be {@literal null}.
     *               Delete a user by id
     */
    void deleteById(Long userId);
}
