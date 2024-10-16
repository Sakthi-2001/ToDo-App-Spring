package com.app.Todo.repository;

import com.app.Todo.model.AggregateStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatusRepo extends JpaRepository<AggregateStatus,Long> {

}
