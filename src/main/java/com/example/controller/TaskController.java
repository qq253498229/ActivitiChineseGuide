package com.example.controller;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  private static final String key = "LeaveProcess";

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

  /**
   * 任务列表
   */
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
      Map<String, Object> variables = taskService.getVariables(task.getId());
      map.put("variables", variables);
      result.add(map);
    }
    return ok(result);
  }

  /**
   * 新建流程
   */
  @PostMapping
  public ResponseEntity<Boolean> newTask(@RequestBody Map<String, Object> param) {
    String userId = (String) param.get("userId");
    Integer day = (Integer) param.get("day");
    String message = (String) param.get("message");
    System.out.println("new process");
    // 新建流程
    ProcessInstance process = runtimeService.startProcessInstanceByKey(key);
    System.out.println("processId:" + process.getId());
    // 查询当前任务
    Task task = taskService.createTaskQuery().processInstanceId(process.getId()).singleResult();
    System.out.println("taskId:" + task.getId());
    // 接受任务
    taskService.claim(task.getId(), userId);
    Map<String, Object> taskVariables = new HashMap<>();
    taskVariables.put("day", day);
    taskVariables.put("createUser", userId);
    taskVariables.put("message", message);
    // 完成任务
    taskService.complete(task.getId(), taskVariables);
    return ok(true);
  }

  /**
   * 重新申请
   */
  @PutMapping
  public ResponseEntity reapplyTask(@RequestBody Map<String, Object> param) {
    String taskId = (String) param.get("taskId");
    String userId = (String) param.get("userId");
    Integer day = (Integer) param.get("day");
    String message = (String) param.get("message");
    // 接受任务
    taskService.claim(taskId, userId);
    Map<String, Object> taskVariables = new HashMap<>();
    taskVariables.put("day", day);
    taskVariables.put("createUser", userId);
    taskVariables.put("message", message);
    // 完成任务
    taskService.complete(taskId, taskVariables);
    return ok(true);
  }

  /**
   * 通过审批
   */
  @PostMapping("/pass")
  public ResponseEntity<Boolean> passTask(@RequestBody Map<String, Object> param) {
    String taskId = (String) param.get("taskId");
    String userId = (String) param.get("userId");
    String passMessage = (String) param.get("passMessage");
    Map<String, Object> taskVariables = new HashMap<>();
    taskVariables.put("approved", true);
    taskVariables.put("passMessage", passMessage);
    // 接受任务
    taskService.claim(taskId, userId);
    // 完成任务
    taskService.complete(taskId, taskVariables);
    return ok(true);
  }

  /**
   * 拒绝审批
   */
  @PostMapping("/reject")
  public ResponseEntity rejectTask(@RequestBody Map<String, Object> param) {
    String taskId = (String) param.get("taskId");
    String userId = (String) param.get("userId");
    String rejectMessage = (String) param.get("rejectMessage");
    System.out.println("reject");
    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    System.out.println("taskId:" + task.getId());
    System.out.println("executionId:" + task.getExecutionId());
    // 接受任务
    taskService.claim(taskId, userId);
    Map<String, Object> taskVariables = new HashMap<>();
    taskVariables.put("approved", false);
    taskVariables.put("rejectMessage", rejectMessage);
    String assignee = (String) taskService.getVariables(taskId).get("createUser");
    // 完成任务
    taskService.complete(taskId, taskVariables);
    Task task1 = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
    // 指定代理人
    taskService.addCandidateUser(task1.getId(), assignee);
    return ok(true);
  }


}
