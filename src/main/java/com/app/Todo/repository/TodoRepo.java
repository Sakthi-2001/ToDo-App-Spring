package com.app.Todo.repository;

import com.app.Todo.model.AggregateStatus;
import com.app.Todo.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoRepo extends JpaRepository<TodoItem,Long> {

//    @Query("SELECT item FROM TodoItem item WHERE item.title = :title")
    Optional<TodoItem> findByTitle(@Param("title") String title);

//    @Query("SELECT item FROM TodoItem item WHERE item.status = :status")
    List<TodoItem> findByStatus(@Param("status") TodoItem.Status title);

    @Query("SELECT new com.app.Todo.model.AggregateStatus(" +
            "t.status, COUNT(t), CURRENT_TIMESTAMP) " +
            "FROM TodoItem t " +
            "GROUP BY t.status")
    List<AggregateStatus> countStatusByType();

}
