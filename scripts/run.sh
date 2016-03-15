cd hadoop
bin/hadoop fs -mkdir /input
bin/hadoop fs -mkdir /output
cd ..
hadoop/bin/hadoop fs -put  /mnt/raid/data/dataset /input
hadoop/bin/hadoop jar hadoop-tera-sort-0.0.1-SNAPSHOT.jar hadoop.terasort.hawk.iit.edu.TeraSort /input /output
hadoop/bin/hadoop fs -get  /output/part-r-00000 /mnt/raid/data/
cd /mnt/raid/data
~/GenSort/64/valsort part-r-00000
