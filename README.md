UI Manager for Quartz Scheduler
==

### 任务列表
![image](https://gitee.com/trotri/picture/raw/master/quartz-ui-index.png)

### 新建任务
![image](https://gitee.com/trotri/picture/raw/master/quartz-ui-create.png)

### 任务列表
```
http://127.0.0.1:9091/index?group=分组
```

### MySQL 建表
```
运行 quartz-ui/mysql_innodb.sql
```

### MySQL application.yml
```
spring:
  quartz:
    properties:
      org:
        quartz:
          dataSource:
            default:
              URL: jdbc:mysql://127.0.0.1:3306/quartz?characterEncoding=utf-8&useSSL=false&useTimezone=true&serverTimezone=Asia/Shanghai
              user: root
              password: 123456
```
