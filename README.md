## General ##

This is a spring boot application with an endpoint to 
receive list of data and return a zipped file containing 
the input files. 

## Steps in SDLC
- Planning: 
  - Dividing the process flow into steps -> Planning, implementing logic, packaging and documentation.
  - Considering the version of Java, tools for packaging (jib) and running the application (make and docker)
- Implementation:
  - Thinking about the layers , controller and service.
  - Setting up integration tests.
  - Implementing the api and the service.
- Packaging
  - Dockerizing the app is done by google component JIB.   https://cloud.google.com/java/getting-started/jib
  - In order to compile->package and run the application, install 'make' and use the command `make app`
- Documentation
  - Define prerequisites to start the project. 
  - Creating a readme file

## Prerequisites before start the app ##

- JDK 18
- maven 3.8.5 or higher
- docker
- make

## Setup ##
To run the application use the maven command below
`mvn spring-boot:run`

In order to start an application in a docker image, 
run the command below:
`make app`
This will compile the project and create a docker image with the name of 'zipapp'. 
It will run a container from this image. 
Make sure port 8080 is free before starting the application.

## Testing ##

In order to check if application running , Swagger is available on http://localhost:8080/swagger-ui/index.html
or you may import "zipfiles.postman_collection.json" (at the root of the project) into postman.
