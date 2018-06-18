package com.example.controller;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author wangbin
 */
@RestController
@RequestMapping("/task")
public class TaskController {

  private static final String key = "Leave";

  @Resource
  private TaskService taskService;
  @Resource
  private RuntimeService runtimeService;
  @Resource
  private IdentityService identityService;
  @Resource
  private RepositoryService repositoryService;
  @Resource
  private HistoryService historyService;

  @GetMapping("/list/{userId}")
  public ResponseEntity<List<Map<String, Object>>> list(@PathVariable("userId") String userId) {
    List<Task> list = taskService.createTaskQuery().taskCandidateUser(userId).list();
    List<Map<String, Object>> result = new ArrayList<>();
    for (Task task : list) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", task.getId());
      map.put("name", task.getName());
      ProcessDefinition processDefinition = repositoryService.getProcessDefinition(task.getProcessDefinitionId());
      map.put("processName", processDefinition.getName());
      result.add(map);
    }
    return ok(result);
  }

  @GetMapping("/new")
  public ResponseEntity<Boolean> newTask() {
    ProcessInstance process = runtimeService.startProcessInstanceByKey(key);
    Task task = taskService.createTaskQuery().processInstanceId(process.getId()).singleResult();
    taskService.complete(task.getId());
//    Group group = identityService.createGroupQuery().groupId("leader").singleResult();
//    taskService.addCandidateGroup(task.getId(), group.getId());
    return ok(true);
  }

  @GetMapping("/pass/{taskId}")
  public ResponseEntity<Boolean> pass(@PathVariable("taskId") String taskId) {
    taskService.setVariable(taskId, "flag", true);
    taskService.complete(taskId);
    return ok(true);
  }

  @GetMapping("/reject/{taskId}")
  public ResponseEntity reject(@PathVariable("taskId") String taskId) {
    taskService.setVariable(taskId, "flag", false);
    taskService.complete(taskId);
    return ok(true);
  }
}
