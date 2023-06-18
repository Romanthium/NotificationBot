package com.example.notificationbot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    private String text;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime notificationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
