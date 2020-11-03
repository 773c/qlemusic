# qlemusic
😄😄😄😄😄😄
## 前言
qlemusic想要做一个关于音乐交流的系统，采用现阶段流行的技术实现。
## 项目文档
编写中.....
## 项目介绍
`qlemusic`是一个关于音乐交流的社区论坛系统。包含了前台音乐系统和后台管理系统。基于SpringBoot+Mybatis实现的，使用nginx+tomcat进行部署。前台音乐系统包括了用户、音乐展示、音乐发布、音乐点赞、音乐评论模块等等。后台管理系统包括用户管理、评论管理、权限管理、音乐管理、设置等模块
### 项目模块结构
````
qlemusic
├── qlemusic-admin -- 后台音乐管理系统
├── mall-common -- 通用代码
├── mall-portal -- 前台音乐系统
└── mall-security -- shiro集成用户模块
````
### 项目演示
#### 前台音乐系统
项目演示地址：http://www.eiqle.com <br/>
前端项目`qlemusic-portal-web`地址：https://github.com/773c/qlemusic-portal-web
#### 后台管理系统
项目演示地址：敬请期待...😄 <br/>
前端项目`qlemusic-admin-web`地址：https://github.com/773c/qlemusic-admin-web
### 技术说明
| 名称 | 说明 |
|--|--|
| SpringBoot | 容器+MVC框架 |
| Mybatis | ORM框架 |
| Shiro | 认证授权框架 |
| JWT | JWT登录加密 |
| PageHelper | MyBatis分页插件 |
| Swagger-UI | 文档生成工具 |
| Hibernator-Validator | 验证框架 |
| Redis | 缓存 |
| Oauth2.0 | 第三方应用授权 |
| Druid | 数据库连接池 |
| OSS | 阿里云对象存储 |
### 环境搭建
| 工具 | 说明 |
|--|--|
| IDEA | 开发IDE |
| Navicat | 数据库连接工具 |
| Postman | API接口调试工具 |
| SecureCRT | Linux远程连接工具 |
| Xftp | Linux远程连接文件传输工具 |
| RedisDesktop | redis连接工具 |
| PowerDesigner | 数据库设计工具 |
### 开发环境
| 工具 | 版本号 |
|--|--|
| JDK | 1.8 |
| Mysql | 5.7 |
| Redis | 5.0 |
| Nginx | 1.14 |
