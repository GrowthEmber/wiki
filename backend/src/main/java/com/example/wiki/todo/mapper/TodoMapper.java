package com.example.wiki.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.wiki.todo.dto.TodoDto;

@Mapper
public interface TodoMapper {
    
    /**
     * Todoリスト全件検索
     * @return todoリスト
     */
    List<TodoDto> getAllTodoList();
    
    /**
     * Todoリスト1件登録
     * @param todo
     */
    void insertTodo(TodoDto todo);
    
    /**
     * Todo更新（完了↔未完了）
     * @return
     */
    void updateTodo(TodoDto todoDto);
    
    /**
     * Todo更新（完了↔未完了）
     * @return
     */
    void updatePriorities(@Param("todoDto") List<TodoDto> todoDto);
    }

