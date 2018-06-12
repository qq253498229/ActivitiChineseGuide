package com.example;

import org.activiti.engine.*;
import org.activiti.engine.form.FormData;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitiTest {
  /**
   * 获取流程configuration信息
   */
  @Test
  public void test0() {
    getConfig();
  }

  /**
   * 部署并获取流程definition信息
   */
  @Test
  public void test00() {
    ProcessEngine engine = getConfig().buildProcessEngine();
    getProcessDefinition(engine);
  }

  @Test
  public void test() {
    ProcessEngine engine = getConfig().buildProcessEngine();

    /*
    部署流程实例
     */
    RepositoryService repositoryService = engine.getRepositoryService();
    Deployment deployment = repositoryService.createDeployment()
            .addClasspathResource("process/choosecourse.bpmn20.xml").deploy();
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.getId()).singleResult();
    System.out.println(
            "Found process definition ["
                    + processDefinition.getName() + "] with id ["
                    + processDefinition.getId() + "]");

    RuntimeService runtimeService = engine.getRuntimeService();
    TaskService taskService = engine.getTaskService();
    FormService formService = engine.getFormService();
    HistoryService historyService = engine.getHistoryService();

    // 获取流程实例
    ProcessInstance choosecourse = runtimeService.startProcessInstanceByKey("choosecourse");

    List<Task> managers = taskService.createTaskQuery().taskCandidateGroup("managers").list();
    for (Task task : managers) {
      System.out.println("Processing Task [" + task.getName() + "]");
      Map<String, Object> variables = new HashMap<>();
      FormData formData = formService.getTaskFormData(task.getId());
    }
  }

  private void getProcessDefinition(ProcessEngine processEngine) {
    String pName = processEngine.getName();
    String ver = ProcessEngine.VERSION;
    System.out.println("ProcessEngine [" + pName + "] Version: [" + ver + "]");

    RepositoryService repositoryService = processEngine.getRepositoryService();
    Deployment deployment = repositoryService.createDeployment()
            .addClasspathResource("process/onboarding.bpmn20.xml").deploy();
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.getId()).singleResult();
    System.out.println(
            "Found process definition ["
                    + processDefinition.getName() + "] with id ["
                    + processDefinition.getId() + "]");
  }


  private ProcessEngineConfiguration getConfig() {
    return new StandaloneProcessEngineConfiguration()
            .setJdbcUrl("jdbc:mysql://localhost:3306/activiti?autoReconnect=true")
            .setJdbcUsername("root")
            .setJdbcPassword("root")
            .setJdbcDriver("com.mysql.jdbc.Driver")
            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
  }
}
