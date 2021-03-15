package com.ntu.messenger.api.service;

import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Set<User> findUsersByUsernames(List<String> usernames) {
        return userRepository.findAllByUsernameIn(usernames);
    }


}
