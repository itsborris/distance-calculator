FROM adoptopenjdk:16
COPY target/distance-calculator-0.0.1-SNAPSHOT.jar /distance-calculator.jar
CMD ["java", "-jar", "/distance-calculator.jar"]