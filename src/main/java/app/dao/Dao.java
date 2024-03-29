package app.dao;

import app.entities.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface Dao extends CrudRepository<Task, Long> {

    List <Task> findByAssignee(String assignee);
    List <Task> findByStartDateBeforeAndEndDateAfterOrStartDateOrEndDate(Date startDate, Date endDate,Date startDate2,Date endDate2);
    List <Task> findByAssigneeAndStartDateBeforeAndEndDateAfterOrAssigneeAndStartDateOrAssigneeAndEndDate(String assignee, Date startDate, Date endDate, String assignee2, Date startDate2, String assignee3, Date endDate2);
}
