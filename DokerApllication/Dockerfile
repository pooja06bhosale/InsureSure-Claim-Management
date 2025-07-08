FROM openjdk:17
COPY target/DokerApllication-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]



#command to run ----> docker build -t aks2307/productservice:v0.000001 . <- dot to tell docker, find dockerfile in this location and build image with name given
#docker run aks2307/test:v0.000001 on terminal
#docker ps
# docker stop container_id
#docker push aks2307/test:v0.000001
#docker run -d --name local -p 8080:8080 aks2307/test:v0.00001

#docker ps
#docker stop container_id