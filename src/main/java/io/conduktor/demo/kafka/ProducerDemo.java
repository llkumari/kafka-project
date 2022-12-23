package io.conduktor.demo.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Hello world!
 *
 */
public class ProducerDemo 
{
    public static void main( String[] args )
    {
        //create producer properties
    	
    	Properties properties= new Properties();
    	properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
    	properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    	properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    	
    	 //create producer
    	KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
    	
    	
    	//create a producer record
    	ProducerRecord<String, String> producerRecord=new ProducerRecord<String, String>("first_topc", "Hello world");
    	
    	
    	//send the data---asynchronous
    	producer.send(producerRecord);
    	
    	
    	//flush data-synchronous
    	producer.flush();//  hey block untill this line of code unless all the data in previous line are send to kafka
    	
    	
    	//flush and close producer
    	producer.close();  // actually close method also close flush method nternally, but just to show the flush operation externally we wrote the above code
    }
}
