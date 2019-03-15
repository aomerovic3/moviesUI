#FROM com.movies/ui:latest
#WORKDIR /src
#ARG WAR_FILE
#COPY ${WAR_FILE} ui.war
##CMD ["sh", "-c", "java -jar C:\Users\Almir\Downloads\Movies-UI-service\Movies-UI\build\libs\ui.war"]
#CMD java -jar C:\\ui.war
##ENTRYPOINT echo "Hello"

FROM openjdk:8-jdk-alpine
WORKDIR /app
#VOLUME /docker
#RUN mkdir -p /app
COPY ./ui.war /app/ui.war
ENTRYPOINT ["java", "-jar", "/app/ui.war"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ui.war"]

#FROM openjdk:8-jdk-alpine
##VOLUME /tmp
###ADD ui.war ui.war
##ARG JAR_FILE
##COPY ${JAR_FILE} ui.war
##EXPOSE 8082
##CMD java -jar ui.war
#RUN mkdir -p /app/
#ADD build/libs/ui.war /app/ui.war
#ENTRYPOINT ["java", "-jar", "/app/ui.war"]
