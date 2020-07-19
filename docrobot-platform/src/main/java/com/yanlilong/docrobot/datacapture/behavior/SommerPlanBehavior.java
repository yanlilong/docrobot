package com.yanlilong.docrobot.datacapture.behavior;

import com.yanlilong.docrobot.kafka.model.NodeEvent;
import com.yanlilong.docrobot.kafka.service.MessageService;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodeEvent;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodePermissions;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

import static com.yanlilong.docrobot.DocRobotConstants.TYPE_SOMMER_PLAN;

public class SommerPlanBehavior {
    private static final Logger LOGGER = LoggerFactory.getLogger(SommerPlanBehavior.class);
    private final ServiceRegistry serviceRegistry;
    private MessageService messageService;
    private NodeRefToNodeEvent nodeTransformer;
    private NodeRefToNodePermissions nodePermissionsTransformer;

    public SommerPlanBehavior(ServiceRegistry serviceRegistry,MessageService kafkaMessageService,NodeRefToNodeEvent nodeTransformer, NodeRefToNodePermissions nodePermissionsTransformer) {
        this.serviceRegistry = serviceRegistry;
        this.messageService=kafkaMessageService;
        this.nodeTransformer=nodeTransformer;
        this.nodePermissionsTransformer=nodePermissionsTransformer;
        LOGGER.info("BEHAVIOR");
    }

    public void registerEventHandlers() {
          serviceRegistry.getPolicyComponent().bindClassBehaviour(
         NodeServicePolicies.OnUpdatePropertiesPolicy.QNAME, TYPE_SOMMER_PLAN,
         new JavaBehaviour(this, "onUpdateSummerHolidayProperties",
         Behaviour.NotificationFrequency.TRANSACTION_COMMIT));

    }

     public void onUpdateSummerHolidayProperties(NodeRef sommerPlan, Map<QName, Serializable> before,
                                                 Map<QName, Serializable> after) {
     //if(after.containsKey(PROP_START_DATE))
     LOGGER.info("before" + before);
     LOGGER.info("after" + after);
     if(serviceRegistry.getNodeService().exists(sommerPlan)){
         NodeEvent e=nodeTransformer.transform(sommerPlan);
         e.setEventType((NodeEvent.EventType.UPDATE));
         e.setPermissions(nodePermissionsTransformer.transform(sommerPlan));
         messageService.publish(e);
     }
     }

}
