# Spring State Machine Example

## 1、Project Purpose
more well to test Spring State Machine flow and to get it all skills.
## 2、Project background
模拟线程状态转化，状态机
## 3、Project technology
SpringBoot、Spring StateMachine、H2database、JPA


Endpoint:
1、localhost:8082/case/
GET：query all case
POST：create a case
PUT：update a case

2、localhost:8082/case/{id}
GET：query a case by id
DELETE：delete a case by id

3、localhost:8082/case/CaseupdateByEvent
PUT：update a case status by event

状态机主要配置
pers.xue.skills.config.StateMachineConfig

