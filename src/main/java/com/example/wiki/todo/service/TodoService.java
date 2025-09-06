package com.example.wiki.todo.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wiki.todo.dto.TodoDto;
import com.example.wiki.todo.dto.TodoListResultDto;
import com.example.wiki.todo.mapper.TodoMapper;

/**
 * 
 */
/**
 * 
 */
@Service
public class TodoService {

    @Autowired
    TodoMapper todoMapper;

    /**
     * Todoリスト完了、未完了で並び変えてContorollerに返却する
     * @return
     */
    public List<TodoDto> getAllTodoListSortedByCompleted() {

        // Todo全件検索のMapper呼び出し
        List<TodoDto> resultDto = todoMapper.getAllTodoList();

        // Stream APIで未完了(false) → 完了(true) の順に並べ替え
        List<TodoDto> resultList = resultDto.stream()
        .sorted(Comparator.comparing(TodoDto::isCompleted))
        .sorted(Comparator.comparing(TodoDto::getId).reversed())
        .collect(Collectors.toList());
       
        return resultList;
    }

    
    /**
     * Todoリスト完了、未完了の2つに分割してContorollerに返却する
     * @return
     */
    public TodoListResultDto getAllTodoListFilterByCompleted() {

        // Todo全件検索のMapper呼び出し
        List<TodoDto> resultDto = todoMapper.getAllTodoList();

        // 完了のみフィルタ
        List<TodoDto> completedResultDto = resultDto.stream()
                .filter(TodoDto::isCompleted) // completed == true
                .collect(Collectors.toList());

        // 未完了のみフィルタ
        List<TodoDto> notCompletedResultDto = resultDto.stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());
        
//        partitioningByを使用する方法
//        Map<Boolean, List<TodoDto>> partitioned = resultDto.stream()
//                .collect(Collectors.partitioningBy(TodoDto::isCompleted));
//
//        List<TodoDto> completedResultDto = partitioned.get(true);   // 完了
//        List<TodoDto> notCompletedResultDto = partitioned.get(false); // 未完了

        // 完了リスト、未完了リストをController返却用のDTOに設定
        TodoListResultDto resultList = new TodoListResultDto();
        resultList.setCompletedList(completedResultDto);
        resultList.setCompletedList(notCompletedResultDto);

        
        return resultList;
    }
    
    public TodoDto insertTodo(TodoDto todoDto) {
        todoMapper.insertTodo(todoDto);
        return todoDto;
    }
    
    public TodoDto updateTodo(TodoDto todoDto) {
        todoMapper.updateTodo(todoDto);
        return todoDto;
    }

}
