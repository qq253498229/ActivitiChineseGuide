package com.example;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangbin
 */
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @RestController
  class TestController {
    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @GetMapping("/list")
    public List<Map> list() {
      List<Task> tasks = taskService.createTaskQuery().list();
      return tasks.stream().map(t -> {
        Map<String, Object> map = new HashMap<>();
        map.put("id", t.getId());
        map.put("name", t.getName());
        return map;
      }).collect(Collectors.toList());
    }

    @GetMapping("/complete/{taskId}")
    public boolean complete(@PathVariable("taskId") String taskId) {
      taskService.complete(taskId);
      return true;
    }

    @GetMapping("/create")
    public boolean create() {
      runtimeService.startProcessInstanceByKey("LeaveProcess");
      return true;
    }

  }
  /*@Bean
  CommandLineRunner runner(
          final RepositoryService repositoryService,
          final RuntimeService runtimeService,
          final TaskService taskService
  ) {
    return args -> {
      System.out.println("Number of process definitions : "
              + repositoryService.createProcessDefinitionQuery().count());
      System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
      runtimeService.startProcessInstanceByKey("choosecourse");
      System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
    };
  }*/
}
