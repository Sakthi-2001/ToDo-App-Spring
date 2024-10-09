package com.app.Todo.service;

import com.app.Todo.model.TodoItem;

import java.util.List;

public interface TodoService {
    TodoItem saveItem(TodoItem item);
    List<TodoItem> getItems();
    TodoItem getItemById(long id);
    TodoItem updateItem(TodoItem item,long id);
    void deleteItem(long id);
    TodoItem updateStatus(TodoItem.Status status,long id);
    List<TodoItem> getItemByStatus(String status);
    TodoItem getItemByTitle(String title);
    List<Long> deleteMultipleItems(List<Long> items);
}
