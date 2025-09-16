package oocl.example.todolistbackend.servcie;

import oocl.example.todolistbackend.dto.CreateToDoReq;
import oocl.example.todolistbackend.dto.UpdateToDoReq;
import oocl.example.todolistbackend.exception.TodoNotFoundException;
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

    public ToDoListItem findTodoListItemById(long id) {
        return todoListRepository.getTodoListItemById(id);
    }

    public ToDoListItem updateTodo(Long id, UpdateToDoReq updateToDoReq) {
        ToDoListItem toDoListItem = findTodoListItemById(id);
        if (toDoListItem == null) {
            throw new TodoNotFoundException("Todo item not found");
        }
        toDoListItem.setDone(updateToDoReq.getDone());
        toDoListItem.setText(updateToDoReq.getText());
        return todoListRepository.updateTodoListItem(toDoListItem);
    }
}
