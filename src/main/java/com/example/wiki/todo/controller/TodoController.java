package com.example.wiki.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.wiki.todo.dto.TodoDto;
import com.example.wiki.todo.service.TodoService;

@Controller
@RequestMapping(value = "/todos")
public class TodoController {
    
    @Autowired
    TodoService todoService;
    
    @RequestMapping(value = "/init")
    public ModelAndView todos(ModelAndView mv) {
        
        // Todoリスト全件検索処理呼び出し
        List<TodoDto> todoDtoList = todoService.getAllTodoListSortedByCompleted();
        
        // 画面返却項目設定
        mv.addObject("todoList", todoDtoList);
        mv.setViewName("todo/todo-list");
        return mv;
    }

}
