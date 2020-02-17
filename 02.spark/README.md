## 1. Scenario
Program an application with Spark, preferably testing with the WordCount data and meteorology data (on previous labwork). 

## 2. Input data
### 2.1 WordCount
The data is saved as [filesample.txt](https://github.com/honghanhh/bigdata/blob/master/data/filesample.txt) and given as input. 

The format of a line in the file is
```
O mon jardin d'eau fraîche et d'ombre
Ma danse d'être mon c?ur sombre
Mon ciel des étoiles sans nombre
Ma barque au loin douce à ramer
...
```

### 2.2 Meterology
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
We use Eclipse to develop applications. After creating the Java project, add the following jars to build path:
```
$SPARK_HOME/jars/spark-core_2.11-2.2.0.jar
$SPARK_HOME/jars/scala-library-2.11.8.jar
$SPARK_HOME/jars/hadoop-common-2.7.3.jar
```
Then, package the application in a jar as in the previous labwork.
```
jar cf wc.jar <your-application-package>
```

You can try to execute locally or in cluster modes.
Locally :
- Your application should refer to local files (local to the file system)
```
spark-submit --class <classname> --master local <jarfile>
```
In cluster mode :
- Run HDFS as in the previous labwork
	we assume here that HDFS is accessible with : hdfs://master:54310
	as defined in core-site.xml
- Your application should refer to hsfd files : hdfs://master:54310/input/ …
- Start the spark daemons
	- On the master node: start-master.sh .You can inspect the master at : http://master:8080. It shows the URL of the master.
    - On the slave nodes: 
        start-slave.sh -c 1 <url-master> 

- Submit a job
```	
spark-submit --class <java-class-name> --master <url master> <jar> 
```
