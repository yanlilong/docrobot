package com.yanlilong.docrobot.kafka.model;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeEvent {

    public enum EventType {
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
}
