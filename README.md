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

Create Key Store
keytool -genseckey -alias myDesKey -keypass someKeyPassword -keystore myDesKey.jceks -storepass someKeystorePassword -v -storetype JCEKS -keyalg DES
``` 

## Making sure Camel Context is running in Standalone Spring Boot

[Link Doc](https://camel.apache.org/camel-spring-boot/3.21.x/#_making_sure_camel_context_is_running_in_standalone_spring_boot)  

To ensure the Spring Boot application keeps running until being stopped or the JVM terminated, typically only need when running Spring Boot standalone, i.e. not with spring-boot-starter-web when the web container keeps the JVM running, set the camel.springboot.main-run-controller=true property in your configuration. For example in application.properties.  

```
# to keep the JVM running  
camel.springboot.main-run-controller = true  
```

## Docker Commands
```
Check Docker version
docker --version

Run container based on custom course image
docker run in28min/todo-rest-api-h2:1.0.0.RELEASE
docker run -p 5000:5000 in28min/todo-rest-api-h2:1.0.0.RELEASE
docker run -p 5000:5000 -d in28min/todo-rest-api-h2:1.0.0.RELEASE
After running this container you can access:
http://localhost:5000/hello-world
http://localhost:5000/hello-world-bean
http://localhost:5000/jpa/users/in28minutes/todos
docker run -p 5001:5000 -d in28min/todo-rest-api-h2:1.0.0.RELEASE
You can access the same previous URLs but now using port 5001.

Run a container with a different version of the custom image
docker run -p 5001:5000 -d in28min/todo-rest-api-h2:0.0.1-SNAPSHOT

See container logs
docker logs a1882f58b3d9f0d0da0df8d68da78d4a53ff51a6e17e267e2a23c9f49f63ace3
We can use just part of the container ID:
docker logs -f e8fe36

See container logs and follow changes in logs
docker logs -f a1882f58b3d9f0d0da0df8d68da78d4a53ff51a6e17e267e2a23c9f49f63ace3

List running containers
docker container ls

List all containers (running and stopped ones)
docker container ls -a

List local images
docker images

Stop container
docker container stop e8fe
docker container stop a188
```
