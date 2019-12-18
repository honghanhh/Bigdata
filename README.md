# [MI3.02: Cloud and BigData With Java]


###  Master Hadoop, Spark, streaming, and scaling in BigData


## About the reporestory

This is a hand-on instructions from setup to mini-labworks to help us get familar with concepts of Hadoop and Spark, based on lectures and labworks of [Prof. Daniel Hagimont](http://sd-127206.dedibox.fr/hagimont/).


## Table of Contents

### 00. Installation prerequisite for labworks
Preferable Ubuntu 16.04 with enough space to install Java, Eclipse, Hadoop, etc...

1. Open the Linux terminal and install __openssh-server__.
```console
$ sudo su
$ apt-get update
$ apt-get install openssh-server
```
2. Create a user
```
$ useradd hanh 
$ passwd hanh 
   New passwd: 
   Retype new passwd 
```
3. SSH Setup and Key Generation

```
$ ssh-keygen -t rsa 
$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys 
$ chmod 0600 ~/.ssh/authorized_keys 
```
You can test on your localhost by:
```
ssh localhost
```
![](./img/ssh_localhost.png)
### 01. Hadoop
You are given a file containing meterological data about cities all over the planet. You have to implement two map-reduce jobs that:
- compute the maximum temperature for each year
- compute the number of months which had a temperature higher than a given value (a parameter).
- the format of a line in the file is : day : month : year : temperature : city
### 02. Spark
Implement the same application as in the previous labwork (Hadoop), but with Spark.
### 03. Streamming
### 04. Scaling
### 05. Project
(updating)

