<img src="http://www.river-framework.org/images/river-header.png" /><br/>
[![Project Stats](https://www.openhub.net/p/river-framework/widgets/project_thin_badge.gif)](https://www.openhub.net/p/river-framework)

# river-framework-demo

Demos for the River Framework project, an Object Document Mapper Framework written in Java. Each demo is a **Maven project**.

## About River Framework

You will find a presentation about the project at its [website](http://www.riverframework.org/). About source code, current features, known issues, next features, etc., please visit its GitHub [repo](https://github.com/mariosotil/river-framework).


## Demo: Getting Started

It's the same demo explained at the [Getting Started](https://github.com/mariosotil/river-framework-documentation/blob/master/getting-started.md) document, but this was written as a Maven Project.

Features:
- It is a stand-alone Java program that opens an IBM Notes session, creates a test database, creates a document, set a simple value and retrieves it again. Also, says "Hello world!" :-)  
- It does not use any 'entity' class like `Person` from the AddressBook demo. It access the database and documents using the  default classes implemented in the framework. 

Requirements:
- Java 1.6+ JDK
- IBM Notes 8.5+ client installed. The IBM Domino server is not necessary.
- An IDE like IntelliJ or Eclipse

## Demo: Address Book
It creates a local IBM Notes database, populates it with random data, and do a simple search. 

Features:
- It shows how to define a database (`AddressBook.class`) for an indexed document (`Person.class`)
- The `Person.java` file has defined all required and some optional methods.
- The `AddressBook.java` file has the minimum to use it with indexed documents. 

Requirements:
- Java 1.6+ JDK
- IBM Notes 8.5+ client installed. The IBM Domino server is not necessary.
- An IDE like IntelliJ or Eclipse

## Demo: Play! Framework Address Book
It works like the Address Book demo, but it was build as a Play! Framework application.

Features:
- It opens a test database. If it does not exist, the application will create and populate it with random data.
- The `Person.java` file has defined all required and some optional methods.
- The `AddressBook.java` file has the minimum to use it with indexed documents. 
- It's a basic CRUD application. It shows the database data in this URL: http://127.0.0.1/persons/ 

Requirements:
- Java 1.8+ JDK
- Play! Framework 2.4+
- IBM Notes 8.5+ client installed. The IBM Domino server is not necessary.
- An IDE like IntelliJ or Eclipse



# To import the demos:
- Create a Git clone or just download it as a ZIP file
- In IntelliJ or Eclipse, import the project that you want to test
- Download the River Framework artifacts using Maven
- Run
 
For run the Play! demos, use the activator provided by the Play framework.

If you have any question or comment, please write me to [mario.sotil@gmail.com](mailto:mario.sotil@gmail.com)
