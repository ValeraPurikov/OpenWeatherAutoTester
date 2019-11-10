# WeatherAPIAutoTester

This is a JAVA Maven Project. It contains a small suite of tests written using JUnit 5 framework, with the help of Rest Assured. These are used to run autotests on an OpenWeatherMap.com Restful Web Service API. Test launching is done via the applications UI, which also supplies the user with a summary report of the launched tests.  

## Dependencies

Java SDK 13 <br/>
JUnit 5 (Jupiter) <br/>
Rest Assured <br/>
Maven <br/>

## Installation

Simply download/clone the project and open it with a Java IDE, preferably IntelliJ IDEA. Build and Run the UI Module.

## Usage

In order to launch a test from the UI, click on the desired test from the list on the left, titled "Available Tests", in order to move it to the list on the right, titled "Assigned Tests", then press the "Start Tests" button. Multiple tests can be assigned this way, before being launched. Test execution can be stopped by pressing Stop Tests button, which appears when tests are being executed. The console bellow will contain all the output of the program, including a report about the running tests.

## Note

Some tests report a failure. It appears that Rome and Marino share the same City ID, and when searching cities by ID and then by Name, different coordinates are returned. It appears to be a bug within the OpenWeatherMap API, but might be "by design".
