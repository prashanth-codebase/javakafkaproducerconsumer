package org.example.simpleproducer;

import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SimpleProducer {

	private final KafkaProducer<String, String> producer;
	private static final String CLIENT_ID = "SimpleProducer";

	public SimpleProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers",Constants.KAFKA_SERVER_URLs);
		props.put("client_id", CLIENT_ID);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.ACKS_CONFIG,Constants.ACKS);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG,Constants.BATCH_SIZE);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,Constants.BUFFER_MEMORY);
		props.put(ProducerConfig.LINGER_MS_CONFIG,Constants.LINGER_MS);
		props.put(ProducerConfig.RETRIES_CONFIG,Constants.RETRIES);
				
		producer = new KafkaProducer<>(props);
	}

	public static void main(String args[]) {
		SimpleProducer p = new SimpleProducer();
		try {
			IntStream.range(1, 30000).forEach(i -> {
				System.out.println("message " + i);
				//p.producer.send(new ProducerRecord<>("test-topic", String.valueOf(i), "message " + i));
				p.producer.send(new ProducerRecord<>("test-topic-mulpart-repli-9092", String.valueOf(i), "message " + i));
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.producer.close();
		}
	}
}