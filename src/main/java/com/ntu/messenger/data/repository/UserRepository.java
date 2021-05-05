package com.ntu.messenger.data.repository;

import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.model.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    Set<User> findAllByIdIn(List<Long> ids);

    @Query(value = "SELECT u.username AS username, u.firstname AS firstname, u.lastname AS lastname FROM user u " +
                   "WHERE u.id IN (SELECT c.contact_user_id c FROM contacts c WHERE c.user_id = :userId) " +
                   "ORDER BY u.id " +
                   "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<UserProjection> findAllUsersContacts(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);

}
