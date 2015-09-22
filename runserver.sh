#!/bin/sh

java -classpath bin:lib/log4j-1.2.9.jar edu.upenn.cis.cis455.webserver.HttpServer 8080 www/ &
sleep 2
curl http://localhost:8080/control
curl http://localhost:8080/shutdown
