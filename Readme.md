

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

## 1.1 Spring Beans

Any normal Java class (POJO plain old java object) that is instantiated, assembled, and otherwise managed by Spring IoC container is called **Spring Bean**.



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