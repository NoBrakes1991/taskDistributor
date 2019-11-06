package app.dao;

import app.entities.Task;
import org.springframework.data.repository.CrudRepository;

public interface Dao extends CrudRepository<Task, Long> {
}
