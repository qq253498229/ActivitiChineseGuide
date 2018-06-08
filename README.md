# Activiti 用户手册

> v6.0.0

## 0.目录

- [1.介绍](#1.介绍)
  - [1.1.协议](#1.1.协议)
  - [1.2.下载](#1.2.下载)
  - [1.3.源码](#1.3.源码)
  - [1.4.软件需求](#1.4.软件需求)
  - [1.4.软件需求](#1.4.软件需求)
    - [1.4.1.JDK 7+](#1.4.1.JDK 7+)
    - [1.4.2.IDE](#1.4.2.IDE)
  - [1.5.报告问题](#1.5.报告问题)
  - [1.6.实验性功能](#1.6.实验性功能)
  - [1.7.内部实现类](#1.7.内部实现类)
- [2.入门](#2.入门)
  - [2.1.一分钟版本](#2.1.一分钟版本)
  - [2.2.Activiti的安装](#2.2.Activiti的安装)
  - [2.3.Activiti数据库设置](#2.3.Activiti数据库设置)



## 1.介绍

### 1.1.协议

Activiti使用了[Apache V2 协议](http://www.apache.org/licenses/LICENSE-2.0.html)

### 1.2.下载

[http://activiti.org/download.html](http://activiti.org/download.html)

### 1.3.源码

该分发包含大部分来源为jar文件。 Activiti的源代码可以在[https://github.com/Activiti/Activiti](https://github.com/Activiti/Activiti)找到

### 1.4.软件需求

#### 1.4.1.JDK 7+

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