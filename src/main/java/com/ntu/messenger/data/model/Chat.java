package com.ntu.messenger.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat extends BaseEntity {

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> chatMessages = new ArrayList<>();

    @ManyToMany(mappedBy = "userChats")
    private Set<User> chatParticipants = new HashSet<>();

}
