sudo apt-add-repository -y ppa:webupd8team/java
sudo apt-get -y update
echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
sudo apt-get -y install oracle-java7-installer 
JAVA_HOME=/usr/lib/jvm/java-7-oracle
sudo apt-get -y install ssh
sudo apt-get -y install rsync
wget http://download.nextag.com/apache/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz
tar -xvf hadoop-2.7.2.tar.gz
echo export JAVA_HOME=/usr/lib/jvm/java-7-oracle >> hadoop-2.7.2/etc/hadoop/hadoop-env.sh
sed -i '/<configuration>/a <property>\n<name>fs.defaultFS</name>\n<value>hdfs://localhost:9000</value>\n</property>' hadoop-2.7.2/etc/hadoop/core-site.xml
sed -i '/<configuration>/a <property>\n<name>dfs.replication</name>\n<value>1</value>\n</property>' hadoop-2.7.2/etc/hadoop/hdfs-site.xml
ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa
cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys
chmod 0600 ~/.ssh/authorized_keys
cp hadoop-2.7.2/etc/hadoop/mapred-site.xml.template hadoop-2.7.2/etc/hadoop/mapred-site.xml
#sed -i '/<configuration>/a <property>\n<name>mapreduce.framework.name</name>\n<value>yarn</value>\n</property>' hadoop-2.7.2/etc/hadoop/mapred-site.xml
#sed -i '/<configuration>/a <property>\n<name>yarn.nodemanager.aux-services</name>\n<value>mapreduce_shuffle</value>\n</property>' hadoop-2.7.2/etc/hadoop/yarn-site.xml
