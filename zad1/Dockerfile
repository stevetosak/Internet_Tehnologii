FROM docker.io/library/openjdk:17 as jdkstage

FROM httpd

RUN apt --yes update
RUN apt --yes upgrade

COPY ./httpd.conf /usr/local/apache2/conf/httpd.conf
COPY --from=jdkstage /usr/java/openjdk-17 /usr/local/java

ENV PATH="${PATH}:/usr/local/java/bin"
ENV JAVA_HOME="/usr/local/java/"
