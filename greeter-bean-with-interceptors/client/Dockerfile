FROM jfloff/thrike:8.5

LABEL maintainer="jfloff@inesc-id.pt"

###################
# Build Server
#
WORKDIR /home/client
# cache gradle build dependencies - is less often changed than code
ADD build.gradle /home/client
RUN gradle getDependencies
# add all code and build
ADD . /home/client
RUN gradle build && \
    cp build/libs/client.war /usr/local/tomcat/webapps/
