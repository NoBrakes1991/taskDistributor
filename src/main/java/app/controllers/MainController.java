package app.controllers;

import app.dao.Dao;
import app.entities.Task;
import app.services.EndDayReplace;
import app.services.MessageSelectedFilter;
import app.services.StartDayReplace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private Dao dao;
    static Set<String> uniqAssignee = new HashSet<>();

    @InitBinder
    public void initBinder(WebDataBinder dataBinder, Locale locale, HttpServletRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        Iterable<Task> tasks = dao.findAll();
        model.addAttribute("tasks", tasks);
        Iterable<Task> tasksForGetAssignee = dao.findAll();
        List<Task> listOfTasksForGetAssignee = new ArrayList<>();
        tasksForGetAssignee.iterator().forEachRemaining(listOfTasksForGetAssignee::add);

        for (int i = 0; i < listOfTasksForGetAssignee.size(); i++) {
            uniqAssignee.add(listOfTasksForGetAssignee.get(i).getAssignee());
        }
        model.addAttribute("uniqAssignee", uniqAssignee);
        return "index";
    }

    @RequestMapping(value = {"/taskAdder"}, method = RequestMethod.GET)
    public String viewTaskAdder() {
        return "taskAdder";
    }

    @PostMapping("add")
    public String add(@RequestParam String summary, @RequestParam String assignee, @RequestParam Date startDate, @RequestParam Date endDate, Map<String, Object> model) {
        if (startDate == null || endDate == null || assignee.isEmpty() || summary.isEmpty() || startDate.after(endDate)) {
            model.put("message", "You choose incorrect parameters! Please, check: 1)All fields is not Empty; 2) End date >= Start date.");
            return "taskAdder";
        } else {
            Task task = new Task(summary, startDate, endDate, assignee);
            dao.save(task);
            Iterable<Task> tasks = dao.findAll();
            uniqAssignee.add(task.getAssignee());
            model.put("uniqAssignee", uniqAssignee);
            model.put("tasks", tasks);
            return "redirect:/index";
        }
    }

    @PostMapping("filterByDateAndAssignee")
    public String filterDateAndAssignee(@RequestParam Date startDate, @RequestParam Date endDate, @RequestParam String period, @RequestParam String assignee, Map<String, Object> model) {
        Iterable<Task> tasks;

        if (!period.equals("")) {
            startDate = StartDayReplace.getDate(period);
            endDate = EndDayReplace.getDate(period);
        }

        if (startDate == null && endDate == null && !assignee.isEmpty()) {
            tasks = dao.findByAssignee(assignee);
        } else if (startDate != null && endDate != null && !assignee.isEmpty()) {
            tasks = dao.findByAssigneeAndStartDateBeforeAndEndDateAfterOrAssigneeAndStartDateOrAssigneeAndEndDate(assignee, endDate, startDate, assignee, startDate, assignee, endDate);
        } else if (startDate != null && endDate != null && assignee.isEmpty()) {
            tasks = dao.findByStartDateBeforeAndEndDateAfterOrStartDateOrEndDate(endDate, startDate, endDate, startDate);
        } else {
            tasks = dao.findAll();
        }

        if (!tasks.iterator().hasNext()) {
            model.put("messageNotFound", "Not found by your filter");
        }

        model.put("messageSelectedFilter", MessageSelectedFilter.getMessageSelectedFilter(assignee, startDate, endDate));
        model.put("uniqAssignee", uniqAssignee);
        model.put("tasks", tasks);
        return "index";
    }
}
