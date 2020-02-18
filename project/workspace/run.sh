

###############################
CONTSLAVE=2

docker run --net storm-project --link master:nimbus -it --rm -v $(pwd)/WordCountTopology.jar:/topology.jar storm:0.9.7 storm jar /topology.jar WordCountTopology topology $CONTSLAVE


