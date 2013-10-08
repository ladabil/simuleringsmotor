#!/bin/bash

PWD=`pwd`
cd ${CLASSPATH}

CLASSPATH="/usr/java"

MYCLASSPATH="${CLASSPATH}/commons-daemon-1.0.15.jar"
MYCLASSPATH="${MYCLASSPATH}:${CLASSPATH}/commons-email-1.3.1.jar"
MYCLASSPATH="${MYCLASSPATH}:${CLASSPATH}/commons-net-3.3.jar"
#MYCLASSPATH="${MYCLASSPATH}:${CLASSPATH}/mysql-connector-java-5.1.17-bin.jar"
MYCLASSPATH="${MYCLASSPATH}:/usr/share/java/javamail.jar"
#export JAVA_HOME="/usr/lib/jvm/jre-openjdk/"

/usr/bin/jsvc -jvm server -pidfile /var/run/simuleringsMotor_${1}.pid -outfile /tmp/out_${1}.log -errfile /tmp/err_${1}_err.log -cp ${MYCLASSPATH} no.hin.student.y2013.grp2it.simuleringsmotor.${1}
