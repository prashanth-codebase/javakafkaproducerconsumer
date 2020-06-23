package org.example.custompartitioner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {
	
	private final KafkaConsumer<String, String> consumer;
	private static final String CLIENT_ID = "SampleConsumer3";
	
	public Consumer() {
		Properties props = new Properties();
		//props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,Constants.KAFKA_SERVER_URLs);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.101:9092");
		props.put(ConsumerConfig.CLIENT_ID_CONFIG,CLIENT_ID);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.CONSUMER_GROUP_ID);
		props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "com.example3.CustomPartitioner");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
	}
	
	public static void main (String args[]) {
		Consumer c = new Consumer();
		
		Collection<String> topics = new ArrayList<>();
		//topics.add("test_topic");
		topics.add("test-topic-mulpart-replicas");
		c.consumer.subscribe(topics);
		
		try {
			while(true) {
				ConsumerRecords<String, String> records = c.consumer.poll(20000);
				System.out.println("consumer waiting/polling for 20000 milliseconds/20seconds");
				for(ConsumerRecord<String, String> record: records) {
					System.out.println(String.format("Topic: - %s, Partition: - %d, Value: %s",
							record.topic(), record.partition(), record.value()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.consumer.close();
		}
	}
}