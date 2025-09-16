package oocl.example.todolistbackend.repository.dao;

import oocl.example.todolistbackend.repository.entity.ToDoListItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoListJpaRepository extends JpaRepository<ToDoListItem,Long> {


}
