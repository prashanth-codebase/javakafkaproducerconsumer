package org.example.avrodemo;

import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.example.avrodemo.dto.Customer;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;

public class AvroProducer {

	private final KafkaProducer<Long, Customer> producer;
	private static final String CLIENT_ID = "AvroProducer";
	private final static String TOPIC = "avro-topic";

	public AvroProducer() {
		Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        // Configure the KafkaAvroSerializer.
       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        // Schema Registry location.
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                "http://192.168.0.101:8081");
        producer = new KafkaProducer<>(props);
	}

	public static void main(String args[]) {
		
		/*
		 * Long key = 1L; String userSchema = "{\"type\":\"record\"," +
		 * "\"name\":\"myrecord\"," +
		 * "\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}"; Schema.Parser parser =
		 * new Schema.Parser(); Schema schema = parser.parse(userSchema); GenericRecord
		 * avroRecord = new GenericData.Record(schema); avroRecord.put("f1", "value1");
		 */
		
		AvroProducer p = new AvroProducer();
		Customer customer = Customer.newBuilder().setId(1).setFullname("Prashanth").setCity("Hyderabad").build();
		try {
			IntStream.range(1, 30).forEach(i -> {
				System.out.println("message " + i);
				//p.producer.send(new ProducerRecord<>("test-topic", String.valueOf(i), "message " + i));
				p.producer.send(new ProducerRecord<>(TOPIC, 1L * i, customer));
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.producer.close();
		}
	}
}