package app.controllers;

import app.dao.Dao;
import app.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




import java.util.*;

@Controller
public class MainController {
    @Autowired
    private Dao dao;
    private static List<Task> taskList = new ArrayList<>();

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        Iterable<Task> tasks = dao.findAll();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @RequestMapping(value = { "/taskAdder" }, method = RequestMethod.GET)
    public String viewTaskAdder(Model model) {

        Iterable<Task> tasks = dao.findAll();
        model.addAttribute("tasks", tasks);

        return "taskAdder";
    }

    @PostMapping
    public String add(@RequestParam String summary, @RequestParam String assignee, @RequestParam Date startDate, @RequestParam Date endDate, Map<String, Object> model){
        Task task = new Task(summary, startDate, endDate,assignee);
        dao.save(task);
        Iterable<Task> tasks = dao.findAll();
        model.put("tasks", tasks);
        return "index";
    }

}
