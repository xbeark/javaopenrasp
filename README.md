# Java Open Rasp(Runtime Application Self-Protection)
# A Java Rasp Demo

### Supported Vulnerabilites

## RCE

1. deserialize vulnerability

2. danger ognl expressions

3. ProcessBuilder log

## Sql Ingection

1. MySQL

2. SQLServer

### How to use:

add "-javaagent:/path/javaopenrasp.jar" to your jvm arguments

# Java Open Rasp

这是一个java rasp的验证性demo，已验证rasp的原理及实现

### 实现的保护点

## RCE

1. 反序列化漏洞

2. Ognl表达式执行

3. ProcessBuilder log

## SQL注入

1. MySql注入保护

2. SQLServer注入保护

## 使用方法
编译后，将"-javaagent:/path/javaopenrasp.jar"添加至JVM的启动参数

E-Mail： xiongxbear@gmail.com
