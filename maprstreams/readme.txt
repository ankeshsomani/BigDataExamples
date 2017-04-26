This contains sample producer and consumer programs reading and writing to MapR streams topics and partitions.
This also contains saprk streaming program using MapR stream topic as source.

On a MapR 5.1.0 sandbox I updated Spark to 1.6.1 and ensured the Kafka libraries and client programs are installed:

# yum -y install mapr-spark mapr-kafka

Please refer to below link in case you need to install a specific version of mapr packages.
http://maprdocs.mapr.com/home/AdvancedInstallation/InstallingMapRSoftware-service-packages.html

Now become the mapr user:

# su - mapr
Now clone this repo:

build the project using maven on your local machine
# mvn clean install

The output file will be generated in target folder
copy the jar file "maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jar" to sandbox at /home/mapr folder

Create a stream and topics to test with. 

# maprcli stream create -path /user/teststream1
# maprcli stream topic create -path /sample-stream  -topic topic1
# maprcli stream topic create -path /sample-stream  -topic testtopic4 -partitions 3

cd /home/mapr

Now run the consumer program to read messages from topic /user/teststream1:topic1
java -cp `mapr classpath`:./maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.masteklabs.streams.SimpleConsumer <consumer groupId> <offsetReset(earliest,latest,none)>
Ex/-
# java -cp `mapr classpath`:./maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jarcom.masteklabs.streams.SimpleConsumer test latest


Now run the Producer program to produce messages for topic /user/teststream1:topic1
java -cp `mapr classpath`:./maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.masteklabs.streams.SimpleProducer <bufferTime> <bufferSize> <numRecords to produce>
Ex/-
# java -cp `mapr classpath`:./maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.masteklabs.streams.SimpleProducer 20 223232 300

Similarly TopicPartitionProducer and TopicPartitionConsumer can be tested

Run the TopicPartitionProducer program to send messages to a specific partition of topic /user/teststream1:testtopic4
java -cp `mapr classpath`:./maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.masteklabs.streams.TopicPartitionProducer <bufferTime> <bufferSize> <numRecords to produce>
Ex/-
java -cp `mapr classpath`:./maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.masteklabs.streams.TopicPartitionProducer 20 223232 300

Run the TopicPartitionerConsumer program to read messages from a specific partition or all partitions of topic /user/teststream1:testtopic4
java -cp `mapr classpath`:./maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.masteklabs.streams.TopicPartitionerConsumer <consumer groupId> <offsetReset(earliest,latest,none)> <partition id>
//if 99999 is send as input to partition id, it reads from all partitions.

Ex/- to read from partition 1 of topic /user/teststream1:testtopic4
# java -cp `mapr classpath`:./maprstreams-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.masteklabs.streams.TopicPartitionerConsumer  test4 latest 1


Test Spark streaming word count program to read from MapR stream topic /user/teststream1:topic1
# /opt/mapr/spark/spark-1.6.1/bin/spark-submit \
    --master local[2] \
    --class com.masteklabs.streams.SparkConsumer \
        /home/mapr/maprstreams2.jar localhost:9092 /user/teststream1:topic1