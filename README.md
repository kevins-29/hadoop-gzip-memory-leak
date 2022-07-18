## Hadoop GZip Memory Leak 

This project demonstrates the GZip memory leak identified by [HADOOP-12007](https://issues.apache.org/jira/browse/HADOOP-12007) 

### Configure

Updating Hadoop version in `gradle.properties`

```properties
versionHadoop=3.4.0-SNAPSHOT
```

### Run

```shell
./gradlew run
```

### Monitor

```shell
while true; do echo \"$(date +%Y-%m-%d' '%H:%M:%S)\",$(pmap -x <PID> | grep "total kB" | awk '{print $4}'); sleep 10; done;
```

### Example Output

```shell
"2022-07-18 03:21:49",1113060
"2022-07-18 03:22:00",1126184
"2022-07-18 03:22:10",1126248
"2022-07-18 03:22:20",1126248
"2022-07-18 03:22:30",1130204
"2022-07-18 03:22:40",1130216
"2022-07-18 03:22:50",1130244
"2022-07-18 03:23:00",1130776
"2022-07-18 03:23:10",1130776
"2022-07-18 03:23:20",1130776
"2022-07-18 03:23:30",1130776
"2022-07-18 03:23:40",1130888
"2022-07-18 03:23:50",1130888
"2022-07-18 03:24:00",1130888
"2022-07-18 03:24:10",1130928
"2022-07-18 03:24:20",1130928
"2022-07-18 03:24:30",1130928
"2022-07-18 03:24:40",1131204
"2022-07-18 03:24:50",1131204
"2022-07-18 03:25:00",1131204
"2022-07-18 03:25:10",1131204
"2022-07-18 03:25:20",1139044
"2022-07-18 03:25:30",1140900
"2022-07-18 03:25:40",1140900
"2022-07-18 03:25:50",1140900
"2022-07-18 03:26:00",1140900
"2022-07-18 03:26:10",1141164
"2022-07-18 03:26:20",1141164
"2022-07-18 03:26:30",1141164
"2022-07-18 03:26:40",1141164
"2022-07-18 03:26:50",1141164
"2022-07-18 03:27:00",1141164
"2022-07-18 03:27:10",1141164
```

### Conclusion

Since we are setting the JVM Arguments `-XX:+AlwaysPreTouch -Xms1g -Xmx1g` the total memory footprint of the Java 
process should fluctuate at around `1048576 kb`, but we can see here that the total memory foot is growing, albeit 
slowly. 
