package com.example.controller;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
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
      result.add(map);
    }
    return ok(result);
  }

  /**
   * 新建流程
   */
  @GetMapping("/new/{userId}")
  public ResponseEntity<Boolean> newTask(@PathVariable("userId") String userId) {
    System.out.println("new process");
    // 新建流程
    ProcessInstance process = runtimeService.startProcessInstanceByKey(key);
    System.out.println("processId:" + process.getId());
    // 查询当前任务
    Task task = taskService.createTaskQuery().processInstanceId(process.getId()).singleResult();
    System.out.println("taskId:" + task.getId());
    // 接受任务
    taskService.claim(task.getId(), userId);
    // 完成任务
    taskService.complete(task.getId());
    return ok(true);
  }

  /**
   * 拒绝审批
   */
  @GetMapping("/reject/{userId}/{taskId}")
  public ResponseEntity reject(@PathVariable("taskId") String taskId, @PathVariable("userId") String userId) {
    System.out.println("reject");
    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    System.out.println("taskId:" + task.getId());
    System.out.println("executionId:" + task.getExecutionId());
    // 接受任务
    taskService.claim(taskId, userId);
    Map<String, Object> taskVariables = new HashMap<>();
    taskVariables.put("approved", "false");
    HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery().processInstanceId(task.getProcessInstanceId());
    long count = query.count();
    String assignee = query.listPage(Math.toIntExact(count - 2), 1).get(0).getAssignee();
    // 完成任务
    taskService.complete(taskId, taskVariables);
    Task task1 = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
    // 指定代理人
    taskService.addCandidateUser(task1.getId(), assignee);
    return ok(true);
  }

  /**
   * 通过审批
   */
  @GetMapping("/pass/{userId}/{taskId}")
  public ResponseEntity<Boolean> pass(@PathVariable("taskId") String taskId, @PathVariable("userId") String userId) {
    Map<String, Object> map = new HashMap<>();
    map.put("approved", "true");
    // 接受任务
    taskService.claim(taskId, userId);
    // 完成任务
    taskService.complete(taskId, map);
    return ok(true);
  }

  /**
   * 重新申请
   */
  @GetMapping("/reapply/{userId}/{taskId}")
  public ResponseEntity reapply(@PathVariable("userId") String userId, @PathVariable("taskId") String taskId) {
    // 接受任务
    taskService.claim(taskId, userId);
    // 完成任务
    taskService.complete(taskId);
    return ok(true);
  }


}
