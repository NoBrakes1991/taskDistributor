package app.dao;

import app.entities.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Dao extends CrudRepository<Task, Long> {

    List<Task> findByAssignee(String assignee);
}
