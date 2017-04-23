package com.masteklabs.streams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

//Producer program to send to a specific partition
//This program sends records to 3 partitions, but it can be configured to send to 1 or all partitions
public class TopicPartitionProducer {
	public static KafkaProducer<String, String> producer;
	public static void main(String[] args) throws InterruptedException {
		if(args.length < 3){
			System.out.println("Proper usage is :java SimpleProducer <bufferTime> <bufferSize> <numRecords to produce>	");
			System.exit(0);
		}
		Properties props =new Properties();
		
		String topic="/user/teststream1:testtopic4";
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
	
		Integer bufferTime=Integer.parseInt(args[0]);
		Integer bufferSize=Integer.parseInt(args[1]);
		
		int numRecords=Integer.parseInt(args[2]);
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("streams.buffer.max.time.ms", bufferTime);
		props.put("buffer.memory", bufferSize);
		producer=new KafkaProducer<String, String>(props);
		int partid=0;
		 System.out.println("before calling producer");
		for(int i=0; i < numRecords; i++){
			
			String txt="Part"+partid+new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
			ProducerRecord <String,String> record=new ProducerRecord(topic,partid,null,txt);
			Callback cb=new ProducerCallback();
	
			producer.send(record,cb);
			Thread.sleep(20);
			partid++;
			if(partid==3){
				partid=0;
			}
	
		}
	}
	

}
