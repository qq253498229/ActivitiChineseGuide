package com.example.controller;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author wangbin
 */
@RestController
@RequestMapping("/reimbursement")
public class ReimbursementController {

  @Resource
  private TaskService taskService;

  @Resource
  private CustomService customService;

  @GetMapping("/list/{userId}")
  public ResponseEntity list(@PathVariable("userId") String userId) {
    List<Task> list = taskService.createTaskQuery().taskOwner(userId).list();
    return ok(customService.getMaps(list));
  }
}
