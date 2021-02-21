package com.ntu.messenger.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    private static final long serialVersionUID = 7412067556079052833L;

    private String username;

    private String email;

    private String firstname;

    private String lastname;

    private boolean isEnabled;

    private boolean superUser;

    @Column(name = "passwordHash")
    private String password;

}
