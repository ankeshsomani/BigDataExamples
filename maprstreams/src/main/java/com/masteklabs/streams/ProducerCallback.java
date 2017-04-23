package com.masteklabs.streams;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerCallback implements Callback{

	public void onCompletion(RecordMetadata meta, Exception e) {
		if(e !=null){
			e.printStackTrace();
		}
		System.out.println("offset is :-"+meta.offset());
	}

	
}
