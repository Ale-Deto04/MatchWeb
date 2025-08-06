# MatchWeb
A full-stack web application developed as part of a university course, simulating a sports championship with the ability for registered users to create and manage betting slips (schedine).

## Project Context and Documentation

This project was developed as part of the academic coursework for the "Introduzione alla Programmazione Web" course.  
Its primary purpose is educational, aimed at learning and applying web programming concepts and technologies.  

For a detailed explanation of the project design, implementation choices, and architecture, please refer to the comprehensive project report included in the repository (`project_report.pdf`).  
The report contains an in-depth description of the system components, business logic, and technical decisions.


## Project Description
This project consists of two web applications:
- **MatchWeb**: The main application that allows users to register and place bets on sports such as Baseball, Cricket, and Padel. Currently, only Cricket is supported, with data provided by the REST application.
- **REST_App**: A backend REST service providing fictitious teams, schedules, and random results for Cricket matches via a REST APIs.

## Repository Structure
- `Matchweb/` - Source code and resources for MatchWeb
- `REST/` - Source code and resources for REST_app
- `LICENSE` - Project license
- `README.md` - This file
- Additional configuration files and folders as needed

## Key Features of MatchWeb
- User management with roles: **ADMIN#03**, **USER#03**, and **MODERATOR#03**
- Public section accessible without login (e.g., homepage, news, registration)
- Private dashboards with role-specific functionalities (profile management, scoring, rewards)
- Integration with REST_app via OpenFeign to fetch match data
- Security implemented with Spring Security (authentication, authorization, CSRF protection, SQL injection and XSS prevention)
- User reviews and rating system

## Key Features of REST_App
- Generates fictitious teams, match schedules, and results
- Provides data via REST endpoints in JSON format

## Technologies Used
- Java, Spring Framework (MVC, Security, REST, Cloud Openfeign)
- Thymeleaf and Bootstrap for frontend
- Maven for build and dependency management

## Running the Project

1. **Database setup**  
   - The project uses an **H2 embedded database**.  
   - Initialize the database by executing the SQL instructions contained in `MatchWeb/schema.sql`. This will create all necessary tables and insert sample data (including test users).  

2. **Server setup**  
   - Install and configure **Apache Tomcat 10** as the application server.  
   - Build the projects to generate their respective `.war` files.  

3. **Deployment**  
   - Deploy both `.war` files for **REST_App** and **MatchWeb** on Tomcat **concurrently**.  
   - Ensure that **REST_App** is running before starting **MatchWeb**, since the latter depends on the former to fetch match data.  

4. **Running**  
   - Access the main application via the URL configured for **Team03_MatchWeb** (e.g., `http://localhost:8080/Team03_MatchWeb`).  

**Note:**  
If REST_app is not available or not running, MatchWeb will crash due to failed backend connections.

## Known Issues and Future Improvements
- Password visibility toggle button in forms not implemented due to UI conflicts
- Future plans include data caching and fallback mechanisms to improve REST API resilience

## Credits and Acknowledgements
- Logos and icons created using Canva and AI tools
- Star rating system adapted from: https://github.com/pryley/star-rating.js

---

*This project was developed as part of the course "Introduction to Web Programming" (Group ID 03).*
