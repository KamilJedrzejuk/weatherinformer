# Example of reactive microservice with hexagonal architecture.

Weather informer is simple Spring Boot application based on Webflux (Project reactor), that's allow us retrieve weather 
information from openweathermap site.
It has end-to-end tests written in Spock.

### Stack technology:
  - JAVA 8, Groovy
  - Lombok - is a java library that automatically plugs into your editor and build tools, spicing up your java
  - Gradle
  - Spring BOOT 2.1.x
  - Spring Webflux - provides reactive programming support for web applications.
  - Vavr - functional library for Java
  - Spock - is a testing and specification framework for Java and Groovy applications
  - Mockwebserver - a scriptable web server for testing HTTP clients

### Requirements
  - Service should expose REST API
  - We can ask for weather by city name
  - Response should has this information:
     - city
     - temp
     - pressure
     - humidity
  - Service should presents results in metrics units
  - Example response for city London:
     ```json
     {
       "city": "London",
       "temp" : 8,
       "pressure": 1011,
       "humidity":81
     }
     ```