# Project Goals

Aim of this project is to demonstrate skills in coding REST API based backend in Java/JEE/Spring.

# Background

For project scaffolding was used [Spring Boot Initializr](https://start.spring.io/) with following dependencies:

* Spring HATEOAS
* Spring Data JPA
* H2 Database

This project is using Maven so to build the project follow basic Maven instruction. 

# Build & Run

Result of build is WAR file deployable & runnable in common servlet containers (not tested). To run project with embeeded
servlet container just run `MrtestApplication#main` method. Due to lack of time there are no unit tests or any fancy validations
using GeoTools testing i.e. farm field polygon validity.   

# Features

Despite all project is part of single module, I strive to do reasonable code splitting into self explanatory packages. 

Main features are:

* Use of H2 DB via Spring-JPA-Hibernate
* Use Spring to access persistence layer and via CRUD operation exposed in REST controller
* Demonstrate HATEOAS approach
* Automatically generate REST API documentation
* Show basic validation
* Demonstrate use of GeoTools to calculate polygon area

# Side Effects

As side effect this project creates H2 database in folder `~/.cleverfarm` and populate it with some initial data. After project review please don't forget to delete
this folder.
   
