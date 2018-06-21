package com.example.controller;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author wangbin
 */
@RestController
@RequestMapping("/test")
public class TestController {
  private static final String key = "formProcess";
  @Resource
  private TaskService taskService;
  @Resource
  private RuntimeService runtimeService;
  @Resource
  private FormService formService;

  @Resource
  private CustomService customService;

  @GetMapping("/create/{userId}")
  public ResponseEntity create(@PathVariable("userId") String userId) {
    ProcessInstance process = runtimeService.startProcessInstanceByKey(key);
    Task task = taskService.createTaskQuery().processInstanceId(process.getId()).singleResult();
    Object renderedTaskForm = formService.getRenderedTaskForm(task.getId());
//    taskService.claim(task.getId(), userId);
    taskService.complete(task.getId());
    return ok(true);
  }

  @GetMapping("/list/{userId}")
  public ResponseEntity list(@PathVariable("userId") String userId) {
    List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
    List<Map<String, Object>> maps = customService.getMaps(list);
    return ok(maps);
  }

}
