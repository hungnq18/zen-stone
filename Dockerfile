# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with Tomcat 10 (Servlet 5+)
FROM tomcat:10.1-jdk17
# Remove the default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT
# Copy the built WAR file from the build stage to Tomcat's webapps directory
COPY --from=build /app/target/SWP_TECH-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"] 