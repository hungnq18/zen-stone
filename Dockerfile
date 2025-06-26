# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with Tomcat
FROM tomcat:9.0-jdk17
# Remove the default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT
# Copy the built WAR file from the build stage to Tomcat's webapps directory
COPY --from=build /app/target/SWP_TECH-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Debug: Liệt kê file WAR sau khi copy để kiểm tra deploy
RUN ls -l /usr/local/tomcat/webapps/

# Create a setenv.sh file to configure Tomcat's JVM options.
# This is a more robust way to pass database credentials from Render's environment variables.
RUN echo '#!/bin/sh' > /usr/local/tomcat/bin/setenv.sh && \
    echo 'export CATALINA_OPTS="$CATALINA_OPTS -Ddb.host=$DB_HOST -Ddb.name=$DB_NAME -Ddb.user=$DB_USER -Ddb.password=$DB_PASSWORD"' >> /usr/local/tomcat/bin/setenv.sh && \
    chmod +x /usr/local/tomcat/bin/setenv.sh

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"] 