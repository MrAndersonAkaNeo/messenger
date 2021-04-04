package com.ntu.messenger.data.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
    private String email;

    @NotNull
    private String firstname;

    private String lastname;

    @Column(name = "is_enabled")
    private boolean enabled;

    @NotNull
    @Column(name = "password_hash")
    private String password;

    @ManyToMany
    @JoinTable(name = "friendship",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    @ManyToMany
    @Setter(AccessLevel.PRIVATE)
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Chat> userChats = new HashSet<>();

    public void assignChat(Chat chat) {
        userChats.add(chat);
        chat.getChatParticipants().add(this);
    }

}

