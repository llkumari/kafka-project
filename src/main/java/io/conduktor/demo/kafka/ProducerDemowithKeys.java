package io.conduktor.demo.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class ProducerDemowithKeys {

	private static final Logger log = LoggerFactory.getLogger(ProducerDemowithKeys.class);

	public static void main(String[] args) {
		// create producer properties

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// create producer
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

		for (int i = 0; i < 10; i++) {
			
			String topic="first_topc";
			String value="Hello world" +i;
			String key="id"+i;
			// create a producer record
			final ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic,key,value);

			// send the data---asynchronous
			producer.send(producerRecord, new Callback() {

				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					// executes every time a record is successfully sent or an exception is thrown
					if (exception == null) {
						log.info("Received new metadata/ \n" + "Topic: " + metadata.topic() + "\n" + "Key"
								+ producerRecord.key() + "Partition: " + metadata.partition() + "\n" + "offset: "
								+ metadata.offset() + "\n" + "Timestamp: " + metadata.timestamp());
					} else {
						log.error("error while producing", exception);
					}

				}
			});
		}

		// flush data-synchronous
		producer.flush();// hey block untill this line of code unless all the data in previous line are
							// send to kafka

		// flush and close producer
		producer.close(); // actually close method also close flush method nternally, but just to show the
							// flush operation externally we wrote the above code
	}
}
