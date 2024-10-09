package com.app.Todo.controller;

import com.app.Todo.model.TodoItem;
import com.app.Todo.service.impl.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping()
    public ResponseEntity<TodoItem> addItem(@RequestBody TodoItem item){
        return new ResponseEntity<TodoItem>(todoService.saveItem(item), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<TodoItem> getItems(){
        return todoService.getItems();
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoItem> getItemById(@PathVariable("id") long id){
        return new ResponseEntity<TodoItem>(todoService.getItemById(id),HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TodoItem> updateItem(@PathVariable("id") long id, @RequestBody TodoItem Item){
        return new ResponseEntity<TodoItem>(todoService.updateItem(Item,id),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") long id){
        todoService.deleteItem(id);
        return new ResponseEntity<String>("Item deleted succesfully!!",HttpStatus.OK);
    }

    @PutMapping("updateStatus/{id}")
    public ResponseEntity<TodoItem> updateStatus(@PathVariable("id") long id, @RequestBody TodoItem item){
        return new ResponseEntity<TodoItem>(todoService.updateStatus(item.getStatus(),id),HttpStatus.OK);
    }

    @GetMapping("status/{status}")
    public List<TodoItem> getItemByStatus(@PathVariable("status") String status){
        return todoService.getItemByStatus(status);
    }

    @GetMapping("title/{title}")
    public ResponseEntity<TodoItem> getItemByTitle(@PathVariable("title") String title){
        return new ResponseEntity<TodoItem>(todoService.getItemByTitle(title),HttpStatus.OK);
    }

    @DeleteMapping("/deleteMultiple")
    public ResponseEntity<List<Long>> deleteMultipleItems(@RequestBody List<Long> items) {
        List<Long> itemNotFound = todoService.deleteMultipleItems(items);
        return new ResponseEntity<List<Long>>(todoService.deleteMultipleItems(items),HttpStatus.OK);
    }
}
