package com.yanlilong.docrobot.datacapture.behavior;

import com.yanlilong.docrobot.kafka.model.NodeEvent;
import com.yanlilong.docrobot.kafka.model.NodeEvent.EventType;
import com.yanlilong.docrobot.kafka.service.MessageService;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodeEvent;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodePermissions;
import lombok.extern.slf4j.Slf4j;
import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import java.io.Serializable;
import java.util.Map;

import static org.alfresco.model.ContentModel.TYPE_CMOBJECT;

@Slf4j
public class CmObjectBehavior {

  private final ServiceRegistry serviceRegistry;
  private final MessageService messageService;
  private final NodeRefToNodeEvent nodeTransformer;
  private final NodeRefToNodePermissions nodePermissionsTransformer;

  public CmObjectBehavior(ServiceRegistry serviceRegistry, MessageService kafkaMessageService,
      NodeRefToNodeEvent nodeTransformer, NodeRefToNodePermissions nodePermissionsTransformer) {
    this.serviceRegistry = serviceRegistry;
    this.messageService = kafkaMessageService;
    this.nodeTransformer = nodeTransformer;
    this.nodePermissionsTransformer = nodePermissionsTransformer;
    log.info("BEHAVIOR");
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
    log.info("update poperties");
    if (!serviceRegistry.getNodeService().exists(nodeRef)) {
      log.debug("nodeRef not exist" + nodeRef);
    }
    NodeEvent e = new NodeEvent();
    e.setNodeRef(nodeRef.getId());
    e.setEventType((NodeEvent.EventType.UPDATE));
    e.setPermissions(nodePermissionsTransformer.transform(nodeRef));
    messageService.publish(e);
  }

  public void onCreateNode(ChildAssociationRef parentChildAssocRef) {
    NodeRef nodeRef = parentChildAssocRef.getChildRef();
    if (nodeRef != null) {
      NodeEvent e = new NodeEvent();
      e.setNodeRef(nodeRef.getId());
      e.setEventType((EventType.CREATE));
      e.setPermissions(nodePermissionsTransformer.transform(nodeRef));
      messageService.publish(e);
    }
  }

  public void beforeDeleteNode(NodeRef nodeRef) {
    if (nodeRef != null) {
      NodeEvent e = new NodeEvent();
      e.setNodeRef(nodeRef.getId());
      e.setEventType((EventType.DELETE));
      e.setPermissions(nodePermissionsTransformer.transform(nodeRef));
      messageService.publish(e);
    }

  }
}