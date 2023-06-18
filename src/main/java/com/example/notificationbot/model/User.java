package com.example.notificationbot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "user_data")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;    // user name from telegram

    @NotEmpty
    @Column(unique = true)
    @Email
    private String login;

    @NotEmpty
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;

    @Enumerated(value=EnumType.STRING)
    private UserRole userRole;

}
