Decision tables  can be defined in an Excel spreadsheet (the .xls file) or a comma separated value (the .csv file) format.

This example uses the following frameworks:

Maven 3.2.3
Java 8
Drools 6.2
IDE Intellij or Eclipse

In the pom.xml, following dependencies are added:
knowledge-api – this provides the interfaces and factories
drools-core – this is the core engine, runtime component. This is the only runtime dependency if you are pre-compiling rules.
drools-complier – this contains the compiler/builder components to take rule source, and build executable rule bases. You don’t need this during runtime, if your rules are pre-compiled.

To build:
mvn clean package

