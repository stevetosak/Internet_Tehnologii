docker run \
--name IT_zad2 \
--privileged  \
--mount type=bind,source=./webapps,target=/usr/local/tomcat/webapps/  \
-p 8082:8080 \
-d \
tomcat:latest
