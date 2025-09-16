package oocl.example.todolistbackend.repository;

import oocl.example.todolistbackend.repository.entity.ToDoListItem;

import java.util.List;

public interface TodoListRepository {

    List<ToDoListItem> getAllTodoListItems();

    void clear();

    ToDoListItem addTodoListItem(ToDoListItem toDoListItem);

    ToDoListItem getTodoListItemById(long id);

    ToDoListItem updateTodoListItem(ToDoListItem toDoListItem);

    void deleteTodoListItemById(long id);
}
