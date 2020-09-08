package com.yanlilong.docrobot.kafka.service;

import com.yanlilong.docrobot.kafka.model.NodeEvent;
import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;

public class MessageService {
    private final static Logger LOGGER = Logger.getLogger(MessageService.class);
    ProducerEvent producerEvent;

    public MessageService(ProducerEvent producerEvent) {
        this.producerEvent = producerEvent;
    }


    public void publish(NodeEvent event) {
        try {
            producerEvent.init();
            StopWatch stopWatch = new StopWatch("thread run performance");
            stopWatch.start();
            producerEvent.sendmessage(event);
            stopWatch.stop();
            LOGGER.info(stopWatch.prettyPrint());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}

