Sample Spring JMS consumer app implementation using maven.
Use below command to create the runnable jar with dependencies:

mvn package

To run the main app use below command:

mvn exec:java -Dexec.mainClass="com.test.spring.ConsumerApp" -Dexec.classpathScope=runtime
