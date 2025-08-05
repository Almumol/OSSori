FROM eclipse-temurin:21-jdk
ARG MODULE
COPY ${MODULE}.jar app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "-Dfile.encoding=UTF-8", "/app.jar"]
