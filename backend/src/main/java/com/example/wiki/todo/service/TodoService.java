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
    public List<TodoDto> getAllTodoListSortedByPriority() {

        List<TodoDto> resultDto = todoMapper.getAllTodoList();

        List<TodoDto> resultList = resultDto.stream()
            .sorted(
                    Comparator.comparingInt((TodoDto todo) -> Integer.parseInt(todo.getPriority()))
                    .thenComparing(Comparator.comparing(TodoDto::getId).reversed())
                    )
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

        // 未着手 (status == "0")
        List<TodoDto> notStartedList = resultDto.stream()
                .filter(todo -> "0".equals(todo.getStatus()))
                .collect(Collectors.toList());

        // 進行中 (status == "1")
        List<TodoDto> progressList = resultDto.stream()
                .filter(todo -> "1".equals(todo.getStatus()))
                .collect(Collectors.toList());

        // 完了 (status == "2")
        List<TodoDto> completedList = resultDto.stream()
                .filter(todo -> "2".equals(todo.getStatus()))
                .collect(Collectors.toList());

        // Controller返却用のDTOに設定
        TodoListResultDto resultList = new TodoListResultDto();
        resultList.setNotStartedList(notStartedList);
        resultList.setProgressList(progressList);
        resultList.setCompletedList(completedList);

        return resultList;

//        partitioningByを使用する方法
//        Map<Boolean, List<TodoDto>> partitioned = resultDto.stream()
//                .collect(Collectors.partitioningBy(TodoDto::isCompleted));
//
//        List<TodoDto> completedResultDto = partitioned.get(true);   // 完了
//        List<TodoDto> notCompletedResultDto = partitioned.get(false); // 未完了
    }

    public TodoDto insertTodo(TodoDto todoDto) {
        todoMapper.insertTodo(todoDto);
        return todoDto;
    }
    
    public TodoDto updateTodo(TodoDto todoDto) {
        todoMapper.updateTodo(todoDto);
        return todoDto;
    }
    
    public void updatePriorities(List<TodoDto> todoDto) {
        todoMapper.updatePriorities(todoDto);
    }

}
