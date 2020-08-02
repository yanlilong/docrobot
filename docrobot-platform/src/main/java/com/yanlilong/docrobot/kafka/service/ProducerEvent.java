package com.yanlilong.docrobot.kafka.service;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.log4j.Logger;


import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerEvent {
    private final static Logger LOGGER = Logger.getLogger(ProducerEvent.class);
    final static String topicName = "alfresco-nodes-event";
    // create instance for properties to access producer configs
    static Properties props = new Properties();
    static KafkaProducer<String, String> producer;

    public static void init() {
        // create instance for properties to access producer configs
        props.put("bootstrap.servers", "172.17.0.1:9092");
        // props.put("bootstrap.servers", "localhost:9092");
        //Assign localhost id
        //Set acknowledgements for producer requests.
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        //Specify buffer size in config
        props.put("batch.size", 16384);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    }

    public static void sendmessage() {
        producer = new KafkaProducer<>(props);
        Callback callback = new ProducerCallback();
        ProducerRecord<String, String> record;
        for (int i = 0; i < 10; i++) {
            record = new ProducerRecord<>(topicName,
                    Integer.toString(i), Integer.toString(i));
            sendAsync(record, callback, producer);
        }
        producer.flush();
    }


    static void sendAsync(ProducerRecord<String, String> producerRecord, Callback callback, KafkaProducer<String, String> kafkaProducer) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.submit(() -> kafkaProducer.send(producerRecord, callback).get());

    }

    private static class ProducerCallback implements Callback {
        ProducerCallback() {
        }

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception ex) {
            LOGGER.info("Received notification: [{}] and ex: [{}]" + recordMetadata, ex);
        }
    }
}
