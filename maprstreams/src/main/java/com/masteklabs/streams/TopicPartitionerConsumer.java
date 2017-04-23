package com.masteklabs.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

//Consumer program to read from a specific partition
//if 99999 is send as input to partition id, it reads from all partitions.
public class TopicPartitionerConsumer {
	
	public static void main(String[] args) {
	
		final String TOPIC_1="/user/teststream1:testtopic4";
		final int READ_FROM_ALL_PARTITIONS=99999;
		final String COMMA=",";
		if(args.length < 2){
			System.out.println("Proper usage is :java SimpleProducer <consumer groupId> <offsetReset(earliest,latest,none)> <partition id>");
			System.exit(0);
		}

        KafkaConsumer<String, String> consumer;
        String groupId=args[0];
        String offsetReset=args[1];
        int partitionId=Integer.parseInt(args[2]);
        Properties props=new Properties();
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", offsetReset);
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id",groupId);
        consumer = new KafkaConsumer<String, String>(props);
       List<TopicPartition> partitions=new ArrayList<TopicPartition>();
       
       if(partitionId==READ_FROM_ALL_PARTITIONS){
    	   consumer.subscribe(Arrays.asList(TOPIC_1));
       }
       else{
       partitions.add(new TopicPartition(TOPIC_1, partitionId));
       //
        consumer.assign(partitions);
       }
        
    
        StringBuilder offsets=new StringBuilder();
        while (true) {
      
        	offsets=new StringBuilder();
           ConsumerRecords<String, String> records = consumer.poll(100);
              for (ConsumerRecord<String, String> record : records){
            	  offsets.append(record.value());
            	  offsets.append(COMMA);
              }
         
              System.out.println(offsets.toString());
        }     
     
	}

}
