# Introduction
This is a demo app to show features of Spring Boot.

# How Spring Boot works
## How Spring Boot Application startup?

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

`JarLauncher` just invokes the class which is specified by `Start-Class` in `MANIFEST.MF`. In this demo, `Start-Class` is `com.github.yanglifan.demo.springboot.SpringBootDemoApplication`. Like most of Spring Boot applications, in `main` method of `SpringBootDemoApplication`, `SpringApplication.run(SpringBootDemoApplication.class, args)` is invoked.

### The 1st important method of the class `SpringApplication`
The first important method is `initialize(Object[] sources)`. This method is invoked by `SpringApplication` constructor.

```java
public class SpringApplication {
    private void initialize(Object[] sources) {
        if (sources != null && sources.length > 0) {
            this.sources.addAll(Arrays.asList(sources));
        }
        this.webEnvironment = deduceWebEnvironment();
        setInitializers((Collection) getSpringFactoriesInstances(
                ApplicationContextInitializer.class));
        setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
        this.mainApplicationClass = deduceMainApplicationClass();
    }
}
```

This method will decide whether the current application is a web application or not.

### The 2nd important method of the class `SpringApplication`
The second important method of `SpringApplication` is `createApplicationContext()`. This method will be according to the variable `webEnvironment` to decide to use which class to create the ApplicationContext. If `webEnvironment` is true, this method will use `AnnotationConfigEmbeddedWebApplicationContext`:

```java
public class SpringApplication {
    protected ConfigurableApplicationContext createApplicationContext() {
    	Class<?> contextClass = this.applicationContextClass;
    	if (contextClass == null) {
    		try {
    			contextClass = Class.forName(this.webEnvironment
    					? DEFAULT_WEB_CONTEXT_CLASS : DEFAULT_CONTEXT_CLASS);
    		}
    		catch (ClassNotFoundException ex) {
    			throw new IllegalStateException(
    					"Unable create a default ApplicationContext, "
    							+ "please specify an ApplicationContextClass",
    					ex);
    		}
    	}
    	return (ConfigurableApplicationContext) BeanUtils.instantiate(contextClass);
    }
}
```

This method will be invoked by `SpringApplication.run(String... args)`.

After `SpringApplication` to create an `ApplicationContext`, the process will like another Spring applications. Then I will introduce the internal process of `AnnotationConfigEmbeddedWebApplicationContext`.

## How Spring Boot to start a web container?
Spring Boot use `AnnotationConfigEmbeddedWebApplicationContext` as the default web application context. `AnnotationConfigEmbeddedWebApplicationContext` will create an embedded web container. By default, it will use `TomcatEmbeddedServletContainerFactory` to create an embedded Tomcat instance.

```java
public class EmbeddedWebApplicationContext extends GenericWebApplicationContext {
    protected EmbeddedServletContainerFactory getEmbeddedServletContainerFactory() {
        // Use bean names so that we don't consider the hierarchy
        String[] beanNames = getBeanFactory()
                .getBeanNamesForType(EmbeddedServletContainerFactory.class);
        if (beanNames.length == 0) {
            throw new ApplicationContextException(
                    "Unable to start EmbeddedWebApplicationContext due to missing "
                            + "EmbeddedServletContainerFactory bean.");
        }
        if (beanNames.length > 1) {
            throw new ApplicationContextException(
                    "Unable to start EmbeddedWebApplicationContext due to multiple "
                            + "EmbeddedServletContainerFactory beans : "
                            + StringUtils.arrayToCommaDelimitedString(beanNames));
        }
        return getBeanFactory().getBean(beanNames[0],
                EmbeddedServletContainerFactory.class);
    }
}
```

There is a question. Why Spring Boot chooses Tomcat by default. The answer is related with Spring Boot auto config mechanism. `spring-boot-autoconfigure` module is very important, it provides the auto config mechanism to Spring Boot. You can find all 3rd party technologies' configuration which supported by Spring Boot officially. Of course, you can find Tomcat configuration in it:

```java
public class EmbeddedServletContainerAutoConfiguration {
	/**
	 * Nested configuration if Tomcat is being used.
	 */
	@Configuration
	@ConditionalOnClass({ Servlet.class, Tomcat.class })
	@ConditionalOnMissingBean(value = EmbeddedServletContainerFactory.class, search = SearchStrategy.CURRENT)
	public static class EmbeddedTomcat {
		@Bean
		public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
			return new TomcatEmbeddedServletContainerFactory();
		}
	}
}
```

Annotations `@ConditionalOnClass` and `@ConditionalOnMissingBean` will make a magic. When the `ApplicationContext` startup, Spring will load classes with `@Configuration` and parse annotations like `@ConditionalOnClass` and `@ConditionalOnMissingBean`. If the condition is matched, then the corresponding configuration will be effective.

```java
@Order(Ordered.HIGHEST_PRECEDENCE)
class OnClassCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context,
			AnnotatedTypeMetadata metadata) {
	    // ...
	}
}
```

Since by default, `spring-boot-starter-web` module depends on `spring-boot-starter-tomcat` module, so embedded Tomcat classes is on the classpath by default. So Spring Boot will use Tomcat be default.

Underlying, Spring uses Java byte code technology to read the annotation data into metadata objects. See `AnnotationMetadataReadingVisitor`.

# References
* [Spring Boot启动流程详解](http://zhaox.github.io/java/2016/03/22/spring-boot-start-flow)
* [spring boot应用启动原理分析](http://blog.csdn.net/hengyunabc/article/details/50120001)
