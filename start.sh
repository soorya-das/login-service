#!/bin/sh

# Default to 128m for initial heap size and 256m for max heap size
# IMPORTANT - NOT ALL SERVICES NEED THIS MUCH MEMORY, PLEASE ADJUST ACCORDINGLY!
DEFAULT_JAVA_OPTS="-Xms16000m -Xmx240000m -XX:MinRAMPercentage=50.0 -XX:MaxRAMPercentage=80.0  -XX:ActiveProcessorCount=4 -XX:-UseParallelGC"

if [ -z "${JAVA_OPTS}" ]; then
  export JAVA_OPTS="${DEFAULT_JAVA_OPTS}"
else
  # Append default values to JAVA_OPTS if -Xms or -Xmx is not specified by the user
  echo "${JAVA_OPTS}" | grep -q "\-Xms" || JAVA_OPTS="${JAVA_OPTS} -Xms128m"
  echo "${JAVA_OPTS}" | grep -q "\-Xmx" || JAVA_OPTS="${JAVA_OPTS} -Xmx256m"
fi

if [ "x${JAVA_ENABLE_DEBUG}" != "x" ] && [ "${JAVA_ENABLE_DEBUG}" != "false" ]; then
  java_debug_args="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=${JAVA_DEBUG_PORT:-5005}"
fi

exec java ${java_debug_args} ${JAVA_OPTS} ${JAVA_ARGS} -jar /opt/learning/*.jar

