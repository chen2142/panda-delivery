# panda-delivery

A hungary-panda-like application, including front-end part and back-end part.

Front-end is developed with Vue2, divided into client and backend.
Client is html5 page for mobile users, and backend is desktop page for admin.
- For clients, order and change orders 
- For admins, set dish and publish it

Back-end is springboot project, implementing basic CRUD functions. Supports login, management, editing and deleting.

There should be a configuration file called `application.yml`, the template is written down here for reference. To start the server correctly, need to create the `src/main/resources/application.yml` like this: ↓
```yml
server:
  port: 8080 # tomcat server's default port
spring:
  application:
    name: panda-delivery
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:<your port>/panda-delivery?serverTimezone=Australia/Adelaide&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
    username: <your username>
    password: <your password> # fill in your own credentials and port number in angle brackets, mysql for example
  data:
    redis:
      host: localhost
      port: 6379 # redis' default port
  cache:
    redis:
      time-to-live: 1800000
mybatis-plus:
  configuration:
    #use camel-case to map the entity and property in database
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: ASSIGN_ID
panda-delivery:
  path: D:\ # change to an available path in your computer
```


- [ ] *Translation for front-end is to be continued.*


:)