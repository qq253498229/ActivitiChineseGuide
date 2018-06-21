package com.example;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
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
  @Resource
  private FormService formService;

  @Test
  public void start() {
    // 开启请假流程
    ProcessInstance process = runtimeService.startProcessInstanceByKey("LeaveProcess");
    System.out.println(process.getId());
    System.out.println(process.getName());

    Task task = getTask(process);
    System.out.println(task.getName());
  }

  @Test
  public void startTask() {
    Task task = taskService.createTaskQuery().processInstanceId("10001").singleResult();

    System.out.println(task.getId());
    System.out.println(task.getName());

    taskService.claim(task.getId(), "worker1");
    taskService.complete(task.getId());
  }

  @Test
  @Deployment
  public void testForm() {
    ProcessInstance process = runtimeService.startProcessInstanceByKey("LeaveProcess");
    System.out.println("process start...");
    System.out.println("process id:" + process.getId());
    Task task = taskService.createTaskQuery().processInstanceId(process.getId()).singleResult();
    System.out.println("task id:" + task.getId());
    TaskFormData taskFormData = formService.getTaskFormData(task.getId());
    for (FormProperty formProperty : taskFormData.getFormProperties()) {
      String id = formProperty.getId();
      String name = formProperty.getName();
      String type = formProperty.getType().getName();
      String value = formProperty.getValue();
    }
    Object renderedTaskForm = formService.getRenderedTaskForm(task.getId());
    System.out.println(1);
  }

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
