package org.example.avrodemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.avrodemo.dto.Customer;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.LongDeserializer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class AvroConsumer {
	
	private final KafkaConsumer<Long, Customer> consumer;
	private static final String CLIENT_ID = "AvroConsumer";
	private final static String TOPIC = "avro-topic";
	
	public AvroConsumer() {
		Properties props = new Properties();
		//props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL+":"+KAFKA_SERVER_PORT);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,Constants.KAFKA_SERVER_URLs);
		props.put(ConsumerConfig.CLIENT_ID_CONFIG,CLIENT_ID);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "AvroConsumerGroupID");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,KafkaAvroDeserializer.class.getName());  
        //Use Specific Record or else you get Avro GenericRecord.
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        //Schema registry location.
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,"http://192.168.0.101:8081"); 
		consumer = new KafkaConsumer<>(props);
	}
	
	public static void main (String args[]) {
		AvroConsumer c = new AvroConsumer();
		
		Collection<String> topics = new ArrayList<>();
		//topics.add("test-topic");
		topics.add(TOPIC);
		c.consumer.subscribe(topics);
		
		try {
			while(true) {
				ConsumerRecords<Long, Customer> records = c.consumer.poll(20000);
				System.out.println("consumer waiting/polling for 20000 milliseconds/20seconds");
				for(ConsumerRecord<Long, Customer> record: records) {
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