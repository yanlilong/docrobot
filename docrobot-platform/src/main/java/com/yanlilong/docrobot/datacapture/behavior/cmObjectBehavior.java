package com.yanlilong.docrobot.datacapture.behavior;

import com.yanlilong.docrobot.kafka.model.NodeEvent;
import com.yanlilong.docrobot.kafka.service.MessageService;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodeEvent;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodePermissions;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

import static org.alfresco.model.ContentModel.TYPE_CMOBJECT;

public class cmObjectBehavior {
    private static final Logger LOGGER = LoggerFactory.getLogger(cmObjectBehavior.class);
    private final ServiceRegistry serviceRegistry;
    private final MessageService messageService;
    private final NodeRefToNodeEvent nodeTransformer;
    private final NodeRefToNodePermissions nodePermissionsTransformer;

    public cmObjectBehavior(ServiceRegistry serviceRegistry, MessageService kafkaMessageService, NodeRefToNodeEvent nodeTransformer, NodeRefToNodePermissions nodePermissionsTransformer) {
        this.serviceRegistry = serviceRegistry;
        this.messageService = kafkaMessageService;
        this.nodeTransformer = nodeTransformer;
        this.nodePermissionsTransformer = nodePermissionsTransformer;
        LOGGER.info("BEHAVIOR");
    }

    public void registerEventHandlers() {
        serviceRegistry.getPolicyComponent().bindClassBehaviour(
                NodeServicePolicies.OnCreateNodePolicy.QNAME, TYPE_CMOBJECT,
                new JavaBehaviour(this, "onCreateNode",
                        Behaviour.NotificationFrequency.TRANSACTION_COMMIT));

        serviceRegistry.getPolicyComponent().bindClassBehaviour(
                NodeServicePolicies.OnUpdatePropertiesPolicy.QNAME, TYPE_CMOBJECT,
                new JavaBehaviour(this, "onUpdateProperties",
                        Behaviour.NotificationFrequency.TRANSACTION_COMMIT));

        serviceRegistry.getPolicyComponent().bindClassBehaviour(
                NodeServicePolicies.BeforeDeleteNodePolicy.QNAME, TYPE_CMOBJECT,
                new JavaBehaviour(this, "beforeDeleteNode",
                        Behaviour.NotificationFrequency.TRANSACTION_COMMIT));
    }

    public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> before,
                                   Map<QName, Serializable> after) {
        LOGGER.info("before" + before);
        LOGGER.info("after" + after);
        if (serviceRegistry.getNodeService().exists(nodeRef)) {
            NodeEvent e = new NodeEvent();
            e.setNodeRef(nodeRef.getId());
            e.setEventType((NodeEvent.EventType.UPDATE));
            e.setPermissions(nodePermissionsTransformer.transform(nodeRef));

            messageService.publish(e);
        }
    }

    public void onCreateNode(ChildAssociationRef parentChildAssocRef) {

    }

    public void beforeDeleteNode(NodeRef nodeRef) {

    }

}
