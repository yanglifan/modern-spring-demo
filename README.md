# Introduction
This is a demo app to show features of Spring Boot.

# How Spring Boot App startup?

In `META-INF/MANIFEST.MF` of the Spring Boot fat jar, we can see the following content:

```
Manifest-Version: 1.0
Implementation-Title: spring-boot-demo
Implementation-Version: 0.0.1-SNAPSHOT
Archiver-Version: Plexus Archiver
Built-By: yanglifan
Implementation-Vendor-Id: com.github.yanglifan
Spring-Boot-Version: 1.4.3.RELEASE
Implementation-Vendor: Pivotal Software, Inc.
Main-Class: org.springframework.boot.loader.JarLauncher
Start-Class: com.github.yanglifan.demo.springboot.SpringBootDemoApplic
 ation
Spring-Boot-Classes: BOOT-INF/classes/
Spring-Boot-Lib: BOOT-INF/lib/
Created-By: Apache Maven 3.3.9
Build-Jdk: 1.8.0_65
Implementation-URL: http://projects.spring.io/spring-boot/spring-boot-
 demo/
```

We know that the value of `Main-Class` segment will be the entry class when execute `java -jar app.jar` command.

`org.springframework.boot.loader.JarLauncher` locates in spring-boot-loader module of spring-boot-tools project 

https://github.com/spring-projects/spring-boot/blob/master/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/JarLauncher.java