FROM jfloff/thrike:8.5

LABEL maintainer="jfloff@inesc-id.pt"

###################
# Build Server
#
WORKDIR /home/server
# cache gradle build dependencies - is less often changed than code
ADD build.gradle /home/server
RUN gradle getDependencies
# add all code and build
ADD . /home/server
RUN gradle build && \
    cp build/libs/server.war /usr/local/tomcat/webapps/