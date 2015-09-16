<img src="http://www.river-framework.org/images/river-header.png" /><br/>
[![Project Stats](https://www.openhub.net/p/river-framework/widgets/project_thin_badge.gif)](https://www.openhub.net/p/river-framework)

# river-framework-demo

Demos for the River Framework project, an Object Document Mapper Framework written in Java. Each demo is a **Maven project for Eclipse** of stand-alone Java programs.

## About River Framework

You will find a presentation about the project at its [website](http://www.riverframework.org/). About source code, current features, known issues, next features, etc., please visit its GitHub [repo](https://github.com/mariosotil/river-framework).


## Demo: Getting Started

It's the same demo explained at the [Getting Started](https://github.com/mariosotil/river-framework-documentation/blob/master/getting-started.md) document, but this was written as a Maven Project.

Features:
- It is a basic program that opens an IBM Notes session, creates a test database, creates a document, set a simple value and retrieves it again. Also, says "Hello world!" :-)  
- It does not use any 'entity' class like `Person` from the AddressBook demo. It access the database and documents using the  default classes implemented in the framework. 


## Demo: Address Book
It creates a local IBM Notes database, populates it with random data, and do a simple search. 

Features:
- It shows how to define a database (`AddressBook.class`) for an indexed document (`Person.class`)
- The `Person.java` file has defined all required and some optional methods.
- The `AddressBook.java` file has the minimum to use it with indexed documents. 


## How install a demo?
You will need:
- Java 1.6+ JDK
- IBM Notes 8.5+ client installed. The IBM Domino server is not necessary.
- Eclipse IDE for Java developers (I use [Luna](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/marsr))
  
To import the demos:
- Create a Git clone or just download it as a ZIP file
- In Eclipse, import the project that you want to test
- Download the River Framework artifacts using Maven
- Run
 
If you have any question or comment, please write me to [mario.sotil@gmail.com](mailto:mario.sotil@gmail.com)
