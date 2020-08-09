package com.yanlilong.docrobot.kafka.service.consumer;

import com.yanlilong.docrobot.kafka.service.ProducerEvent;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConsumerConfiguration {
    private final static Logger LOGGER = Logger.getLogger(ProducerEvent.class);
    private static String group = "group1";
    private static String topic = "alfresco-node-events";

    private Map<String, Object> getBaseConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return config;
    }

    public Consumer<String, String> getConsumer() {
        return  new KafkaConsumer<>(getBaseConfig());
    }
    public void consumerSubscribe(Consumer<String,String> consumer){
        consumer.subscribe(Collections.singleton(topic));
        while(true){
            ConsumerRecords<String,String> records=consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
               LOGGER.info(record.value());
                }
            consumer.commitAsync();
            }
    }

}
