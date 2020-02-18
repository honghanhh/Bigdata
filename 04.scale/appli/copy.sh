
hdfs dfs -rm /input/*
hdfs dfs -rm /output/*
hdfs dfs -rmdir /output

hdfs dfs -mkdir /input
hdfs dfs -put data.txt /input
