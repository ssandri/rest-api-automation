# REST Api Automation Exercise

## Installation

This projects needs the Java JDK 11 and Gradle to execute the test scenarios.

### AdoptOpenJDK11

You can download the AdoptOpenJDK11 (with HotSpot JVM) for your system [here](https://adoptopenjdk.net/). Also you can
find the installation steps for your operative system [here](https://adoptopenjdk.net/installation.html).

### Gradle

There is no need to install Gradle, this projects comes with a gradle wrapper in the root folder.

## Getting Started

Once you have clone the repository, open a terminal and run the following commands in the root project folder:

### MacOS / Linux

```bash
  ./gradlew clean build
```

```bash
  ./gradlew downloadAllure
```

### Windows

```bash
  gradlew.bat clean build
```

```bash
  gradlew.bat downloadAllure
```

## Running Tests

To run tests, run the following command

### MacOS / Linux

```bash
  ./gradlew clean executeScenarios
```

### Windows

```bash
  gradlew.bat clean executeScenarios
```

### Logs

Each test scenarios execution will generate a log file inside the folder `logs`

## Reporting

To generate the execution report execute, after running the test, the following command:

### MacOS / Linux

```bash
  ./gradlew allureServe
```

### Windows

```bash
  gradlew.bat allureServe
```

This command will open a report in your system default browser containing a detailed report for the execution, all HTTP calls will have a detailed request and response log attached to the step.

In order to end the command simply input ``TRL+C` in your terminal.