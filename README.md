# 智慧小区物业管理系统 (Property Management System)

基于 Spring Boot 3 构建的现代化小区物业管理系统，实现 **管理员、物业职工、小区住户** 三端联动，覆盖楼栋房屋管理、住户与职工管理、在线报修派单、考勤打卡审计、投诉建议、社区公告、密码重置等完整业务流程。

## 🌟 核心功能

### 1. 统一认证中心
- **多角色登录**：三端共用统一登录页，通过角色下拉切换管理员 / 住户 / 职工身份。
- **权限隔离**：基于 Spring MVC Interceptor 实现三角色权限拦截，防止未登录与越权访问。
- **安全加密**：使用 Spring Security 的 `BCryptPasswordEncoder` 对密码进行哈希存储。

### 2. 物业管理员端 (Admin)
- **基础数据管理**
  - 楼栋管理：增删改查楼栋信息。
  - 房屋管理：按楼栋查看房间，维护房间号、单元、面积、状态（空闲 / 已入住）。
  - 住户管理：维护住户信息，支持住户与房间绑定 / 解绑。
  - 职工管理：维护物业维修人员信息。
  - 管理员管理：新增管理员账号。
- **报修任务调度**：查看住户报修记录，指派职工处理，更新报修状态，删除记录。
- **公告与投诉**：发布面向全体 / 住户 / 职工的公告；查看并回复住户投诉建议。
- **考勤审计**：查看职工与管理员的打卡记录；支持单条 / 批量逻辑删除，并记录审计日志。
- **用户密码管理**：按角色 / 关键词搜索住户或职工，查看详情，重置密码并记录变更日志。

### 3. 小区住户端 (Resident)
- **服务大厅**：展示欢迎信息、绑定房产、功能快捷入口及最新公告。
- **在线报修**：提交设施报修，支持上传故障现场照片、填写联系电话与故障描述。
- **进度追踪**：实时查看报修状态（待处理 / 已指派 / 已完成）。
- **评价反馈**：报修完成后对职工服务进行星级评分和文字评价。
- **投诉建议**：提交标题与内容，查看处理进度与管理员回复。
- **个人资料**：修改姓名、电话、登录密码。
- **社区公告**：查看面向住户或全体人员的公告。

### 4. 物业职工端 (Staff)
- **我的任务**：查看管理员指派给自己的待处理任务与已完成任务。
- **任务完成**：维修完成后填写处理说明并上传完成对比照片，系统自动更新报修状态。
- **考勤打卡**：支持上班打卡与下班打卡，系统根据时间自动判断正常 / 迟到 / 早退。
- **内部公告**：查看面向职工或全体人员的公告。

### 5. 系统初始化
- 应用首次启动时，`DataInitializer` 会自动创建测试管理员、楼栋房间、住户与职工账号，可直接登录体验。

## 🛠️ 技术栈

- **后端**：Java 17, Spring Boot 3.2.5
- **持久层**：Spring Data JPA, Hibernate
- **数据库**：MySQL 8.0（运行期）；H2（测试期）
- **安全**：Spring Security（仅密码加密），Session + 自定义 Interceptor 认证授权
- **前端模板**：Thymeleaf
- **样式**：原生 CSS3，响应式布局，支持桌面端与移动端访问
- **文件存储**：本地文件系统（`uploads/` 目录），用于报修图片与完成反馈图片
- **构建工具**：Maven 3.6+
- **测试**：JUnit 5 + Spring Boot Test + H2 内存数据库

## 🚀 快速开始

### 1. 环境准备
- JDK 17+
- Maven 3.6+
- MySQL 8.0

### 2. 数据库配置
在 MySQL 中创建数据库：

```sql
CREATE DATABASE property_db CHARACTER SET utf8mb4;
```

修改 `src/main/resources/application.properties` 中的数据库连接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/property_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=你的密码
```

### 3. 运行项目

```bash
mvn spring-boot:run
```

启动后访问：http://localhost:8081/login

Hibernate 会自动根据实体类创建 / 更新表结构，首次启动会写入测试数据。

## 🔑 测试账号

系统启动时会自动初始化以下账号，可直接登录体验：

| 角色 | 账号 | 密码 |
| :--- | :--- | :--- |
| 物业管理员 | `admin` | `123456` |
| 物业管理员 | `admin2` | `654321` |
| 审计管理员 | `audit` | `audit123` |
| 小区住户 | `zhangsan` | `user123` |
| 小区住户 | `lisi` | `user456` |
| 物业职工 | `staff` | `staff123` |
| 物业职工 | `repairman` | `repair123` |

> 提示：更多初始住户（`wangwu`、`zhaoliu`、`qianqi`）和职工（`security`、`manager`）可在登录后查看管理员端。

## 🧪 运行测试

项目使用 H2 内存数据库进行单元与集成测试，执行：

```bash
mvn test
```

测试覆盖：实体类、Repository、Controller、Interceptor。

## 📂 项目结构

```text
src/main/java/com/example/propertymanagement/
├── component/          # 数据初始化组件
├── config/             # 全局配置（WebMvc、资源映射、Security）
├── controller/         # 控制层（认证、管理员、住户、职工、考勤）
├── entity/             # JPA 实体类
├── exception/          # 全局异常处理
├── interceptor/        # 权限拦截器
└── repository/         # Spring Data JPA 数据访问层

src/main/resources/
├── static/css/         # 样式文件（admin.css / user.css / form-standard.css）
├── static/js/          # 前端交互脚本
├── templates/          # Thymeleaf 页面模板
│   ├── admin/          # 管理员页面
│   ├── staff/          # 职工页面
│   ├── user/           # 住户页面
│   ├── error.html      # 错误页
│   └── login.html      # 统一登录页
└── application.properties

src/test/
├── java/               # 单元与集成测试
└── resources/
    └── application.properties   # H2 测试数据库配置
```

## ⚠️ 注意事项

- 上传的图片默认保存在项目根目录的 `uploads/` 文件夹中，生产环境建议替换为对象存储或独立文件服务。
- 当前密码规则：长度不少于 6 位，仅支持字母、数字和下划线。
- 考勤规则：9 点前打卡为正常，9 点及之后为迟到；18 点及之后签退为正常，18 点前为早退。

## 📝 许可证

本项目采用 [MIT License](LICENSE) 许可。
