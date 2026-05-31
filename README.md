# 智慧小区物业管理系统 (Property Management System)

这是一个基于 Spring Boot 3 构建的现代化小区物业管理系统。系统实现了 **管理员、物业职工、小区住户** 三端联动，涵盖了住户管理、职工管理、在线报修、任务指派及图文反馈等核心业务流程。

## 🌟 核心功能

### 1. 统一认证中心
*   **多角色登录**：三端共用一个极简、安全的登录界面，支持通过下拉菜单切换身份。
*   **权限隔离**：基于 Spring MVC Interceptor 实现严格的角色权限控制，确保数据安全。

### 2. 小小区住户端 (Resident)
*   **在线报修**：住户可提交报修申请，支持上传**故障现场照片**及详细文字描述。
*   **进度追踪**：实时查看报修状态（待处理、已指派、已完成）。
*   **反馈查看**：报修完成后，可查看物业职工回传的**处理结果说明及完成照片**。

### 3. 物业管理员端 (Admin)
*   **基础数据管理**：
    *   **住户管理**：管理业主信息、房号及登录账号。
    *   **职工管理**：录入及管理物业维修人员。
    *   **管理员管理**：支持生成新的管理员账号。
*   **智能调度**：接收住户报修，并将其一键指派给特定的物业职工。
*   **全流程监管**：监控从报修提交到维修完成的整个生命周期。

### 4. 物业职工端 (Staff)
*   **任务领取**：职工登录后可查看指派给自己的维修任务。
*   **移动反馈**：维修完成后，职工可在手机端提交文字反馈并上传**维修完成后的对比照片**。

## 🛠️ 技术栈

*   **后端**：Java 17, Spring Boot 3.2.5
*   **持久层**：Spring Data JPA, Hibernate
*   **数据库**：MySQL 8.0
*   **前端模板**：Thymeleaf
*   **样式**：原生 CSS3 (响应式设计，支持移动端访问)
*   **文件存储**：本地文件系统存储 (支持图片上传)

## 🚀 快速开始

### 1. 环境准备
*   JDK 17+
*   Maven 3.6+
*   MySQL 8.0

### 2. 数据库配置
在 MySQL 中创建数据库 `property_db`：
```sql
CREATE DATABASE property_db CHARACTER SET utf8mb4;
```
修改 `src/main/resources/application.properties` 中的数据库用户名和密码。

### 3. 运行项目
```bash
mvn spring-boot:run
```
项目启动后访问：`http://localhost:8080/login`

## 🔑 测试账号

系统内置了自动初始化数据，您可以使用以下账号直接登录体验：

| 角色 | 账号 | 密码 |
| :--- | :--- | :--- |
| **物业管理员** | `admin` | `123456` |
| **物业职工** | `staff` | `123456` |
| **小区住户** | `user` | `123456` |

## 📂 项目结构

```text
src/main/java/com/example/propertymanagement/
├── config/             # 全局配置 (WebMvc, 资源映射)
├── controller/         # 控制层 (三端业务逻辑)
├── entity/             # 实体类 (JPA Mapping)
├── interceptor/        # 权限拦截器
├── repository/         # 数据访问层
└── component/          # 组件 (数据初始化)

src/main/resources/
├── static/css/         # 样式文件
├── templates/          # HTML 模板
│   ├── admin/          # 管理员页面
│   ├── staff/          # 职工页面
│   ├── user/           # 住户页面
│   └── login.html      # 统一登录页
└── application.properties # 核心配置文件
```

## 📝 许可证

本项目采用 [MIT License](LICENSE) 许可。
