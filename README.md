|---HOW TO RUN---|
run "mvnw clean package" on a cmd terminal at the root folder location to build the jar.
Then, run the jar file (java -jar spring-boot-0.0.1-SNAPSHOT.jar) and done.
The API process will listen at port 8083 on localhost by default as configured in my additional-properties.

This is my submission for the technical challenge:

An API in Java emulating a stack structure using Maven and Spring Boot. The three specified endpoints were implemented, along
with an additional DELETE endpoint that clears the entire stack, to be used in @BeforeEach in my test suite to refresh the stack
for my unit tests. Stack implementation is in StackStruct.java and all API endpoints is in RestController.java which uses an instance of StackStruct.
Unit tests were written with JUnit framework and Postman was also used for manual API debugging.

I had some challenges in initial setup as I was unfamiliar with using the Spring frameworks, so I read the documentations for
everything related to Spring Boot I needed to know beforehand before attempting to start. I used Spring Initializr to create the initial maven project,
and largely kept its design structure. I eventually went with a java.util.Stack for my StackStruct as it was more thread-safe than my previous implementation
which used an arraylist and global index variable. Also as mentioned, I had trouble with my unit tests passing when running all at once so my workaround to refresh
the test environment was to make another DELETE endpoint that cleared the stack; that endpoint is called by ClearStack @BeforeEach annotated function in ApplicationTests.java.