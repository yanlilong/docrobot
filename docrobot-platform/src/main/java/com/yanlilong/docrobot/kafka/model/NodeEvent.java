package com.yanlilong.docrobot.kafka.model;


import org.alfresco.rest.api.model.NodePermissions;

import java.util.Date;
import java.util.List;

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
    private EventType eventType;
    private String path;
    private Date created;
    private Date modified;
    private String creator;
    private String modifier;
    private String mimetype;
    private String contentType;
    private String siteId;
    private Long size;
    private String parent;
    private String authority;
    private String permission;
    private NodePermissions permissions;
    private List<String> tags;

    public NodeEvent(NodeEventBuilder builder) {
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public static class NodeEventBuilder{
       private String nodeRef;
       private EventType eventType;
       private String path;
       private Date created;
       private Date modified;
       private String creator;
       private String modifier;
       private String mimetype;
       private String contentType;
       private String siteId;
       private Long size;
       private String parent;
       private String authority;
       private String permission;
       private NodePermissions permissions;
       private List<String> tags;

       public NodeEventBuilder() {
       }

       public NodeEventBuilder nodeRef(String nodeRef) {
           this.nodeRef = nodeRef;
           return this;
       }

       public NodeEventBuilder eventType(EventType eventType) {
           this.eventType = eventType;
           return this;
       }

       public NodeEventBuilder path(String path) {
           this.path = path;
           return this;
       }

       public NodeEventBuilder created(Date created) {
           this.created = created;
           return this;
       }

       public NodeEventBuilder modified(Date modified) {
           this.modified = modified;
           return this;
       }

       public NodeEventBuilder creator(String creator) {
           this.creator = creator;
           return this;
       }

       public NodeEventBuilder modifier(String modifier) {
           this.modifier = modifier;
           return this;
       }

       public NodeEventBuilder mimetype(String mimetype) {
           this.mimetype = mimetype;
           return this;
       }

       public NodeEventBuilder contentType(String contentType) {
           this.contentType = contentType;
           return this;
       }

       public NodeEventBuilder siteId(String siteId) {
           this.siteId = siteId;
           return this;
       }

       public NodeEventBuilder size(Long size) {
           this.size = size;
           return this;
       }

       public NodeEventBuilder parent(String parent) {
           this.parent = parent;
           return this;
       }

       public NodeEventBuilder authority(String authority) {
           this.authority = authority;
           return this;
       }

       public NodeEventBuilder permission(String permission) {
           this.permission = permission;
           return this;
       }

       public NodeEventBuilder permissions(NodePermissions permissions) {
           this.permissions = permissions;
           return this;
       }

       public NodeEventBuilder tags(List<String> tags) {
           this.tags = tags;
           return this;
       }
       public NodeEvent build(){
           return new NodeEvent(this);
       }
   }

}
