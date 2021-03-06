package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.criteria.PageCriteria;
import com.ntu.messenger.api.service.ContactService;
import com.ntu.messenger.api.service.UserService;
import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.model.UserProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/contacts")
@RequiredArgsConstructor
public class ContactsController extends SecurityController {

    private final UserService userService;
    private final ContactService contactService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserProjection>> getUserContacts(@Valid PageCriteria pageCriteria) {
        User current = userService.findUserById(getUserDetails().getId());
        List<UserProjection> userContacts = contactService.getUserContacts(current, pageCriteria.getSize(), pageCriteria.getPage());
        return new ResponseEntity<>(userContacts, HttpStatus.OK);
    }

    @GetMapping(path = "present/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isInContactList(@PathVariable("id") Long id) {
        User current = userService.findUserById(getUserDetails().getId());
        return new ResponseEntity<>(contactService.isInContactList(current,id), HttpStatus.OK);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@PathVariable("id") Long id) {
        User current = userService.findUserById(getUserDetails().getId());
        contactService.addContact(current, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeContact(@PathVariable("id") Long id) {
        User current = userService.findUserById(getUserDetails().getId());
        contactService.removeContact(current, id);
    }
}
