package com.example;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
  @Resource
  private RuntimeService runtimeService;
  @Resource
  private TaskService taskService;

  /**
   * 最简单的一套流程处理
   */
  @Test
  @Deployment
  public void simpleProcessTest() {
    // 开启请假流程
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LeaveProcess");
    // 获取当前任务节点
    Task task = getTask(processInstance);
    System.out.println(task.getName());

    // 员工完成申请操作
    taskService.complete(task.getId());
    task = getTask(processInstance);
    System.out.println(task.getName());

    // 经理完成审批操作
    taskService.complete(task.getId());
    task = getTask(processInstance);

    // 判断任务是否结束
    assertNull(task);
  }

  /**
   * 根据流程示例查询当前任务节点
   *
   * @param processInstance 流程实例
   * @return 当前任务节点
   */
  private Task getTask(ProcessInstance processInstance) {
    return taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
  }

}
