package oocl.example.todolistbackend.repository.imp;

import oocl.example.todolistbackend.repository.TodoListRepository;
import oocl.example.todolistbackend.repository.dao.TodoListJpaRepository;
import oocl.example.todolistbackend.repository.entity.ToDoListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoListRepositoryImp implements TodoListRepository {
    @Autowired
    private TodoListJpaRepository todoListJpaRepository;

    @Override
    public List<ToDoListItem> getAllTodoListItems() {
        return todoListJpaRepository.findAll();
    }

    @Override
    public void clear() {
        todoListJpaRepository.deleteAll();
    }

    @Override
    public ToDoListItem addTodoListItem(ToDoListItem toDoListItem) {
        return todoListJpaRepository.save(toDoListItem);
    }
}
