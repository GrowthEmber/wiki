package com.example.wiki.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wiki.todo.dto.TodoDto;
import com.example.wiki.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoRestController {
    
    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<TodoDto> getTodos() {
        return todoService.getAllTodoListSortedByPriority();
    }

    @PostMapping("/insert")
    public TodoDto insertTodos(@RequestBody TodoDto todoDto) {
        return todoService.insertTodo(todoDto);
    }

    @PutMapping("/{id}")
    public TodoDto updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        todoDto.setId(id);
        return todoService.updateTodo(todoDto);
    }
    
    @PutMapping("/reorder")
    public void reorderTodos(@RequestBody List<TodoDto> todos) {
        todoService.updatePriorities(todos);
    }
}
