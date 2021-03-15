package com.ntu.messenger.data.repository;

import com.ntu.messenger.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Set<User> findAllByUsernameIn(List<String> usernames);

}
