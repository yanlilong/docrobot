package com.yanlilong.docrobot.kafka.transform;

import com.yanlilong.docrobot.kafka.model.NodePermissions;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PermissionService;

public class NodeRefToNodePermissions {
    private NodeService nodeService;
    private PermissionService permissionService;

    public NodeRefToNodePermissions(NodeService nodeService, PermissionService permissionService) {
        this.nodeService = nodeService;
        this.permissionService = permissionService;
    }

    public NodePermissions transform(NodeRef nodeRef) {
        boolean inherits = permissionService.getInheritParentPermissions(nodeRef);
        NodePermissions nodePermissions=null;
        return nodePermissions;
    }

}
