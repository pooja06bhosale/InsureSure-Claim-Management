
# Use OpenJDK 17 base image
FROM openjdk:17

# Set a working directory inside the container (optional but good practice)
WORKDIR /app

# Copy the built jar file from target/ to /app/app.jar inside the container
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Auth service uses (if required — e.g. 8083)
EXPOSE 8083

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]


#Build your jar -->
# mvn clean package
#docker image  ---->docker build -t pooja06bhosale/authservice:v0.000001 .
#run container-->
#docker run -d -p 8083:8083 --name auth-service-container pooja06bhosale/authservice:v0.000001
#Check logs-->
#docker logs -f auth-service-container





#docker ps
# docker stop container_id
#docker push aks2307/test:v0.000001
#docker run -d --name local -p 8080:8080 aks2307/test:v0.00001

#docker ps
#docker stop container_id