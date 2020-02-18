
HERE=`pwd`
cd ..
HAGIHOME=`pwd`
cd $HERE

docker network create haginet

#####################
CONTNAME=master
CONTCPU=0
docker run --cpuset-cpus="$CONTCPU" --net haginet --name $CONTNAME --hostname $CONTNAME -p 50070:50070 -p 8080:8080 -d -v $HAGIHOME/hadoop-2.7.1:/root/hadoop -v $HAGIHOME/spark-2.4.3-bin-hadoop2.7:/root/spark -v $HAGIHOME/appli:/root/appli server
#####################
CONTNAME=slave1
CONTCPU=1
docker run --cpuset-cpus="$CONTCPU" --net haginet --name $CONTNAME --hostname $CONTNAME -d -v $HAGIHOME/hadoop-2.7.1:/root/hadoop -v $HAGIHOME/spark-2.4.3-bin-hadoop2.7:/root/spark server 
#####################
CONTNAME=slave2
CONTCPU=2
docker run --cpuset-cpus="$CONTCPU" --net haginet --name $CONTNAME --hostname $CONTNAME -d -v $HAGIHOME/hadoop-2.7.1:/root/hadoop -v $HAGIHOME/spark-2.4.3-bin-hadoop2.7:/root/spark server 
#####################
CONTNAME=slave3
CONTCPU=3
docker run --cpuset-cpus="$CONTCPU" --net haginet --name $CONTNAME --hostname $CONTNAME -d -v $HAGIHOME/hadoop-2.7.1:/root/hadoop -v $HAGIHOME/spark-2.4.3-bin-hadoop2.7:/root/spark server 
#####################

