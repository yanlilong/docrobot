package com.yanlilong.docrobot.kafka.model;


public class NodeEvent {
    public enum EventType{
        CREATE,
        UPDATE,
        DELETE,
        PING,
        GRANT,
        REVOKE,
        ENABLE_INHERIT,
        DISABLE_INHERIT
    }
    private String nodeRef;
}
