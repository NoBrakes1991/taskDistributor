package app.controllers;

import app.dao.Dao;
import app.entities.Task;
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
    static Set <String> uniqAssignee = new HashSet<>();

    @InitBinder
    public void initBinder(WebDataBinder dataBinder, Locale locale, HttpServletRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        Iterable <Task> tasks = dao.findAll();
        model.addAttribute("tasks", tasks);

        Iterable <Task> tasksForGetAssignee = dao.findAll();
        List<Task> listOftasksForGetAssignee = new ArrayList<>();
        tasksForGetAssignee.iterator().forEachRemaining(listOftasksForGetAssignee::add);

        for (int i=0; i<listOftasksForGetAssignee.size();i++)
        {uniqAssignee.add(listOftasksForGetAssignee.get(i).getAssignee());
        }
        model.addAttribute("uniqAssignee", uniqAssignee);
        return "index";
    }

    @RequestMapping(value = { "/taskAdder" }, method = RequestMethod.GET)
    public String viewTaskAdder() {
        return "taskAdder";
    }

    @PostMapping("add")
    public String add(@RequestParam String summary, @RequestParam String assignee, @RequestParam Date startDate, @RequestParam Date endDate, Map<String, Object> model){
        Task task = new Task(summary, startDate, endDate,assignee);
        dao.save(task);
        Iterable<Task> tasks = dao.findAll();
        uniqAssignee.add(task.getAssignee());
        model.put("uniqAssignee", uniqAssignee);
        model.put("tasks", tasks);
        return "index";
    }
    @PostMapping("filterByAssignee")
    public String filterByAssignee(@RequestParam String assignee, Map<String, Object> model) {
        Iterable<Task> tasks;
        if (assignee != null && !assignee.isEmpty()) {
            tasks = dao.findByAssignee(assignee);
        } else {
            tasks = dao.findAll();
        }
        model.put("tasks", tasks);
        return "index";
    }

    @PostMapping("filterByDate")
    public String filterByDate(@RequestParam Date startDate,@RequestParam Date endDate, Map<String, Object> model) {
        Iterable<Task> tasks;
        if (startDate != null && endDate !=null) {
            tasks = dao.findByStartDateBetweenOrEndDateBetween(startDate,endDate, startDate, endDate);
        } else {
            tasks = dao.findAll();
        }
        model.put("tasks", tasks);
        return "index";
    }
    @PostMapping("filterByDateAndAssignee")
    public String filterDateAndAssignee(@RequestParam Date startDate,@RequestParam Date endDate, @RequestParam String assignee, Map<String, Object> model) {
        Iterable<Task> tasks;
        if (startDate != null && endDate !=null && assignee!=null && !assignee.isEmpty()) {
            tasks = dao.findByAssigneeAndStartDateBetweenOrAssigneeAndEndDateBetween(assignee, startDate,endDate,assignee, startDate, endDate);
        } else {
            tasks = dao.findAll();
        }
        model.put("uniqAssignee", uniqAssignee);
        model.put("tasks", tasks);
        return "index";
    }
}
