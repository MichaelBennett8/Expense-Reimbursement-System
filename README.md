# Expense Reimbursement System
 
## Project Description

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used

* Java - version 12.0.1
* Javalin - version 3.13.3
* Hibernate - version 5.4.28.Final

## Features

List of features ready and TODOs for future development
* Supports Logging in and authentication with JWTs
* Persists data on a PostgreSQL database
* Provides a RESTful API to request resources from the database

To-do list:
* Implement a frontend design with Angula Material

## Getting Started

- Required environment variables: JWT_SECRET, CONN_DETAILS
- Required files: hibernate.cfg.xml

## Usage

> A PostgreSQL database and it's credentials must be provided via the hibernate.cfg.xml.
> Build the backend with gradlew fatJar
> Change the serverPath in the api.service.ts file to the IP adressof your backend. The port is 7000.
