# microservice-link
It's  about how to trace the invocation link in logs when invoking in the  microservice
it's based on SpringBoot+Log4j2. and Asynchronous Support.
How to use it?
add "%X{logId}-%X{parentSpanId}-%X{spanId}" in the pattern config of log4j.xml
