package org.example.custompartitioner;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class CustomPratitionerProducer {
	private static final String topic = "SenserTopic";
	private final KafkaProducer<String, String> producer;
	private static final String CLIENT_ID = "CustomPartionerProducer";

	public CustomPratitionerProducer() {
		Properties props = new Properties();
		//props.put("bootstrap.servers",Constants.KAFKA_SERVER_URLs);
		props.put("bootstrap.servers","192.168.0.101:9092");
		props.put("client_id", CLIENT_ID);
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"org.example.custompartitioner.SensorPartitioner");
		props.put("speed.sensor.name", "TSS");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
	}

	public static void main(String args[]) {
		CustomPratitionerProducer p = new CustomPratitionerProducer();
		try {
		     // Producer<String, String> producer = new KafkaProducer <>(props);

		         for (int i=0 ; i<10 ; i++)
		         p.producer.send(new ProducerRecord<>(topic,"SSP"+i,"500"+i));

		         for (int i=0 ; i<10 ; i++)
		         p.producer.send(new ProducerRecord<>(topic,"TSS","500"+i));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.producer.close();
		}
	}
}