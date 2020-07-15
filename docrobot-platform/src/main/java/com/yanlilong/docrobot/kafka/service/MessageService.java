package com.yanlilong.docrobot.kafka.service;

import com.yanlilong.docrobot.kafka.transform.NodeRefToNodeEvent;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodePermissions;
import org.apache.kafka.clients.producer.KafkaProducer;

public class MessageService {
    NodeRefToNodeEvent nodeTransformer;
    NodeRefToNodePermissions nodePermissionsTransformer;

    private String topic="node-event";
    private String bootstrapServers="localhost;9092";
    private KafkaProducer<String,String> producer;
    //pivate ObjectMapper mapper=new ObjectMapper();

}
