package com.app.Todo.model;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "aggregateStatus")
public class AggregateStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "status_type")
    private String statusType;

    @Column(name = "count")
    private Long count;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_stamp")
    private Date timeStamp;

    public AggregateStatus(TodoItem.Status statusType, Long count, Date timeStamp) {
        this.statusType = statusType.name();
        this.count = count;
        this.timeStamp = timeStamp;
    }

    public AggregateStatus() {
    }
}
