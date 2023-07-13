#!/bin/sh

JAVA_OPTS="-Xms512m -Xmx1g"

java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar $args --logging.file.path=/log

