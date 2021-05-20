package com.ntu.messenger.api.service;

import com.ntu.messenger.data.converter.UserMapper;
import com.ntu.messenger.data.dto.user.UserCreateDto;
import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Set<User> findUsersByIdIn(List<Long> ids) {
        return userRepository.findAllByIdIn(ids);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public Boolean signUpUser(UserCreateDto userCreateDto) {
        User byUsername = userRepository.findByUsername(userCreateDto.getUsername());
        User byEmail = userRepository.findByEmail(userCreateDto.getEmail());
        if (byUsername == null && byEmail == null) {
            return createNewUser(userCreateDto);
        }
        return false;
    }

    @Transactional
    public User updateUser(User user, UserCreateDto dto) {
        UserMapper.MAPPER.updateFromDto(dto, user);
        return user;
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    private Boolean createNewUser(UserCreateDto dto) {
        User user = UserMapper.MAPPER.map(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }
}
