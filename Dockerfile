FROM eclipse-temurin:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "-Dfile.encoding=UTF-8", "/app.jar"]
