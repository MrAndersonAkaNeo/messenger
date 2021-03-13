package com.ntu.messenger.api.service;

import com.ntu.messenger.data.converter.UserMapper;
import com.ntu.messenger.data.dto.user.UserCreateDto;
import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Boolean signUpUser(UserCreateDto userCreateDto) {
        User user = userRepository.findByUsername(userCreateDto.getUsername());
        if (user == null) {
           return createNewUser(userCreateDto);
        }
        return false;
    }

    private Boolean createNewUser(UserCreateDto dto) {
        User user = UserMapper.MAPPER.fromCreateDto(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }


}
