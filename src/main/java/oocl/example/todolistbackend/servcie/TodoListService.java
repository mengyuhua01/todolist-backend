package oocl.example.todolistbackend.servcie;

import oocl.example.todolistbackend.dto.CreateToDoReq;
import oocl.example.todolistbackend.repository.TodoListRepository;
import oocl.example.todolistbackend.repository.entity.ToDoListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {
    @Autowired
    private TodoListRepository todoListRepository;

    public List<ToDoListItem> findAllTodoListItems() {
        return todoListRepository.getAllTodoListItems();
    }

    public ToDoListItem createTodoListItem(CreateToDoReq createToDoReq) {
        ToDoListItem toDoListItem = ToDoListItem.builder().text(createToDoReq.getText())
                .done(false)
                .build();
        return todoListRepository.addTodoListItem(toDoListItem);
    }
}
