My notes for this course.

[toc]

# 0 Introduction

## 0.1 What is Spring framework ?

Spring framework is a mature, powerful and highly flexible framework focused on building web applications in Java.



## 0.2 Spring Vs Java EE

After Java EE8, Java EE was handed-over to a community and named as Jakarta EE

##### 



## 0.3 Evolution of Spring and release timeline of Spring

The first version of Spring was written by Rod Johnson, who released the framework with the publication of his book Expert One-on-One J2EE Design and Development in October 2002.



# 1. Spring Core

Spring Core is the heart of entire Spring. It contains some base framework classes, princinples and mechanisms.

The entire Spring Framework and other projects of Spring are developed on top of the Spring Core.

Spring Core contains following important components:

- IoC (Inversion of Control)
- DI (Dependency Injection)
- Beans
- Context
- SpEl (Spring Expression Language)
- IoC Container

## 1.0.1 Spring Core Concepts like Inversion of Control (IoC)

- IoC is a software design principle, independent of languages, which does not actually created the objects but describes the way in which object is being created.
- IcC is the principle, where the control flow of a program is inverted: instead of the programmer controlling the flow of a program, the framework or service takes control of the program flow.
- Dependency Injection is the pattern through which Inversion of Control achieved.
- Through dependency injection, the responsibility of creating objects is shifted from application to the Spring IoC container. It reduces coupling between multiple objects as it is dynamically injected by the framework. 

DI is the implementation of IoC

## 1.0.2 Advantages of IoC & DI

- Loose coupling between components **Main advantage of Spring**
- Minimizes the amount of code in your application
- Makes unit testing easy with different mocks
- Increased system maintainability & module reusability
- Allows concurrent or independent development
- Replacing modules has no side effect on other modules 

## 1.0.3 Spring Beans

Any normal Java class (POJO plain old java object) that is instantiated, assembled, and otherwise managed by Spring IoC container is called **Spring Bean**.

These beans are created by using **XML configs** and **Annotations**.

Spring IoC Container managers the lifecycle of Spring Bean scope and injecting any required dependencies in the bean.

Context is like a memory location of the app in which we add all the object instances that we want the framework to manage. 

The SpEL provides a powerful expression language for querying and manipulating an object graph at runtime like setting and getting property values, property assignment, method invocation etc.

## 1.0.4 Spring IoC Container

------------------------------------------Application classes(POJOs)

​                                                                      ↓

Configuration instructions→|Spring Application Context |

​                                                                       ↓

​                                                      Creates fully configured app, ready for use



Config is how to create Beans

Context is the Container for Beans



## 1.1 Add new beans to spring context

### 1.1.1 @Bean Annotation

```java
@Bean
Vehicle vehicle(){
    var veh = new Vehicle();
    veh.setName("Audi");
    return veh;
}
```

### 1.1.2 NoUniqueBeanDefinitionException

```java
 @Bean(name = "audi")
    Vehicle vehicle1() {
        Vehicle veh = new Vehicle();
        veh.setName("Audi");
        return veh;
    }

    @Bean(name = "honda")
    Vehicle vehicle2() {
        Vehicle veh = new Vehicle();
        veh.setName("Honda");
        return veh;
    }

    @Bean(name="ferrari")
    Vehicle vehicle3(){
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Ferrari");
        return vehicle;

    }
```

Solutions

- Fetch the bean from the context by mentioning it's name

  ```java
  context.getBean("vehicle1",Vehicle.class);
  ```

- `@Primary` Annotation to indicate the default bean

### 1.1.3 Stereotype annotations

Using @ComponentScan annotation over the configuration class, instruct Spring on where to find the classes marked with stereotype annotations.

```java
@Configuration
@ComponentScan(basePackages = "com.example.beans")
public class ProjectConfig {
}
```

#### 1.1.3.1 @Component

@Component is one of the most commonly used stereotype annotation to create and add a bean to the Spring context by wiring less code compared to @Bean option.

It's used as general on top of any Java class. It is the base for other annotations.

####  1.1.3.2 @Service

can be used on top of the classes inside the service layer especially where we write business logic and make external API calls.

#### 1.1.3.3 @Repository

Can be used on top of the classes which handles the code related to Database access related operations like Insert, Update, Delete etc.

#### 1.1.3.4 @Controller

can be used on top of the classes inside the Controller layer of MVC applications.

### 1.1.4 @Bean vs @Component

### 1.1.5 @PostConstruct & @PreDestroy

In class with @Component annotation, @PostConcstruct & @PreDestroy can be used before a method, which instructs Spring to execute the method after it finishes creating the bean or call this method just before clearing and destroying the context.

Spring borrows these two annotations from Java EE 

`import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy; `

### 1.1.6 Add new Beans programmatically

From Sping 5, registerBean() can be invoked inside the context object.

`context.registerBean("beanName",beanType.class,instanceSupplier);`

### 1.1.7 Add new Beans using XML configs

xml file

```xml
<bean id="vehicle" class="com.example.beans.Vehicle">
    <property name="name" value="Honda" />
</bean>
```

Create Bean

```java
var context = new ClassPathXmlApplicationContext("beans.xml");
Vehicle vehicle = context.getBean(Vehicle.class);
System.out.println(vehicle.getName())
```

## 1.2 Beans wiring inside Spring

### 1.2.1 Method call

```java
    @Bean
    public Vehicle vehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Toyota");
        return vehicle;
    }


    @Bean
    public Person person() {
        Person person = new Person();
        person.setName("Lucy");
        person.setVehicle(vehicle());
        return person;
    }

```

### 1.2.2 Method parameters

```java
    @Bean
    public Vehicle vehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Toyota");
        return vehicle;
    }

    @Bean
    public Person person(Vehicle vehicle) {
        Person person = new Person();
        person.setName("Lucy");
        person.setVehicle(vehicle);
        return person;
    }
```

### 1.2.3 @Autowired on class fields

```java
@Autowired
private Vehicle vechile;
```

Spring injects/auto-wire the vehicle bean to person bean through a class field and dependency injection.

It is **not** recommended for production usage as we can't mark the fields as final.

**`@Autowired(required = false)`** will help to avoid the NoSuchBeanDefinitionException if the bean is not available during Autowiring process.

### 1.2.4 @Autowired on setter method

```java
@Component
public class Person {

    private String name="Lucy";
...
    @Autowired
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
```

It is **not** recommended for production usage as we can't mark the fields as final and not readable friendly.

### 1.2.5 @Autowired with constructor

```java
@Component
public class Person {

    private String name="Lucy";
	private final Vehicle vehicle;
    
    @Autowired
    public Person(Vehicle vehicle){
        System.out.println("Person bean created by Spring");
        this.vehicle = vehicle;
    }
    ...
}
```

From Spring version 4.3, when we only have one constructor in the class, writing the @Autowired annotation is optional.

### 1.2.6 How Autowiring works with multiple Beans of same type

If the Spring context has multiple beans of same class type, then Spring will try to auto-wire based on the parameter name/field name that we use while configuring autowiring annotation.

- Specify the bean name: `public Person( Vehicle vehicle1){...}`
- @Primary annotation will be the default one
- Using @Qualifier annotation `public Person(@Qualifier("vehicle2" Vehicle vehicle)){...}`

### 1.2.7 Circular dependencies

If 2 beans are waiting for each to create inside the Spring context in order to do auto-wiring.

E.G.

Where Person has a dependency on Vehicle and Vehicle has a dependency on Person. In such scenarios, Spring will throw **UnsatisfiedDependencyException** due to circular reference.

## 1.3 Bean Scopes inside Spring

There are 5 bean scopes inside Spring

1. Singleton
2. Prototype
3. Request
4. Session
5. Application

3-5 are Web scopes

### 1.3.1 Singleton bean scope

- Singleton is the **default** scope of a bean in Spring. In this scope, for a single bean we always get a same instance when you refer or autowire inside your application.

- Unlike singleton design pattern where we have only 1 instance in entire app, inside singleton scope Spring will make sure to have **only 1 instance per unique bean**. For example, if you have multiple beans of same type, then Spring Singleton scope will maintain 1 instance per each bean declared of the same type.

### Race condition

A race condition occurs when two threads access a shared variable at the same time. The first thread reads the variable, and the second thread reads the same value from the variable. Then the first thread and second thread perform their operation on the value, and they race to see which thread can write the value last to the shared variable. The value of the thread that writes its value last is preserved, because the thread is writing over the value that the previous thread wrote.

### Use case of singleton beans

- Since the same instance of singleton bean will be used by multiple threads inside your application, it is very important that these beans are immutable
- This scope is more suitable for beans which handles service layer, repository layer business logics.

1. Building mutable singleton beans, will result in the race conditions inside multi thread environments
2. There are ways to avoid race conditions due to mutable singleton beans with the help of synchronization
3. But it is not recommended, since it brings lot of complexity and performance issues inside your app. So please don't try to build mutable singleton beans.

### Eager & lazy instantiation

- By default Spring will create all singleton beans eagerly during the startup of the application itself. This is called Eager instantiation.
- The initialization of the singleton beans can be changed to be lazily only when the application is trying to refer to the bean.

```java
@Component
@Lazy
public class Person{...}
```

Eager vs Lazy

| Eager instantiation                                          | Lazy instantiation                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| **Default inside Spring**                                    | @Lazy annotation                                             |
| Bean will be created during startup                          | Bean will be created when the app is trying to refer the bean for the first time |
| The server will not start if bean is not able to create      | Application will throw an exception runtime if bean creation is failed due to any dependent exceptions |
| Spring context will occupy lot of memory if all beans are eager | Performance will be impacted if all beans are lazy           |
| Eager can be followed for all the beans which are required very commonly inside a application | Lazy can be followed for the beans that are used in a very remote scenario inside a application |

### 1.3.2 Prototype bean scope

- With prototype scope, every time we request a reference of a bean, Spring will create a new object instance and provide.
- Prototype scope is rarely used inside the applications and we can use this scope only in the scenarios where your bean will frequently change the state of the data which will result race conditions inside multi thread environment. Using prototype scope will not create any race conditions.

```java
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VehicleServices{...}
```

### 1.3.3 Singleton vs prototype

| Singleton scope                                              | Prototype scope                                              |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Default                                                      | @Scope(BeanDefinition.SCOPE_PROTOTYPE)                       |
| The same object instance will be returned every time we refer a bean inside the code | New object instance will be returned every time we refer a bean inside the code |
| We can configure to create the beans during the start up or when the first time referred | Spring always creates new object when we try to refer the bean. No eager instantiation is possible. |
| Immutable objects can be idle for Singleton scope            | Mutable objects can be idle for prototype scope              |
| Most commonly used                                           | Rarely used                                                  |

## 1.4 Aspect-Oriented Programming(AOP)

- An aspect is simply a piece of code the Spring framework executes when you call specific methods inside your app.
- Spring AOP enables AOP in spring applications. In AOP, aspects enable the modularization of concerns such as transaction management, logging or security that cut across multiple types and objects( often termed crosscutting concerns).

1. AOP provides the way to dynamically add the cross-cutting concern before, after or around the actual logic using simple pluggable configurations.
2. AOP helps in separating and maintaining many non-business logic related code like logging, auditing, security, transaction management.
3. AOP is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns. It does this by adding additional behavior to existing code without modifying the code itself.

### AOP Jargons

When we define an Aspect or doing configurations, we need to follow 3Ws

- WHAT-> Aspect
  - What code or logic we want the Spring to execute when you call a specific method. This is called as **Aspect**
- WHEN-> Advice
  - When the Spring need to execute the given Aspect. For example is it before or after the method call. This is called as **Advice**.
- WHICH-> Pointcut
  - Method inside App that framework need to intercept and execute the given Aspect. This is called as a **Pointcut**.
- **Join point**: which defines the event that triggers the execution of an aspect. Inside Spring, this event is always a method call.
- **Target object**: is the bean that declares the method/pointcut which is intercepted by an aspect.

### AOP implementation

Developer want **some logic** ***`Aspect`*** to be executed **before** ***`Advice`*** each **execution** ***`Joinpoint`*** of method **playMusic()** ***`Pointcut`*** present inside the bean **VehicleServices** ***`Target Object`***.

### Weaving inside AOP

- When we are implementing AOP inside our App using Spring framework, it will intercept each method call and apply the logic defined in the Aspect.
- Spring does this with the help of **proxy object**. So we try to invoke a method inside a bean, Spring instead of directly giving reference of the bean instead it will give proxy object that will manage the each call to a method and apply the aspect logic. This process is called **Weaving**.

Without AOP, method is directly called and no interception by Spring

intercept -> Proxy object of VehicleServices Bean -> delegate -> VehicleServices Bean {void playMusik(){//Business logic}}

With AOP, method executions will be intercepted by proxy object and aspect will be executed. Post that actual method invocation will be happen.

### Advice types inside AOP

- @Before: runs before a matched method execution
- @AfterReturning: runs when a matched method execution completes normally
- @AfterThrowing: runs when a matched method execution exits by throwing an exception
- @After: runs no matter how a matched method execution exits
- @Around: runs "around" a matched method execution. It has the opportunity to do work both before and after the method runs and to determin when, how and even if the method actually gets to run at all.

### Configuring Advices inside AOP with AspectJ

- We can use AspectJ pointcut expression to provide details to Spring about what kinds of method it needs to intercept by mentioning details around **modifier, return type, name pattern, package name pattern, params pattern, exceptions pattern etc.**
- execution(**modifiers-pattern?** **ret-type-pattern?** **declaring-type-pattern?**  **name-pattern(param-pattern)?** **throws-pattern?**)

```java
@Configuration
@ComponentScan(basePackages = {"com.example.implementation","com.example.services","com.example.aspects"})
@EnableAspectJAutoProxy
public class ProjectConfig{}
```

```java
@Aspect
@Component
public class LoggerAspect{
    @Around("execution(* com.example.services.*.*(..))")
    public void log(ProceedingJoinPoint joinPoint)throws Throwable{
       // Aspect Logic
    }
}
```

### Configuring Advices with annotations

1. Create an annotation type

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAspect{
}
```

2. Mention the same annotation on top of the method which we want to intercept using AOP

```java
@LogAspect
public String playMusic(boolean started, Song son){}
```

3. Use the annotation details to configure on top of the aspect method to advice

```java
@Around("@annotation(com.example.interfaces.LogAspect)")
public void logWithAnnotation(ProceedingJointPoint joinPoint) throws Throwable{
}
```

# 2 Overview of a web APP

## 2.1 Overview

![](https://github.com/riceniu/UdemySpringCourse/blob/master/pic/overview_web_app.png?raw=true)

1. The web clients sends a request using protocols like HTTP to Web Application asking some data like list of images, videos, text etc.
2. The web server where web app is deployed receives the client requests and process the data it receives. Post that it will response to the client's request in the format of HTML, JSON etc.
3. In Java web apps, **Servlet Container**(Web server) takes care of translating the HTTP messages for Java code to understand. One of the mostly used servlet container is **Apache Tomcat**
4. Servlet Container converts the HTTP messages into **ServletRequest** and hand over to Servlet method as a parameter. Similarly, **ServletResponse**returns as an output to Servlet Container from Servlet.

## 2.2 Role of Servlet inside WEB APPs



### Before Spring

![](https://github.com/riceniu/UdemySpringCourse/blob/master/pic/RolesOfServletInsideApps.png?raw=true)

1. Developer has to create a new servlet instance, configure it in the servlet container, and assign it to a specific URL path
2. When the client sends a request, Tomcat calls a method of the servlet associated with the path the client requested. The servlet gets the values on the request and builds the response that Tomcat sends back to the client.

### With Spring

![](https://github.com/riceniu/UdemySpringCourse/blob/master/pic/RolesOfServletInsideAppsWithSpring.png?raw=true)

1. It defines a servlet called **Dispatcher Servlet** which maintain all the URL mapping inside a web application.
2. The servlet container calls this Dispatcher Servlet for any client request, allowing the servlet to manage the request and response. This way spring internally does all the magic for *Developers without the need of defining the servlets inside*

## 2.3 Evolution of web apps inside java eco system

![](https://github.com/riceniu/UdemySpringCourse/blob/master/pic/evolutionofwebapps.png?raw=true)

- Somewhere in 2000

  - JSP/JSF
  - HTML/CSS
  - Servlets
  - JDBC
  - SOAP
  - J2EE

  No Web Design patterns and frameworks support present in 2000s. So all the web application code is written in such a way all the layers like Presentation, Business, Data layers are tightly coupled.

- Somewhere in 2010

  - JSP/JSF
  - HTML/CSS
  - jQuery/Bootstrap
  - SOAP/REST
  - MVC
  - ORM

  With the help & invention of design patterns like MVC and frameworks like Spring, Struts, Hibernate, developers started building web applications separating the layers of Presentation, Business, Data Layers. But all the code deployed into a single jumbo server as monolithic application

- Somewhere in 2020

  - Angula
  - React JS
  - Vue.js
  - HTML/CSS
  - REST
  - ORM/JPA
  - Cloud
  - Microservices
  - Docker/K8s

  With the invention of UI frameworks like Angular, React and new tends like Microservices, Containers, developers started building web application by separating UI and backend layers. |The code also deployed into multiple servers using containers and cloud.

## 2.4 Developing web applications using Spring

![](https://github.com/riceniu/UdemySpringCourse/blob/master/pic/developingwithSpring.png?raw=true)

Spring MVC is the key differentiator between these two approaches

## 2.5 Springboot

1. Springboot was introduced in 2014 April to reduce some of the burdens while developing a Java web application.
2. Before Springboot, developer need to configure a servelet container, establish link b/w Tomcat and Dispatcher servlet, deploy into a server, define lot of dependencies...
3. But with Springboot, we can create Web Apps skeletons within seconds or at least in 1-2 mins. It helps eliminating all the configurations we need to do.
4. Springboot is now one of the most appreciated projects in the Spring ecosystem. It helps us to create Spring apps more efficiently and focus on the business code.
5. Springboot is a mandatory skill now due to the latest trends like Full Stack development, microservices, Serverless, containers, Docker etc.

## 2.6 Before & after Springboot

| DISADVAN                                                     | ADVAN                                                        |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Configure a Maven/Gradle project with all dependencies needed | Springboot automatically configures the bare minimum components of a Spring application |
| Understand how servlets work & configure the DispatcherServlet inside web.xml | Springboot applications embed a web server so that we do not require an external application server. |
| Package the web application into a WAR file. Deploy the same into a server | Springboot provides several useful production-ready features out of the box to monitor and manage the applicaiton |
| Deal with complicated class loading strategies, application monitoring and management |                                                              |

## 2.7 Getting started with Springboot

1. https://start.spring.io/ is the website which can be used to generate web projects skeleton based on the dependencies required for an application.
2. We can identify the Springboot main class by looking for an annotation @SpringBootApplication
3. A single @SpringBootApplication annotation can be used to enable those three features
   1. @EnableAutoConfiguration: enable springboot's auto-configuration mechanism
   2. @ComponentScan: enable @Component scan on the package where the application is located
   3. @SpringBootConfiguration: enable registration of extra beans in the context or the import of additional configuration classes. An alternative to Srping's standard @Configuration annotation.
4. The @RequestMapping annotation provides "routing" information. It tells Spring that any HTTP request with the given path should be mapped to the corresponding method. It is a Spring MVC annotation and not specific to Spring Boot.
5. server.port and server.servlet.context-path properties can be mentioned inside the applicaiton.properties to change the default port number and context path of a web application. 
6. Mentioning server.port = 0 will start the web application at a random port number every time.
7. Mentioning debug=true will print the Autoconfiguration report on the console. We can mention the exclusion list as well for SpringBoot auto-configuration by using the below config: `@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})`

## 2.8 Tip: multiple path for RequestMapping

```java
@Controller
public class HomeController{
    @RequestMapping(value={"","/","home"})
    public String displayHomePage(Model model){
        //Business logic
    }
}
```

# 3 Spring family & tools

## 3.1 Themeleaf

- Thymeleaf is a modern server-side Java template engine for both web and standalone environments. This allow developers to build dynamic content inside the web applications.
- Thymeleaf has great integration with Spring especially with Spring MVC, Spring Security etc.
- The Thymeleaf + Spring integration packages offer a SpringResourceTemplateResolver implementation which uses all the Spring infrastructure for accessing and reading resources in applications, and which is the recommended implementation in Spring-enabled applications.

## 3.2 Springboot DevTools

- The Spring Boot DevTools provides features like Automatic restart & liveReload that make the application development experience a little more pleasant for developers.

- It can be added into any of the SpringBoot project by adding the below maven dependency

  - ```xml
    <dependency>
    	<groupId>org.springframkework.boot</groupId>
    	<artifactId>spring-boot-devtools</artifactId>
    </dependency>
    ```

- DevTools maintains 2 class loaders, one with classes that doesn't change and other one with classes that change. When restart needed it only reload the second class loader which makes restarts faster as well.

- DevTools includes an embedded LiveReload server that can be used to trigger a browser refresh when a resource is changed. LiveReload related browser extensions are freely available for Chrome, Firefox.

- 

  - DevTools triggers a restart when ever a build is triggered through IDE or by maven commands etc.
  - DevTools disables the caching options by default during development.
  - Repackaged archives do not contain DevTools by default.

## 3.3 MVC pattern

![](https://github.com/riceniu/UdemySpringCourse/blob/master/pic/MVCpattern.png?raw=true)

Model View Controller Design Pattern: separation of concerns to achieve loose coupling

**Model**: Represents the data of the application like name, age, students like etc. It stores & manages the data.

**View**: Represents UI and usually takes data from the controller and display it with the help of HTML pages.

**Controller**:  Controls the flow ^ decides which business logic needs to be executed. It acts as a Brain inside MVC pattern.



## 3.4 Spring MVC architecture & internal flow

​		![](https://github.com/riceniu/UdemySpringCourse/blob/master/pic/SpringMVCarchitecture.png?raw=true)

1. Web client makes HTTP request
2. Servlet Containers like Tomcat accepts the HTTP requests and handovers the Servlet Request to **Dispatcher Servlet** inside Spring Web app
3. The Dispatcher Servlet will check with the **Handler Mapping** to identify the controller and method names to invoke based on the HTTP method, path etc.
4. The Dispatcher Servlet will invoke the corresponding controller & method. After execution, the controller will provide a view name and data that needs to be rendered in the view.
5. The Dispatcher Servlet with the help of a component called view Resolver finds the view and render it with the data provided by controller.
6. The Servlet Container or Tomcat accept the servlet Response from the Dispatcher servlet and convert the same to HTTP response before returning to the client.
7. The browser or client intercepts the HTTP response and display the view, data etc.

### Tip: use ViewControllerRegistry to register view controller

By using VieControllerRegistery, we can register view controller that create a direct mapping between the URL and the view name. This way, there's no need for any Controller between the two.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/courses").setViewName("courses");
        registry.addViewController("/about").setViewName("about");
    }
}
```

## 3.5 Lombok

- Java expects lot of boilerplate code inside POJO classes like getters and setters.
- Lombok, which is a Java library provides you with several annotations aimed at avoiding writing Java code known to be repetitive and/or boilerplate.
- It can be added into any of the Java project by adding the maven dependency

```xml
<denepdency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

- Project lombok works by plugging into your build process. Then, it will auto-generate the Java bytecode into your .class files required to implement the desired behavior, based on the annotations you used.
- Most commonly used Lombok annotations,
  - `@Getter,@Setter`
  - `@NoArgsConstructor`
  - `@RequiredArgsConstructor`
  - `@AllArgsConstructor`
  - `@ToString,@EqualsAndHashCode`
  - `@Data`
- `@Data` is a shortcut annotation that combines the features of below annotations together,
  - `@ToString`
  - `@EqualsAndHashCode`
  - `@Getter,@Setter`
  - `@RequiedArgsConstructor`

## 3.6 `@RequestParam` annotation

- In Spring `@RequestParam` annotation is used to map either query parameters or form data.

- For example, if we want to get parameters value from a HTTP GET requested URL and we can use @RequestParam annotation like below:

  http://localhost:8080/holidays?festival=true&federal=true

  ```java
  @GetMapping("/holidays")
  public String displayHolidays(@RequestParam(required = false) boolean festival,
                               @RequestParam(required = false) boolean federal){
      //todo
      return "holidays.html";
  }
  ```

- The @RequestParam annotation supports attributes like name, required, value, defaultvalue. We can use them in our application based on the requirements.

- The **name** attribute indicates the name of the request parameter to bind to.

- The **required** attribute is used to make a field either optional or mandatory. If it is mandatory, an exception will throw in case of missing fields.

- The **value** attribute is similar to name elements and can be used as an alias.

- **defaultValue** for the parameter is to handle missing values or null values. If the parameter does not contain any value then this default value will be considered.

## 3.7 `@PathVariable` annotation

- The @PathVariable annotation is used to extract the value from the URI. It is most suitable for the RESTful web service where the URL contains some value. Spring MVC allows us to use multiple @PathVariable annotations in the same method.

- For example, if we want to get the value from a requested URI path, then we can use @PathVariable annotation like:

  http://localhost:8080/holidays/all

  http://localhost:8080/holidays/federal

  http://localhost:8080/holidays/festival

  ```java
  @GetMapping("/holidays/{display}")
  public String displayHolidays(@PathVariable String display){
      //todo
      return "holidays.html"
  }
  ```

- The @PathVariable annotation supports attributes like **name, required, value** similar to @RequestParam. We can use them in our application based on the requirements.
