package com.yanlilong.docrobot.kafka.transform;

import com.yanlilong.docrobot.kafka.model.NodeEvent;
import com.yanlilong.docrobot.kafka.model.NodePermission;
import com.yanlilong.docrobot.kafka.model.NodePermissions;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AccessPermission;
import org.alfresco.service.cmr.security.PermissionService;

import java.util.HashSet;
import java.util.Set;

public class NodeRefToNodePermissions {
    private NodeService nodeService ;
    private PermissionService permissionService;

    public NodeRefToNodePermissions(ServiceRegistry serviceRegistry) {
        this.nodeService = serviceRegistry.getNodeService();
        this.permissionService =serviceRegistry.getPermissionService();
    }

    public NodePermissions transform(NodeRef nodeRef) {
        boolean inherits = permissionService.getInheritParentPermissions(nodeRef);
        NodePermissions perms = new NodePermissions();
        perms.setInheritanceEnabled(inherits);
        Set<AccessPermission> permissionSet = permissionService.getAllSetPermissions(nodeRef);
        Set<NodePermission> set = new HashSet<>();
        for (AccessPermission perm : permissionSet) {

            NodePermission nodePerm = new NodePermission.NodePermissionBuilder().authority(perm.getAuthority())
                    .authorityType(perm.getAuthorityType().name())
                    .isInherited(perm.isInherited())
                    .permission(perm.getPermission())
                    .build();
            set.add(nodePerm);
        }
        perms.setPermissions(set);
        return perms;
    }

}
