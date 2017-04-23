package com.masteklabs.streams;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

//A simple MapR producer
public class SimpleProducer {
	public static KafkaProducer<String, String> producer;
	public static void main(String[] args) throws InterruptedException {
		Properties props =new Properties();
		if(args.length < 3){
			System.out.println("Proper usage is :java SimpleProducer <bufferTime> <bufferSize> <numRecords to produce>	");
			System.exit(0);
		}
		//Full topic name with stream path included in it
		final String TOPIC_NAME = "/user/teststream1:topic1";
		
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		Integer bufferTime=Integer.parseInt(args[0]);
		Integer bufferSize=Integer.parseInt(args[1]);
		
		int numRecords=Integer.parseInt(args[2]);
		
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("streams.buffer.max.time.ms", bufferTime);
		props.put("buffer.memory", bufferSize);
		producer=new KafkaProducer<String, String>(props);
		 System.out.println("before calling producer");
		for(int i=0; i < numRecords; i++){
			String txt="Newmsg:-"+i;
			ProducerRecord <String,String> record=new ProducerRecord<String,String>(TOPIC_NAME,txt);
			Callback cb=new ProducerCallback();
		//	System.out.println("before"+i);
			producer.send(record,cb);
		//	Thread.sleep(20);
		//	System.out.println("after"+i);
		}
	}
}
