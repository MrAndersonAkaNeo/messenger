package com.ntu.messenger.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat extends BaseEntity {

    @OneToMany(mappedBy = "chat")
    private List<Message> chatMessages = new ArrayList<>();

    @ManyToMany(mappedBy = "userChats")
    private Set<User> chatParticipants = new HashSet<>();

}
