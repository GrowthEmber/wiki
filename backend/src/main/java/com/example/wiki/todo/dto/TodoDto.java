package com.example.wiki.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    
    private Long id;
    private String title;
    private String priority;
    private boolean completed;
}
