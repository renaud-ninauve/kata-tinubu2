FROM eclipse-temurin:21
WORKDIR workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} insurancepolicies.jar
ENTRYPOINT ["java", "-jar", "insurancepolicies.jar"]