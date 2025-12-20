#!/bin/sh

pwd
BASE_PATH=$(cd `dirname $0`;pwd)
cd "$BASE_PATH" || exit
echo "currentPath:$BASE_PATH"
pwd
JAR_NAME=nohi-web-1.0-SNAPSHOT.jar

echo start app $JAR_NAME

export JAVA_OPT="-server -Xms1024m -Xmx2048m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m"

nohup java "$JAVA_OPT"  -jar ${JAR_NAME}  > /dev/null 2>&1 &

