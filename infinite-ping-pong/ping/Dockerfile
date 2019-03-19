FROM jfloff/thrike:8.5

LABEL maintainer="jfloff@inesc-id.pt"

###################
# Build ping
#
WORKDIR /home/ping
# cache gradle build dependencies - is less often changed than code
ADD build.gradle /home/ping
RUN gradle getDependencies
# add all code and build
ADD . /home/ping
RUN gradle build && \
    cp build/libs/ping.war /usr/local/tomcat/webapps/