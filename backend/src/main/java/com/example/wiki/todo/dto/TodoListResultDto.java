package com.example.wiki.todo.dto;

import java.util.List;

import lombok.Data;

@Data
public class TodoListResultDto {
    
    // 完了リスト
    private List<TodoDto> completedList;
    // 未完了リスト
    private List<TodoDto> notCompletedList;

}
