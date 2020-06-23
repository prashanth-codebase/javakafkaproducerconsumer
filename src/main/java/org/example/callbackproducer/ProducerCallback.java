package org.example.callbackproducer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerCallback implements Callback{

	private final long startTime;
	private final String key;
	private final String message;

	public ProducerCallback(long startTime, String key, String message) {
		System.out.println("inside producer callback");
		this.startTime = startTime;
		this.key = key;
		this.message = message;
	}
	
	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			System.out.println(message+" sent to partition("+metadata.partition()+
					")," + "offset("+metadata.offset()+") in "+ elapsedTime+" ms");
		} else {
			exception.printStackTrace();
		}
	}
}