# we use cloudera quickstart vm for doing prestacop control project
# for this, please download vmware and cloudera-quickstart-vm-5.13.0-0-vmware. After installing vmare
# vmware launch cloudera vm and wait.

# now, we need to install java 1.8, spark2 and kafka as a parcel on cloudera vm.

# install spark 2 cloudera  : https://www.youtube.com/watch?v=lQxlO3coMxM&t=1385s

# First, launch cloudera manager express icon that you can find in the desktop

sudo service cloudera-scm-agent stop
sudo service cloudera-scm-server stop

#################### Install java 8:

sudo cp /home/cloudera/Desktop/jdk-8u251-linux-x64.tar.gz /usr/java/

sudo tar xvf jdk-8u251-linux-x64.tar.gz
sudo mv jdk1.8.0_251/ java8

vi ~/.bash_profile
JAVA_HOME=/usr/java/java8
export JAVA_HOME
PATH=/usr/java/java8/bin:$PATH:$HOME/bin
source ~/.bash_profile

sudo vi /etc/default/cloudera-scm-server
export JAVA_HOME=/usr/java/java8

sudo service cloudera-scm-server start
sudo service cloudera-scm-agent start

#################### Install spark2 jar :

sudo cp SPARK2_ON_YARN-2.4.0.cloudera2.jar /opt/cloudera/csd/

# on cloudera manager :
# - set java on -> all hosts -> configuration -> advanced
# - and disable parcel option -> validate parcel relations

sudo service cloudera-scm-agent restart
sudo service cloudera-scm-server restart

# go to parcel -> download, distribute and activate Spark 2 (you will find the version that we selected previously)
# go to parcel -> download, distribute and activate CDH5.

# delete spark1 on cloudera manager, make configuration to (None) when click on delete and then delete it.
# then, click add service on cloudera manager, and add Spark2.

# Next add service, you need to restart the cluster.

#################### Install kafka on cloudera : https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-centos-7

cd /etc/yum.repos.d
sudo wget http://archive.cloudera.com/kafka/redhat/6/x86_64/kafka/
sudo yum clean all
sudo yum install kafka
sudo yum install kafka-server
sudo service kafka-server start

sudo jps

#################### collecte data

# copie data to desktop of cloudera VM

mkdir atldev01/ && cd atldev01/
mv /home/cloudera/Desktop/3498_1146042_bundle_archive/ /home/cloudera/atldev01/drone-data
hdfs dfs -mkdir -p /user/cloudera/drone/data/
hdfs dfs -put ./drone-data/Parking_Violations_Issued_-_Fiscal_Year_2017.csv  /user/cloudera/drone/data/

# add to kafka properties automatique creation of topic if not exist
sudo vi /usr/lib/kafka/config/server.properties
# add this conf and save the file : auto.create.topics.enable=true

yes | rm prestacop-drone-control-0.0.1-SNAPSHOT.jar && mv ../Desktop/prestacop-drone-control-0.0.1-SNAPSHOT.jar ./

#################### Test application

#Meth 1:

curl -X PUT -H "Content-Type: application/json" -d '{"drone_Id": "342323758425", "drone_Location_latitude": "-18.345", "drone_Location_longitude": "45.895", "drone_Time": "2020-05-05 08:04", "plate_ID": "", "violation_Code": ""}' http://localhost:9005/kafka/publish

curl -X PUT -H "Content-Type: application/json" -d '{"drone_Id": "34232375842ZEREZR5", "drone_Location_latitude": "-18.543","drone_Location_longitude": "45.345", "drone_Time": "2020-05-05 08:05", "plate_ID": "DF543KJ", "violation_Code": "76"}' http://localhost:9005/kafka/publish

curl -X PUT -H "Content-Type: application/json" -d '{"drone_Id": "H43BR342324DREZR5", "drone_Location_latitude": "-154.543","drone_Location_longitude": "23.345", "drone_Time": "2020-05-06 14:54", "plate_ID": "NEED_INTERVENTION", "violation_Code": "65"}' http://localhost:9005/kafka/publish


#Meth 2:
curl -X PUT 'http://localhost:9005/kafka/publishEvents?nbEvents=20'

#Meth 3:
curl -X PUT 'http://localhost:9005/kafka/publishEventsHistory?nbEvents=20'