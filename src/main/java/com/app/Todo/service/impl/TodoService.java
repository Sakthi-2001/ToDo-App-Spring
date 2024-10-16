package com.app.Todo.service.impl;

import com.app.Todo.exception.ResourceNotFoundException;
import com.app.Todo.exception.TypeErrorException;
import com.app.Todo.model.AggregateStatus;
import com.app.Todo.model.TodoItem;
import com.app.Todo.repository.StatusRepo;
import com.app.Todo.repository.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TodoService implements com.app.Todo.service.TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private StatusRepo statusRepo;


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
        try {
            TodoItem.Status enumStatus = TodoItem.Status.valueOf(status.toUpperCase());
            return todoRepo.findByStatus(enumStatus);
        } catch (IllegalArgumentException e) {
            throw new TypeErrorException("TodoItem", "Status", status);
        }
    }

    @Override
    public TodoItem getItemByTitle(String title) {
        return todoRepo.findByTitle(title).orElseThrow(()->new ResourceNotFoundException("TodoItem","title",title));
    }

    @Override
    @Transactional
    public List<Long> deleteMultipleItems(List<Long> ids) {
            boolean flag = true;
            List<Long> notFoundItems = new ArrayList<>();
            for(Long id : ids ){
                if(!todoRepo.findById(id).isPresent()){
                    notFoundItems.add(id);
                    flag=false;
                }
            }
            if(!flag){
                return notFoundItems;
            }
            for (Long id : ids) {
                todoRepo.deleteById(id);
            }
            return notFoundItems;
    }

    private boolean isValidEnumStatus(TodoItem.Status status) {
        try {
            TodoItem.Status.valueOf(status.name());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public List<AggregateStatus> getStatusCount(){
        return todoRepo.countStatusByType();
    }

    @Transactional
    @Override
    public void saveStatusItems(List<AggregateStatus> statusCount) {
        for(AggregateStatus item : statusCount ){
            statusRepo.save(item);
        }
        System.out.println("Saved statusCount successfully!!");

    }


}


