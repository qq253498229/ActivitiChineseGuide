# Activiti 用户手册

> v6.0.0

## 0.目录

- [1.介绍](#1.介绍)
  - [1.1.协议](#1.1.协议)
  - [1.2.下载](#1.2.下载)
  - [1.3.源码](#1.3.源码)
  - [1.4.软件需求](#1.4.软件需求)
  - [1.4.软件需求](#1.4.软件需求)
    - [1.4.1.JDK7+](#1.4.1.JDK7+)
    - [1.4.2.IDE](#1.4.2.IDE)
  - [1.5.报告问题](#1.5.报告问题)
  - [1.6.实验性功能](#1.6.实验性功能)
  - [1.7.内部实现类](#1.7.内部实现类)
- [2.入门](#2.入门)
  - [2.1.一分钟版本](#2.1.一分钟版本)
  - [2.2.Activiti的安装](#2.2.Activiti的安装)
  - [2.3.Activiti数据库设置](#2.3.Activiti数据库设置)
- [3.配置](#3.配置)
  - [3.1.创建流程引擎](#3.1.创建流程引擎)
  - [3.2.配置流程引擎](#3.2.配置流程引擎)
  - [3.3.配置数据库](#3.3.配置数据库)
  - [3.4.配置JNDI数据源](#3.4.配置JNDI数据源)
  - [3.5.支持的数据库](#3.5.支持的数据库)
  - [3.6.创建数据表](#3.6.创建数据表)
  - [3.7.数据表名解释](#3.7.数据表名解释)
  - [3.8.数据库更新](#3.8.数据库更新)
  - [3.9.Job执行](#3.9.Job执行)
  - [3.10.激活Job执行](#3.10.激活Job执行)
  - [3.11.Mail服务器配置](#3.11.Mail服务器配置)
  - [3.12.历史配置](#3.12.历史配置)
  - [3.13.通过表达式和脚本配置](#3.13.通过表达式和脚本配置)
  - [3.14.部署缓存配置](#3.14.部署缓存配置)
  - [3.15.日志](#3.15.日志)
  - [3.16.映射诊断上下文](#3.16.映射诊断上下文)
  - [3.17.事件处理](#3.17.事件处理)
- [4.Activiti API](#4.ActivitiAPI)
  - [4.1.流程引擎API和服务](#4.1.流程引擎API和服务)
  - [4.2.异常策略](#4.2.异常策略)
  - [4.3.使用Activiti服务](#4.3.使用Activiti服务)
  - [4.4.查询API](#4.4.查询API)
  - [4.5.参数](#4.5.参数)
  - [4.6.临时参数](#4.6.临时参数)
  - [4.7.表达式](#4.7.表达式)
  - [4.8.单元测试](#4.8.单元测试)
  - [4.9.调试单元测试](#4.9.调试单元测试)
  - [4.10.Web应用中的流程引擎](#4.10.Web应用中的流程引擎)
- [9.表单](#9.表单)
  - [9.1表单属性](#9.1表单属性)

## 1.介绍

### 1.1.协议

Activiti使用了[Apache V2 协议](http://www.apache.org/licenses/LICENSE-2.0.html)

### 1.2.下载

[http://activiti.org/download.html](http://activiti.org/download.html)

### 1.3.源码

该分发包含大部分来源为jar文件。 Activiti的源代码可以在[https://github.com/Activiti/Activiti](https://github.com/Activiti/Activiti)找到

### 1.4.软件需求

#### 1.4.1.JDK7+

Activiti在高于或等于版本7的JDK上运行。
转至[Oracle Java SE下载](http://www.oracle.com/technetwork/java/javase/downloads/index.html)并单击按钮“下载JDK”。 
该页面上还有安装说明。 要验证您的安装是否成功，请在命令行上运行java -version。 这应该打印已安装的JDK版本。

#### 1.4.2.IDE

Activiti开发可以使用您选择的IDE来完成。 
如果您想使用Activiti Designer，那么您需要Eclipse Kepler或Luna。 
从[Eclipse下载页面](http://www.eclipse.org/downloads/)下载您选择的eclipse发行版。 
解压缩下载的文件，然后您应该能够使用eclipse目录中的eclipse文件来启动它。 
在本用户指南中，还有一节介绍如何[安装我们的eclipse设计器插件](#12.1.安装)。

### 1.5.报告问题

每个有自尊心的开发者都应该阅读[如何以聪明的方式提问](http://www.catb.org/~esr/faqs/smart-questions.html)。

完成之后，您可以在[用户论坛](http://forums.activiti.org/en/viewforum.php?f=3)上发布问题和评论，并在我们的[JIRA问题跟踪器](https://activiti.atlassian.net/)中创建问题。

> 即使Activiti在GitHub上托管，也不应使用GitHub的问题系统报告问题。 如果您想报告问题，请勿创建GitHub问题，但请使用[我们的JIRA](https://activiti.atlassian.net/)。

### 1.6.实验性功能

实验特征标有 **[EXPERIMENTAL]** 的部分不应被视为稳定的功能。

所有具有 **.impl** 的类。 在包名中是内部实现类，都不能被认为是稳定的。 
但是，如果用户指南提到这些类为配置值，则它们可以是可以使用的，并且可以被认为是稳定的。

### 1.7.内部实现类

在jar文件中，包含 **.impl** 的包中的所有类。 （例如 **org.activiti.engine.impl.db** ）是实现类，应该被认为是内部的。 
对实现类中的类或接口没有给出稳定性保证。

## 2.入门

### 2.1.一分钟版本

从[Activiti网站](https://www.activiti.org/)下载Activiti UI WAR文件后，请按照以下步骤使用默认设置运行演示设置。 
您需要一个可用的[Java运行环境](http://www.oracle.com/technetwork/java/javase/downloads/index.html)和[Apache Tomcat](http://tomcat.apache.org/download-80.cgi)
（实际上，任何Web容器都可以工作，因为我们只依赖servlet功能，但我们主要是在Tomcat上进行测试）。

- 将下载的activiti-app.war复制到Tomcat的webapps目录。

- 通过在Tomcat的bin文件夹中运行startup.bat或startup.sh脚本启动Tomcat

- 当Tomcat开始打开浏览器并转到[http://localhost:8080/activiti-app](http://localhost:8080/activiti-app)。 用管理员和密码测试登录。

就是这些了！ Activiti UI应用程序默认使用内存H2数据库，如果您想使用其他数据库配置，请阅读[更长的版本](#2.2.Activiti的安装)。

### 2.2.Activiti的安装

要安装Activiti，你需要一个可用的[Java运行环境](http://www.oracle.com/technetwork/java/javase/downloads/index.html)和[Apache Tomcat](http://tomcat.apache.org/download-80.cgi)。 
还要确保JAVA_HOME系统变量设置正确。 这些如何实现取决于你的操作系统。

要使Activiti UI和REST Web应用程序运行，只需将从Activiti下载页面下载的WAR复制到Tomcat安装目录中的 **webapps** 文件夹。 默认情况下，UI应用程序使用内存数据库运行。

演示用户:

用户名 | 密码 | 权限
---|---|---
admin | test | admin

现在您可以访问以下Web应用程序：

web应用名 | URL | 描述
---|---|---
Activiti UI | [http://localhost:8080/activiti-app](http://localhost:8080/activiti-app) | 流程引擎用户控制台。 使用此工具启动新流程，分配任务，查看和声明任务等。

### 2.3.Activiti数据库设置

### 2.4.导入Activiti jar及其依赖项

### 2.5.下一步

## 3.配置

### 3.1.创建流程引擎

## 9.表单

Activiti为您的业务流程的手动步骤添加表单提供了一种方便灵活的方式。 我们支持两种使用表单的策略：使用表单属性和外部表单呈现。

### 9.1表单属性

与业务流程相关的所有信息都包含在流程变量本身中，或者通过流程变量进行引用。 Activiti支持将复杂的Java对象作为流程变量（如Serializable对象，JPA实体或整个XML文档）存储为字符串。

启动流程并完成用户任务是人们参与流程的地方。与人交流需要在某些UI技术中呈现表格。为了简化多种UI技术，流程定义可以包含将流程变量中的复杂Java类型对象转换为属性的Map <String，String>的逻辑。

任何UI技术都可以使用暴露属性信息的Activiti API方法在这些属性之上构建表单。这些属性可以为流程变量提供一个专门的（和更有限的）视图。例如，FormData返回值中提供了显示表单所需的属性

```
StartFormData FormService.getStartFormData(String processDefinitionId)
```

或

```
TaskFormdata FormService.getTaskFormData(String taskId)
```

默认情况下，内置表单引擎会查看属性以及流程变量。 因此，如果任务表单属性与过程变量1-1匹配，则无需声明任务表单属性。 例如，使用以下声明：

```
<startEvent id="start" />
```

当执行到达startEvent时，所有进程变量都可用，但是

```
formService.getStartFormData(String processDefinitionId).getFormProperties()
```

将是空的，因为没有定义特定的映射。

在上面的例子中，所有提交的属性将被存储为过程变量。这意味着只需在表单中添加一个新的输入字段，就可以存储一个新的变量。

属性是从过程变量派生的，但它们不必存储为过程变量。例如，一个流程变量可以是Address类的JPA实体。并且UI技术使用的表单属性StreetName可以与表达式＃{address.street}

类似地，用户应该在表单中提交的属性可以作为过程变量存储，也可以作为其中一个过程变量的嵌套属性存储为UEL值表达式，例如， ＃{街道地址} 。

模拟提交的属性的默认行为是它们将被存储为流程变量，除非formProperty声明另外指定。

也可以将类型转换作为表单属性和流程变量之间的处理的一部分应用。

例如：

```
<userTask id="task">
  <extensionElements>
    <activiti:formProperty id="room" />
    <activiti:formProperty id="duration" type="long"/>
    <activiti:formProperty id="speaker" variable="SpeakerName" writable="false" />
    <activiti:formProperty id="street" expression="#{address.street}" required="true" />
  </extensionElements>
</userTask>
```

- 表单属性空间将以字符串形式映射到流程变量空间

- 表单属性持续时间将作为java.lang.Long映射到进程变量持续时间

- 表单属性说话者将被映射到流程变量SpeakerName。 它只会在TaskFormData对象中可用。 如果属性发言者被提交，ActivitiException将被抛出。 模拟，属性可读=“假”，一个属性可以从FormData中排除，但仍然在提交中处理。

- 表单属性街道将以字符串映射到过程变量地址中的Java bean属性街道。 如果没有提供该属性，required =“true”将在提交期间抛出异常。

也可以将类型元数据作为从方法StartFormData FormService.getStartFormData（String processDefinitionId）和TaskFormdata FormService.getTaskFormData（String taskId）返回的FormData的一部分提供。

我们支持以下表单属性类型：

- 字符串（org.activiti.engine.impl.form.StringFormType

- long（org.activiti.engine.impl.form.LongFormType）

- 枚举（org.activiti.engine.impl.form.EnumFormType）

- 日期（org.activiti.engine.impl.form.DateFormType）

- 布尔（org.activiti.engine.impl.form.BooleanFormType）

对于声明的每个表单属性，下列FormProperty信息将通过
**List<FormProperty> formService.getStartFormData(String processDefinitionId).getFormProperties()**
和
**List<FormProperty> formService.getTaskFormData(String taskId).getFormProperties()**
提供

```java
public interface FormProperty {
  /** the key used to submit the property in {@link FormService#submitStartFormData(String, java.util.Map)}
   * or {@link FormService#submitTaskFormData(String, java.util.Map)} */
  String getId();
  /** the display label */
  String getName();
  /** one of the types defined in this interface like e.g. {@link #TYPE_STRING} */
  FormType getType();
  /** optional value that should be used to display in this property */
  String getValue();
  /** is this property read to be displayed in the form and made accessible with the methods
   * {@link FormService#getStartFormData(String)} and {@link FormService#getTaskFormData(String)}. */
  boolean isReadable();
  /** is this property expected when a user submits the form? */
  boolean isWritable();
  /** is this property a required input field */
  boolean isRequired();
}
```

例如：

```xml
<startEvent id="start">
  <extensionElements>
    <activiti:formProperty id="speaker"
      name="Speaker"
      variable="SpeakerName"
      type="string" />

    <activiti:formProperty id="start"
      type="date"
      datePattern="dd-MMM-yyyy" />

    <activiti:formProperty id="direction" type="enum">
      <activiti:value id="left" name="Go Left" />
      <activiti:value id="right" name="Go Right" />
      <activiti:value id="up" name="Go Up" />
      <activiti:value id="down" name="Go Down" />
    </activiti:formProperty>

  </extensionElements>
</startEvent>
```

所有这些信息都可以通过API访问。 类型名称可以通过 **formProperty.getType().getName()** 获得。 
而且即使日期模式可以通过 **formProperty.getType().getInformation("datePattern")** 获得，而枚举值可以通过 **formProperty.getType().getInformation("values")** 获得。

Activiti浏览器支持表单属性，并将相应的窗体定义呈现表单。 以下XML片段