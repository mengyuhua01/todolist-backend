package oocl.example.todolistbackend.controller;

import jakarta.validation.Valid;
import oocl.example.todolistbackend.dto.CreateToDoReq;
import oocl.example.todolistbackend.dto.UpdateToDoReq;
import oocl.example.todolistbackend.repository.entity.ToDoListItem;
import oocl.example.todolistbackend.servcie.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoListController {
    @Autowired
    private TodoListService todoListService;

    @PostMapping("")
    public ResponseEntity<ToDoListItem> createTodoItem(@Valid  @RequestBody CreateToDoReq createToDoReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoListService.createTodoListItem(createToDoReq));
    }
    @GetMapping("")
    public List<ToDoListItem> getEmployeeList() {
        return todoListService.findAllTodoListItems();
    }

    @PutMapping("/{id}")
    public ToDoListItem updateTodo(@PathVariable Long id, @Valid @RequestBody UpdateToDoReq updateToDoReq) {
        return todoListService.updateTodo(id,updateToDoReq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoListService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
