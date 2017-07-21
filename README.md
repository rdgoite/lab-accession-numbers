#Accession Numbers

## Background
This application is built using `Gradle 3.5.1`, whose wrapper is provided in this repository. It is recommended to use the Gradle wrapper for running all the tasks in this project.

**Generating Gradle Wrapper**

There may be restrictions imposed by methods through which this code base is transferred from one environment to another. For example, some email services may restrict attachment of executable files, which may result in removal of the Gradle wrapper. Ideally, the code base is accessed through version control, but that is not always an available option. To add the wrapper back to this project, please refer to the [official Gradle documentation][1].

## Running the Application

There are 2 modes for running this application: command line mode, and Web application mode. Each mode is run through their respective Gradle task, which are discussed below.

**Assumptions**

The application is provided with some validation code and error checks to prevent unexpected behavior. However, like most development projects, there may be alternative flows that are yet to be addressed. It was tested to run based on a limited set of possible basic inputs, and so it is expected to run properly for well-formed comma-separated data input. In CLI mode, the application was tested to work well for multi-line comma-separated input file.

For comments or feedback, please send them to [rdgoite@gmail.com][2].

### Command Line Mode

As a basic requirement, this application can run through the command line interface. The Gradle task `cli` is provided to run the main class. It expects a system property named `input.file` that should refer to a comma-separated file in the local file system. The CLI mode can be executed using the following command:

    ./gradlew cli -Dinput.file=path/to/input.dat
    
Running this will result in Gradle printing some build related messages on the standard output. To run the application less verbose, the `-q` switch can be used:

    ./gradlew cli -Dinput.file=path/to/input.dat -q

### Web Application Mode

The Web endpoint for this application is implemented using Spring Boot's Web module. Spring Boot's Gradle plugin provides a task named `bootRun` to run the application in the appropriate mode (in this case using embedded Web server). To run the application in Web application mode use:

    ./gradlew bootRun
    
and wait for the log to indicate that the execution is completed. The server will bind to port 8080 by default and may cause some conflict on the local environment. To resolve this issue, either the default port may be released from another running application, or an alternative port may be chosen instead. To run in Web application mode using a different port, the `server.port` system property may be set, for example:

    ./gradlew bootRun -Dserver.port=9090

In the Web application mode, the system takes inputs through plain HTTP. The comma-separated values can be sent to the `/sorter` endpoint via `POST` method only. For example, if the application runs on the default port, `curl` command may be used as:

    curl -X POST -H "Content-Type:text/plain;charset=UTF-8" \
    -d @path/to/input.dat http://localhost:8080/sorter
    
This will respond with a command-separated data as plain text. The system will respond with a non-OK response when errors occur during processing. No other endpoint works for this system.
    

<!-- links -->
[1]: https://docs.gradle.org/3.3/userguide/gradle_wrapper.html#sec:wrapper_generation
[2]: mailto:rdgoite@gmail.com