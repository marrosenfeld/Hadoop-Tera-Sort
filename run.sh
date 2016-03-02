cd hadoop-2.7.2
bin/hadoop fs -mkdir /input
bin/hadoop fs -mkdir /output
cd ..
wget http://www.ordinal.com/try.cgi/gensort-linux-1.5.tar.gz
mkdir GenSort
tar xvf gensort-linux-1.5.tar.gz -C GenSort
GenSort/64/gensort -a 1000 dataset
hadoop-2.7.2/bin/hadoop fs -put  dataset /input
hadoop-2.7.2/bin/hadoop jar hadoop-tera-sort-0.0.1-SNAPSHOT.jar hadoop.terasort.hawk.iit.edu.TeraSort /input /output
hadoop-2.7.2/bin/hadoop fs -get  /output/part-r-00000 .
GenSort/64/valsort part-r-00000
