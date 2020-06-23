package org.example.callbackproducer;

import java.util.Properties;
import java.util.stream.IntStream;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.simpleproducer.Constants;

public class Producer {

	private final KafkaProducer<String, String> producer;
//	private static final String KAFKA_SERVER_URL = "192.168.0.101";
//	private static final int KAFKA_SERVER_PORT = 9092;
	
	private static final String CLIENT_ID = "CallBackProducer";

	public Producer() {
		Properties props = new Properties();
		props.put("bootstrap.servers",Constants.KAFKA_SERVER_URLs);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
	}

	public static void main(String args[]) {
		Producer p = new Producer();
		try {
			IntStream.range(0, 300).forEach(i -> {
				long startTime = System.currentTimeMillis();
				String messageStr =  "message " + i;
				p.producer.send(new ProducerRecord<>("test-topic-mulpart-repli-9092", String.valueOf(i) , messageStr), 
						new ProducerCallback(startTime,String.valueOf(i),messageStr));
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.producer.close();
		}
	}
}
