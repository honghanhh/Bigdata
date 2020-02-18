from pyspark import SparkContext, SparkConf

conf = SparkConf().setAppName("WordCount")
sc = SparkContext(conf=conf)

inputFile = "hdfs://master:54310/input/data.txt"
outputDir = "hdfs://master:54310/output"

import time

start_time = time.time()

words = sc.textFile(inputFile).flatMap(lambda line : line.split(" "))

counts = words.map(lambda w : (w, 1)).reduceByKey(lambda a, b: a + b)      

counts.saveAsTextFile(outputDir)

end_time = time.time()

print("==================================");
print("Execution time : %s seconds" % (end_time - start_time))
print("==================================");

