#!/bin/bash

CLASSNAME=SimuleringsMotor
FILESOURCE="${BASH_SOURCE[0]}"
CURDIR="$( dirname "$FILESOURCE" )"

PSCHECKOUTPUT=`ps axu | grep -i \`cat /var/run/simuleringsMotor_${CLASSNAME}.pid\` | grep -v grep`
#PSCHECKOUTPUT=`ps axu | grep -i 12323 | grep -v grep`
LOGLASTMTIME=`stat -c %Y /tmp/out_${CLASSNAME}.log`
CURTIME=`date +%s`
TIMESINCELASTUPDATE=`expr $CURTIME - 1800 - $LOGLASTMTIME`

if [ $TIMESINCELASTUPDATE -gt 0 ]; then
        echo "SimuleringsMotor has stopped! Restarting"

kill -9 `cat /var/run/simuleringsMotor_${CLASSNAME}.pid`

/usr/sbin/sendmail -f "501080@student.hin.no" 501080@student.hin.no << EOF
From: checkSimuleringsMotor.sh <501080@student.hin.no>
To: 501080@student.hin.no
Subject: checkSimuleringsMotor.sh failed: SimuleringsMotor $(CLASSNAME} has stopped running, restarting!

restarting SimuleringsMotor.
EOF

${CURDIR}/startSimuleringsMotor.sh $CLASSNAME
fi

if [ -z "${PSCHECKOUTPUT}" ]; then
        echo "SimuleringsMotor has died! Restarting"

/usr/sbin/sendmail -f "501080@student.hin.no" 501080@student.hin.no << EOF
From: checkSimuleringsMotor.sh <501080@student.hin.no>
To: 501080@student.hin.no
Subject: checkSimuleringsMotor.sh failed: SimuleringsMotor is dead, restarting!

restarting SimuleringsMotor.
EOF

${CURDIR}/startSimuleringsMotor.sh $CLASSNAME
fi