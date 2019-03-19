FROM jfloff/thrike:8.5

LABEL maintainer="jfloff@inesc-id.pt"

###################
# Build pong
#
WORKDIR /home/pong
# cache gradle build dependencies - is less often changed than code
ADD build.gradle /home/pong
RUN gradle getDependencies
# add all code and build
ADD . /home/pong
RUN gradle build && \
    cp build/libs/pong.war /usr/local/tomcat/webapps/