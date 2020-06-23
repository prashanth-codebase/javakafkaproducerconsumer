package org.example.custompartitioner;

public interface Constants {
		
	//private static final String KAFKA_SERVER_URL = "192.168.0.101";
	//private static final int KAFKA_SERVER_PORT = 9092;
	public static final String KAFKA_SERVER_URLs = "192.168.0.101:9092,192.168.0.101:9093,192.168.0.101:9094";
	public static String TOPIC_NAME="test-topic-mulpart-replicas";	
	public static String ACKS="1";
	public static String BATCH_SIZE="10";
	public static String BUFFER_MEMORY="33554432";
	public static String LINGER_MS="100";
	public static String RETRIES="3";
	
	public static String CONSUMER_GROUP_ID="consumerGroup1";
	
	public static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
	
	public static String OFFSET_RESET_LATEST="latest";
	
	public static String OFFSET_RESET_EARLIER="earliest";
	
	public static Integer MAX_POLL_RECORDS=1;
	
	public static Integer REQUEST_TIMEOUT_MS_CONFIG=10000;

}
