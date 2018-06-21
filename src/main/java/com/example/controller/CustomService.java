package com.example.controller;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangbin
 */
@Service
public class CustomService {

  @Resource
  private RepositoryService repositoryService;

  @Resource
  private TaskService taskService;
  @Resource
  private FormService formService;

  public List<Map<String, Object>> getMaps(List<Task> list) {
    List<Map<String, Object>> result = new ArrayList<>();
    for (Task task : list) {
      TaskFormData taskFormData = formService.getTaskFormData(task.getId());
      Map<String, Object> map = new HashMap<>();
      map.put("id", task.getId());
      map.put("name", task.getName());
      ProcessDefinition processDefinition = repositoryService.getProcessDefinition(task.getProcessDefinitionId());
      map.put("processName", processDefinition.getName());
      Map<String, Object> variables = taskService.getVariables(task.getId());
      map.put("variables", variables);
      result.add(map);
    }
    return result;
  }
}
