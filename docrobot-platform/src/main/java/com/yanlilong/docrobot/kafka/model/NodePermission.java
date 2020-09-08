package com.yanlilong.docrobot.kafka.model;

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
public class NodePermission {
    private String authority;
    private String authorityType;
    private String permission;
    private boolean isInherited;
}
