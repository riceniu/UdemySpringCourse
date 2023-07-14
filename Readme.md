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

#####  Dependency Injection (DI)

##### Aspect-Oriented Programming (AOP)

1. Different approaches of Beans creation inside Spring framework
2. Bean Scopes inside Spring framework
3. Autowiring of the Spring Beans
4. Lombok library and Annotations
5. Introduction to MVC pattern & overview of web apps
6. Spring MVC internal architecture & how to create web applications using Spring MVC & Thymeleaf
7. Spring MVC Validations
8. How to build dynamic web apps using Thymeleaf & Spring
9. Thymeleaf integration with Spring, Spring MVC, Spring Security
10. Deep dive on Spring Boot, Auto-configuration
11. Spring Boot Dev Tools
12. Spring Boot H2 Database
13. Securing web applications using Spring Security
14. Authentication , Authorization, Role based access
15. Cross-Site Request Forgery (CSRF) & Cross-Origin Resource Sharing (CORS)
16. Database create, read, update, delete operations using Spring JDBC
17. Introduction to ORM frameworks & database create, read, update, delete operations using Spring Data JPA/Hibernate
18. Derived Query methods in JPA
19. OneToOne, OneToMany, ManyToOne, ManyToMany mappings inside JPA/Hibernate
20. Sorting, Pagination, JPQL inside Spring Data JPA
21. Building Rest Services inside Spring
22. Consuming Rest Services using OpenFeign, Web Client, RestTemplate
23. Spring Data Rest & HAL Explorer
24. Logging inside Spring applications
25. Properties Configuration inside Spring applications
26. Profiles inside Spring Boot applications
27. Conditional Bean creation using Profiles
28. Monitoring Spring Boot applications using SpringBoot Actuator & Spring Boot Admin
29. Deploying SpringBoot App into cloud using AWS Elastic Beanstalk