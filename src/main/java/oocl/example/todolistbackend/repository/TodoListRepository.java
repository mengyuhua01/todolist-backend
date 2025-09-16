package oocl.example.todolistbackend.repository;

import oocl.example.todolistbackend.repository.entity.ToDoListItem;

import java.util.List;

public interface TodoListRepository {

    List<ToDoListItem> getAllTodoListItems();

    void clear();

    ToDoListItem addTodoListItem(ToDoListItem toDoListItem);
}
