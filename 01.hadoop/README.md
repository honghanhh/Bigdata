## 1. Scenario
You are given a file containing meterological data about cities all over the planet. You have to implement two map-reduce jobs that:
- compute the maximum temperature for each year
- compute the number of months which had a temperature higher than a given value (a parameter).


## 2. Input data
The data is saved as [meteosample.txt](https://github.com/honghanhh/bigdata/blob/master/data/meteosample.txt) and given as input. 

The format of a line in the file is [ day : month : year : temperature : city ]
```
26 : 9 : 2000 : -20 : Santiago
28 : 7 : 1973 : -18 : Paris
29 : 12 : 1921 : -40 : Wellington
23 : 3 : 2015 : -31 : Bridgetown
22 : 11 : 2003 : 7 : Asmara
3 : 6 : 2007 : 5 : Castries
13 : 5 : 1936 : 43 : Pretoria
14 : 6 : 1945 : 20 : Nairobi
5 : 5 : 1973 : 11 : Alofi
22 : 5 : 1900 : -37 : Kigali
11 : 3 : 1915 : 43 : St. John's
3 : 9 : 1940 : -17 : Honiara
7 : 10 : 1947 : 29 : Addis Ababa
```

## 3. Compilation and Execution
Let us assume we are in the home directory of './bigdata/'
1. Format to initialize the Hadoopfile system by running hdfs namenode.
```
hdfs namenode -format 
```
2. Create a single node cluster:
```
start-dfs.sh 
``` 
3. Use jps command (Java Virtual Machine Process Status Tool) to check all the Hadoop daemons like NameNode, DataNode, ResourceManager, NodeManager etc. which are running on the machine:
```
jps 
```
4. Create an input directory in HDFS:
```
hdfs dfs -mkdir /input 
```
5. Copy the input file named meteosample.txt in the input directory of HDFS:
```
hdfs dfs -put meteosample.txt /input 
```
6. Verify the files in the input directory

```
fs -ls input_dir/ 
```
7. Run the application by taking the input files from the input directory:

    7.1 Create jar file:
```
jar cf wc.jar <your-application-package>
```

    7.2 Run application:
```
hadoop jar MaxTemp.jar MaxTemp /input /output 
```

8. Verify the resultant files in the output folder:
```
fs -ls output_dir/ 
```
9. See the output (all files genereated by HDFS):
```
hdfs dfs -cat /output/* 
```

10. Stop single node cluster:
```
stop-dfs.sh
```
