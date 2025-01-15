jarName=nohi-web-1.0-SNAPSHOT.jar
echo "jps -ml|grep ${jarName} | grep -v grep | awk '{print \$1}'"
LIVEPID=`jps -ml|grep ${jarName}  |grep -v grep | awk '{print $1}'`
echo "PID ${LIVEPID}"
if [ -z $LIVEPID ];then
    echo "pid is null"
else
    echo $LIVEPID
    kill -9 $LIVEPID
fi
