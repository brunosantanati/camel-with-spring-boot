# [Learn Apache Camel Framework with Spring Boot](https://www.udemy.com/course/apache-camel-framework-with-spring-boot/)  

## Links

[Links Downloads](https://github.com/in28minutes/course-material/blob/main/08-apache-camel/downloads.md)  
[GitHub repo](https://github.com/in28minutes/camel)  
[Camel Simple Language](https://camel.apache.org/components/3.21.x/languages/simple-language.html)  

## Commands used in the course  

```
Run container with Active MQ
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
After running the container you should access:
http://0.0.0.0:8161/
and click "Manage ActiveMQ broker". Use admin for both user and pass.

Start Kafka
docker-compose up
This command should be run in the folder where you have the docker-compose.yaml file.

Solve 'java.net.UnknownHostException: bc3cf810fa4e'
In the video we are told to edit the file:
sudo vi /private/etc/hosts
but in my case, I needed to edit this one:
sudo vi /etc/hosts
I just added this line to get the Kafka consumer application to work:
127.0.1.1       bc3cf810fa4e
``` 

## MAKING SURE CAMEL CONTEXT IS RUNNING IN STANDALONE SPRING BOOT  

[Link Doc](https://camel.apache.org/camel-spring-boot/3.21.x/#_making_sure_camel_context_is_running_in_standalone_spring_boot)  

To ensure the Spring Boot application keeps running until being stopped or the JVM terminated, typically only need when running Spring Boot standalone, i.e. not with spring-boot-starter-web when the web container keeps the JVM running, set the camel.springboot.main-run-controller=true property in your configuration. For example in application.properties.  

# to keep the JVM running  
camel.springboot.main-run-controller = true  
