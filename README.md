# File Processing Application

[![Build Status](https://travis-ci.org/atanasg/file-processing-application.svg?branch=master)](https://travis-ci.org/atanasg/file-processing-application)

A command-line application in Java that allows the user to manipulate text files with number content.

## About
The application requests at start that the user provides a valid path to a `.txt` file.
After the file is opened and parsed, the following commands are supported:
```
FILE PROCESSING APPLICATION - SUPPORTED COMMANDS
----
help - Prints this message
----
insertnum <line_index> <line_number_index> <new integer to set> - Insert a number at a specific position
swaplines <first_line_index> <second_line_index> - Swap two lines
swapnums <first_line_index> <first_line_number_index> <second_line_index> <second_line_number_index> - Swap two numbers at specified positions
readnum <line_index> <line_number_index> - Read a number at a specified position
modifynum <line_index> <line_number_index> <new integer to be set> - Modify a number at a specified position and set a new value
removenum <line_index> <line_number_index> - Remove a number at a specified position
* PAY ATTENTION: The first number in the first row has <line_index> = 1 and <line_number_index> = 1
----
validate - Validate the current content
print - Print the current content
----
save - Save the performed changes in the file
quit - Exit the application
```

## Usage
```shell
<clone the source code>
cd FileProcessingApp

# Build application
mvn clean package

# Run
java -classpath target/FileProcessingApp-<version, e.g. 0.1.0-SNAPSHOT>.jar:<path to guava>/guava-18.0.jar com.atanasg.fileprocessingapp.FileProcessingAppLauncher
```
A log showing an example interaction with the appliaction can be found in the `EXAMPLE_CONSOLE_LOG` file.

## License
This project is licensed under the Apache License 2.0. Check out the license text inside  the `LICENSE` file.

## Dependencies
Generate the project site in order to see all direct and transitive dependencies incl. their licenses.
```shell
# Build project site and reports
mvn clean site

# Go to the location of the generated html site
cd target/site/
```
## Test coverage and bugs
Generate the project site (see above) to find the reports from Cobertura and FindBugs.
