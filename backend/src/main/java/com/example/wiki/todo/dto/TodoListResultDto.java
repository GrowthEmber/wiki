package com.example.wiki.todo.dto;

import java.util.List;

import lombok.Data;

@Data
public class TodoListResultDto {
    
    // 未着手リスト
    private List<TodoDto> notStartedList;
    // 進行中リスト
    private List<TodoDto> progressList;
    // 完了リスト
    private List<TodoDto> completedList;

}
