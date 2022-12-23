package io.conduktor.demo.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.LogContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class ConsumerDemo {
	private static final Logger log = LoggerFactory.getLogger(ProducerDemowithCallback.class);

	public static void main(String[] args) {
		// create producer properties

		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-application");
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		// create Consumer
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

		// subscribe consumer to our topics
		consumer.subscribe(Arrays.asList("consumer_demo"));

		// poll for new data

		while (true) {

			ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : consumerRecords) {
				log.info("Key" + record.key());
				log.info("value" + record.value());
				log.info("Partition" + record.partition());
				log.info("Offset" + record.offset());

			}
		}

	}
}
