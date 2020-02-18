



docker rm -f $(docker ps -qa)

docker network rm storm-project


docker network create storm-project

#######################
ZOONAME=some-zookeeper
docker run --net storm-project -d --restart always --name $ZOONAME zookeeper


#######################
NIMBUSNAME=master
CONTCPU=0

docker run --cpuset-cpus="$CONTCPU" --net storm-project -d --restart always --name $NIMBUSNAME --link $ZOONAME:zookeeper storm:0.9.7 storm nimbus


#######################
CONTNAME=slave1
CONTCPU=1
docker run --cpuset-cpus="$CONTCPU" --net storm-project -d --restart always --name $CONTNAME --link $ZOONAME:zookeeper --link $NIMBUSNAME:nimbus storm:0.9.7 storm supervisor

#######################
CONTNAME=slave2
CONTCPU=2
docker run --cpuset-cpus="$CONTCPU" --net storm-project -d --restart always --name $CONTNAME --link $ZOONAME:zookeeper --link $NIMBUSNAME:nimbus storm:0.9.7 storm supervisor


#######################
docker run --net storm-project -d -p 8080:8080 --restart always --name ui --link master:nimbus storm:0.9.7 storm ui
