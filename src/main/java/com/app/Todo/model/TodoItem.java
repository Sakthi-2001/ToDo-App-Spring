package com.app.Todo.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="todo")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="Title", nullable=false)
    private String title;

    @Column(name="Description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="Status")
    private Status status;

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED;
    }




}
