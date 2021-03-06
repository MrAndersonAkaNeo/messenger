package com.ntu.messenger.data.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    private static final long serialVersionUID = 7412067556079052833L;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(unique = true)
    @Email
    private String email;

    @NotNull
    private String firstname;

    private String lastname;

    @Column(name = "is_enabled")
    private boolean enabled;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "password_hash")
    private String password;

    @ManyToMany
    @Setter(AccessLevel.PRIVATE)
    @JoinTable(name = "contacts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_user_id"))
    private Set<User> contacts = new HashSet<>();

    @ManyToMany
    @Setter(AccessLevel.PRIVATE)
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Chat> userChats = new HashSet<>();

    public void assignChat(Chat chat) {
        userChats.add(chat);
    }

    public void removeChat(Chat chat) {
        userChats.remove(chat);
    }
}

