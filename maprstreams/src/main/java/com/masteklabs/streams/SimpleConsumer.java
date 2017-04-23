package com.masteklabs.streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
public class SimpleConsumer {


	//Consumer Program to read normally from a topic
	public static void main(String[] args) throws IOException {
		
		//Full topic name with stream path included in it
		final String TOPIC_NAME = "/user/teststream1:topic1";
		if(args.length < 2){
			System.out.println("Proper usage is :java SimpleProducer <consumer groupId> <offsetReset(earliest,latest,none)>");
			System.exit(0);
		}
        // and the consumer
        KafkaConsumer<String, String> consumer;
        String groupId=args[0];
        String offsetReset=args[1];
        Properties props=new Properties();
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", offsetReset);
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id",groupId);
       consumer = new KafkaConsumer<String, String>(props);
       
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
      
        
        while (true) {
        //	System.out.println("polling now "+ i);
           ConsumerRecords<String, String> records = consumer.poll(10);
              for (ConsumerRecord<String, String> record : records){
                 System.out.printf("offset = %d, key = %s, value = %s\n", 
                 record.offset(), record.key(), record.value());
              }
             
        }     
	}

}
