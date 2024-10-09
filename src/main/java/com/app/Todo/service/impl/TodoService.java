package com.app.Todo.service.impl;

import com.app.Todo.exception.ResourceNotFoundException;
import com.app.Todo.exception.TypeErrorException;
import com.app.Todo.model.TodoItem;
import com.app.Todo.repository.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TodoService implements com.app.Todo.service.TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Override
    public TodoItem saveItem(TodoItem item) {
        return todoRepo.save(item);
    }

    @Override
    public List<TodoItem> getItems() {
        return todoRepo.findAll();
    }

    @Override
    public TodoItem getItemById(long id) {
        Optional<TodoItem> item = todoRepo.findById(id);
        if(item.isPresent()){
            return item.get();
        }
        throw new TypeErrorException("TodoItem","Id",id);
    }

    @Override
    public TodoItem updateItem(TodoItem item,long id) {
        Optional<TodoItem> existingItemOptional = todoRepo.findById(id);
        if(existingItemOptional.isPresent()){
            TodoItem existingItem = existingItemOptional.get();
            existingItem.setTitle(item.getTitle());
            existingItem.setDescription(item.getDescription());
            if (!isValidEnumStatus(item.getStatus())) {
                throw new TypeErrorException("TodoItem", "Status", item.getStatus());
            }
            existingItem.setStatus(item.getStatus());
            todoRepo.save(existingItem);
            return existingItem;
        }
        throw new TypeErrorException("TodoItem","Id",id);
    }

    @Override
    public void deleteItem(long id) {
        todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("TodoItem","Id",id));
        todoRepo.deleteById(id);
    }

    @Override
    public TodoItem updateStatus(TodoItem.Status status, long id) {
        Optional<TodoItem> existingItemOptional = todoRepo.findById(id);
        if(existingItemOptional.isPresent()){
            TodoItem existingItem = existingItemOptional.get();
            if(!isValidEnumStatus(status)){
                throw new TypeErrorException("TodoItem","Status",id);
            }
            existingItem.setStatus(status);
            todoRepo.save(existingItem);
            return existingItem;
        }
        throw new TypeErrorException("TodoItem","Id",id);
    }

    @Override
    public List<TodoItem> getItemByStatus(String status) {
        List<TodoItem> requiredItems = new ArrayList<>();
        List<TodoItem> items = todoRepo.findAll();

        TodoItem.Status enumStatus;
        try {
            enumStatus = TodoItem.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TypeErrorException("TodoItem", "Status", status);
        }

        for(TodoItem item : items){
            if(item.getStatus().equals(enumStatus)){
                requiredItems.add(item);
            }
        }
        return requiredItems;
    }

    @Override
    public TodoItem getItemByTitle(String title) {
        List<TodoItem> items = todoRepo.findAll();
        for(TodoItem item : items){
            if(item.getTitle().toLowerCase().equals(title.toLowerCase())){
                return item;
            }
        }
        throw new ResourceNotFoundException("TodoItem","title",title);
    }

    @Override
    public List<Long> deleteMultipleItems(List<Long> items) {
        List<Long> itemNotFound = new ArrayList<>();
        for(Long id:items){
            if(!todoRepo.findById(id).isPresent()){
                itemNotFound.add(id);
            }
            else{
                todoRepo.deleteById(id);
            }
        }
        return itemNotFound;
    }

    private boolean isValidStatus(String status) {
        for (TodoItem.Status enumStatus : TodoItem.Status.values()) {
            if (enumStatus.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidEnumStatus(TodoItem.Status status) {
        for (TodoItem.Status enumStatus : TodoItem.Status.values()) {
            if (enumStatus == status) {
                return true;
            }
        }
        return false;
    }
}
