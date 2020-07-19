package com.yanlilong.docrobot.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanlilong.docrobot.kafka.model.NodeEvent;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodeEvent;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodePermissions;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.Properties;

public class MessageService {
    private Logger logger = Logger.getLogger(MessageService.class);
    NodeRefToNodeEvent nodeTransformer;
    NodeRefToNodePermissions nodePermissionsTransformer;

    private String topic = "node-event";
    private String bootstrapServers = "localhost:9092";
    private KafkaProducer<String, String> producer;
    private ObjectMapper mapper = new ObjectMapper();
    public MessageService(NodeRefToNodeEvent nodeTransformer,NodeRefToNodePermissions nodePermissionsTransformer){
        this.nodeTransformer=nodeTransformer;
        this.nodePermissionsTransformer=nodePermissionsTransformer;
    }

    @PostConstruct
    public void init() {
        producer = new KafkaProducer<String, String>(createProducerConfig());
    }
    public void ping(NodeRef nodeRef) {
        NodeEvent e = nodeTransformer.transform(nodeRef);
        e.setEventType(NodeEvent.EventType.PING);
        e.setPermissions(nodePermissionsTransformer.transform(nodeRef));
        publish(e);
    }

    public void publish(NodeEvent event) {
        try {
            final String message = mapper.writeValueAsString(event);

            if (message != null && message.length() != 0) {
                producer.send(new ProducerRecord<String, String>(topic, message));
            }
        } catch (JsonProcessingException jpe) {
            logger.error(jpe);
        }
    }

    private Properties createProducerConfig() {
        final Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", "all");
        props.put("retries",0);
        props.put("batch.size",16384);
        props.put("linger.ms",1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }
}

