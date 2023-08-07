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

