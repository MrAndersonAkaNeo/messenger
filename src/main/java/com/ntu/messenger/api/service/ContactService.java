package com.ntu.messenger.api.service;

import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.model.UserProjection;
import com.ntu.messenger.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final UserRepository userRepository;

    @Transactional
    public void addContact(User user, Long id) {
        User contact = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!contact.equals(user)) {
            Set<User> contacts = user.getContacts();
            contacts.add(contact);
        }
    }

    @Transactional
    public void removeContact(User user, Long id) {
        User contact = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!contact.equals(user)) {
            Set<User> contacts = user.getContacts();
            contacts.remove(contact);
        }
    }
    @Transactional(readOnly = true)
    public List<UserProjection> getUserContacts(User user, Integer limit, Integer page) {
        return userRepository.findAllUsersContacts(user.getId(), limit, limit * page);
    }
}
