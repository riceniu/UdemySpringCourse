package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/*
Spring @Configuration annotation is part of the spring core framework.
Spring Configuration annotation indicates that the class has @Bean definition
methods. So Spring container can process the class and generate Spring Beans
to be used in the application.
* */
@Configuration
@ComponentScan(basePackages = {
        "com.example.impl",
        "com.example.services",
        "com.example.beans"
})
//@ComponentScan(basePackageClasses = {
//        com.example.beans.Person.class,
//        com.example.beans.Vehicle.class})
public class ProjectConfig {
}
