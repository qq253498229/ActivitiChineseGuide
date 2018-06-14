package com.example.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangbin
 */
@RestController
@RequestMapping("/task")
public class TaskController {

  private static final String key = "LeaveProcess";

  @Resource
  private TaskService taskService;
  @Resource
  private RuntimeService runtimeService;
  @Resource
  private IdentityService identityService;

  @GetMapping("/list/{userId}")
  public ResponseEntity list(@PathVariable("userId") String userId) {
    // todo
    List<Task> list = taskService.createTaskQuery().processInstanceId(key).list();
    return null;
  }
}
